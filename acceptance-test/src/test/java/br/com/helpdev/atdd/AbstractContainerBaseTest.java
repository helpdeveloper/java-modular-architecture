package br.com.helpdev.atdd;

import io.restassured.RestAssured;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.MountableFile;

abstract class AbstractContainerBaseTest {

  private static final GenericContainer<?> APP;
  private static final GenericContainer<?> FLYWAY;
  private static final MySQLContainer<?> MY_SQL_CONTAINER;

  static {
    final var network = Network.newNetwork();

    MY_SQL_CONTAINER = (MySQLContainer) new MySQLContainer("mysql:5.7.22")
        .withNetwork(network)
        .withNetworkAliases("testdb");

    FLYWAY = new GenericContainer("flyway/flyway")
        .dependsOn(MY_SQL_CONTAINER)
        .withNetwork(network)
        .withCopyFileToContainer(MountableFile.forHostPath("../resources/flyway/db/"), "/flyway/sql")
        .withCommand("-url=jdbc:mysql://testdb -schemas=test -user=test -password=test -connectRetries=60 migrate")
        .waitingFor(
            Wait.forLogMessage("(?s).*No migration necessary(?s).*|(?s).*Successfully applied(?s).*", 1)
        );

    APP = new GenericContainer("app-test:integration")
        .dependsOn(MY_SQL_CONTAINER, FLYWAY)
        .withNetwork(network)
        .withEnv("MYSQL_USER", "test")
        .withEnv("MYSQL_PASSWORD", "test")
        .withEnv("MYSQL_URL", "jdbc:mysql://testdb:" + MySQLContainer.MYSQL_PORT + "/test?autoReconnect=true&useSSL=false")
        .withExposedPorts(8080)
        .waitingFor(Wait.forHttp("/health/ready")
            .forStatusCode(200));

    MY_SQL_CONTAINER.start();
    FLYWAY.start();
    APP.start();
    init();
  }


  private static void init() {
    RestAssured.baseURI = "http://" + APP.getHost();
    RestAssured.port = APP.getFirstMappedPort();
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
  }


}
