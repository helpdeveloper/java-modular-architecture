package br.com.helpdev.quarkus;

import static io.restassured.RestAssured.given;

import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

@QuarkusTest
class QuarkusAppTest {

  @Test
  void whenAppIsHealthThenReturnLive() {
    given()
        .when()
        .get("/q/health/live")
        .then()
        .statusCode(200)
        .body(CoreMatchers.notNullValue());
  }

}
