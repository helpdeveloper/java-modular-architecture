####
# This Dockerfile is used in order to build a container that runs the Quarkus application in JVM mode
#
# Before building the container image run:
#
# ./mvnw package
#
# Then, build the image with:
#
# docker build  -t helpdev/app-quarkus-jvm .
#
# Then run the container using:
#
# docker run -i --rm -p 8080:8080 helpdev/app-quarkus-jvm
#
# If you want to include the debug port into your docker image
# you will have to expose the debug port (default 5005) like this :  EXPOSE 8080 5050
#
# Then run the container using :
#
# docker run -i --rm -p 8080:8080 -p 5005:5005 -e JAVA_ENABLE_DEBUG="true" helpdev/app-quarkus-jvm
#
# Sample to run Flyway migration :
#
# docker run --rm --workdir="/flyway" --entrypoint /flyway/flyway helpdev/app-quarkus-jvm -url=jdbc:mysql://172.17.0.1:3306 -schemas=app -user=root -password=test123 -connectRetries=60 migrate
#
###
FROM registry.access.redhat.com/ubi8/ubi-minimal:8.3

ARG JAVA_PACKAGE=java-17-openjdk-headless
ARG RUN_JAVA_VERSION=1.3.8
ENV FLYWAY_VERSION 7.5.2
ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en'

# Install java and the run-java script
# Also set up permissions for user `1001`
RUN microdnf install curl ca-certificates ${JAVA_PACKAGE} tar gzip \
    && microdnf update \
    && microdnf clean all \
    && mkdir /deployments \
    && mkdir /flyway \
    && chown 1001 /deployments \
    && chmod "g+rwX" /deployments \
    && chown 1001:root /deployments \
    && curl https://repo1.maven.org/maven2/io/fabric8/run-java-sh/${RUN_JAVA_VERSION}/run-java-sh-${RUN_JAVA_VERSION}-sh.sh -o /deployments/run-java.sh \
    && chown 1001 /deployments/run-java.sh \
    && chmod 540 /deployments/run-java.sh \
    && echo "securerandom.source=file:/dev/urandom" >> /etc/alternatives/jre/lib/security/java.security

# Install and configure Flyway
RUN curl https://repo1.maven.org/maven2/org/flywaydb/flyway-commandline/${FLYWAY_VERSION}/flyway-commandline-${FLYWAY_VERSION}.tar.gz -o /flyway/flyway-commandline-${FLYWAY_VERSION}.tar.gz \
    && tar -xzf /flyway/flyway-commandline-${FLYWAY_VERSION}.tar.gz --strip-components=1 -C /flyway \
    && rm /flyway/flyway-commandline-${FLYWAY_VERSION}.tar.gz \
    && chown 1001 /flyway/flyway \
    && chmod 540 /flyway/flyway

# Configure the JAVA_OPTIONS, you can add -XshowSettings:vm to also display the heap size.
ENV JAVA_OPTIONS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="quarkus-run.jar"
COPY app/quarkus-app/target/quarkus-app/ /deployments/
COPY resources/flyway/db/migration /flyway/sql

EXPOSE 8080
USER 1001

ENTRYPOINT [ "/deployments/run-java.sh" ]
