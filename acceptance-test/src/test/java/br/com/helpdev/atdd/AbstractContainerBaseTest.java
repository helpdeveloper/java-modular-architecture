package br.com.helpdev.atdd;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import org.slf4j.LoggerFactory;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.lifecycle.Startable;
import org.testcontainers.utility.MountableFile;

abstract class AbstractContainerBaseTest {

  private static final GenericContainer<?> APP;
  private static final GenericContainer<?> FLYWAY;
  private static final GenericContainer<?> MYSQL_CONTAINER;
  protected static final WireMockServer MOCK_SERVER;

  static {
    final var network = Network.newNetwork();

    MOCK_SERVER = new WireMockServer(wireMockConfig().dynamicPort());
    MOCK_SERVER.start();
    exposeHostMachinePortToContainersForApiIntegrations();

    MYSQL_CONTAINER = buildMySqlContainer(network);
    MYSQL_CONTAINER.start();

    FLYWAY = buildFlywayContainer(network, MYSQL_CONTAINER);
    FLYWAY.start();

    APP = buildAppContainer(network, MYSQL_CONTAINER, FLYWAY);
    APP.start();

    initRestAssured();
  }

  private static GenericContainer<?> buildMySqlContainer(final Network network) {
    return new MySQLContainer<>("mysql:5.7.22")
        .withNetwork(network)
        .withNetworkAliases("testdb");
  }

  private static GenericContainer<?> buildFlywayContainer(final Network network, final Startable... dependsOn) {
    return new GenericContainer<>("flyway/flyway")
        .dependsOn(dependsOn)
        .withNetwork(network)
        .withCopyFileToContainer(MountableFile.forHostPath("../resources/flyway/db"), "/flyway/sql")
        .withCommand("-url=jdbc:mysql://testdb?useSSL=false -schemas=test -user=test -password=test -connectRetries=60 migrate")
        .waitingFor(Wait.forLogMessage("(?s).*No migration necessary(?s).*|(?s).*Successfully applied(?s).*", 1))
        .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger("FLYWAY")));
  }

  private static GenericContainer<?> buildAppContainer(final Network network, final Startable... dependsOn) {
    return new GenericContainer<>("app-test:integration")
        .dependsOn(dependsOn)
        .withNetwork(network)
        .withEnv("RANDOM_DATA_API_URL", "http://host.testcontainers.internal:" + MOCK_SERVER.port())
        .withEnv("MYSQL_USER", "test")
        .withEnv("MYSQL_PASSWORD", "test")
        .withEnv("MYSQL_URL", "jdbc:mysql://testdb:" + MySQLContainer.MYSQL_PORT + "/test?autoReconnect=true&useSSL=false")
        .withExposedPorts(8080)
        .waitingFor(Wait.forHttp("/health/ready").forStatusCode(200))
        .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger("APP_CONTAINER")));
  }


  private static void initRestAssured() {
    RestAssured.baseURI = "http://" + APP.getHost();
    RestAssured.port = APP.getFirstMappedPort();
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
  }

  private static void exposeHostMachinePortToContainersForApiIntegrations() {
    Testcontainers.exposeHostPorts(MOCK_SERVER.port());
  }

}
