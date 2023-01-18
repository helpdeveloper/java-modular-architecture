package br.com.helpdev.atdd;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.slf4j.LoggerFactory;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.lifecycle.Startable;
import org.testcontainers.utility.MountableFile;

abstract class DefaultContainerStarterTest {

  private static final GenericContainer<?> APP;
  private static final GenericContainer<?> FLYWAY;
  private static final GenericContainer<?> MYSQL_CONTAINER;
  private static final Network NETWORK = Network.newNetwork();
  protected static final WireMockServer MOCK_SERVER;

  /* Containers are initialized in static block to create only once in test execution  */
  static {
    MOCK_SERVER = new WireMockServer(wireMockConfig().dynamicPort());
    MOCK_SERVER.start();
    exposeHostMachinePortToContainersForApiIntegrations();

    MYSQL_CONTAINER = buildMySqlContainer();
    MYSQL_CONTAINER.start();

    FLYWAY = buildFlywayContainer(MYSQL_CONTAINER);
    FLYWAY.start();

    APP = buildAppContainer(MYSQL_CONTAINER, FLYWAY);
    APP.start();

    initRestAssured();
  }

  private static GenericContainer<?> buildMySqlContainer() {
    return new MySQLContainer<>("mysql:5.7.22")
        .withNetwork(NETWORK)
        .withNetworkAliases("testdb");
  }

  private static GenericContainer<?> buildFlywayContainer(final Startable... dependsOn) {
    return new GenericContainer<>("flyway/flyway:7.5.2")
        .dependsOn(dependsOn)
        .withNetwork(NETWORK)
        .withCopyFileToContainer(MountableFile.forHostPath("../resources/flyway/db"), "/flyway/sql")
        .withCommand("-url=jdbc:mysql://testdb?useSSL=false -schemas=test -user=test -password=test -connectRetries=60 migrate")
        .waitingFor(Wait.forLogMessage("(?s).*No migration necessary(?s).*|(?s).*Successfully applied(?s).*", 1))
        .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger("FLYWAY")));
  }

  private static GenericContainer<?> buildAppContainer(final Startable... dependsOn) {
    return new GenericContainer<>("app-test:integration")
        .dependsOn(dependsOn)
        .withNetwork(NETWORK)
        .withEnv("RANDOM_DATA_API_URL", "http://host.testcontainers.internal:" + MOCK_SERVER.port())
        .withEnv("MYSQL_USER", "test")
        .withEnv("MYSQL_PASSWORD", "test")
        .withEnv("MYSQL_URL", "jdbc:mysql://testdb:" + MySQLContainer.MYSQL_PORT + "/test?autoReconnect=true&useSSL=false")
        .withExposedPorts(8080)
        //TODO: change to: '/actuator/health' if you use spring
        .waitingFor(Wait.forHttp("/actuator/health").forStatusCode(200))
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

  @AfterEach
  void tearDown() {
    MOCK_SERVER.resetAll();
    /* add here others resets needed after each test */
  }

}
