package br.com.helpdev.atdd;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import br.com.helpdev.controller.dto.CommunicationChannelDto;
import br.com.helpdev.controller.dto.MessageCreateDto;
import br.com.helpdev.controller.dto.RecipientDto;
import io.restassured.http.ContentType;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

class ApplicationIT extends AbstractContainerBaseTest {

  @Test
  void whenCreateNewScheduleThenReturn() {
    var now = ZonedDateTime.now();
    Integer id = given()
        .contentType(ContentType.JSON)
        .body(buildDefaultMessage(now))
        .when()
        .post("/v1/message/")
        .then()
        .statusCode(201)
        .body("id", notNullValue())
        .body("scheduleDate", notNullValue())
        .body("body", equalTo("Hello"))
        .body("channel", equalTo("WHATSAPP"))
        .body("recipient.name", equalTo("John James"))
        .body("chats[0].status", equalTo("WAITING"))
        .extract()
        .path("id");
    given()
        .when()
        .get("/v1/message/{id}", id)
        .then()
        .statusCode(200)
        .body("scheduleDate", notNullValue())
        .body("body", equalTo("Hello"))
        .body("channel", equalTo("WHATSAPP"))
        .body("recipient.name", equalTo("John James"))
        .body("recipient.phoneId", equalTo("123"))
        .body("recipient.email", equalTo("johm@jame.com"))
        .body("recipient.phoneNumber", equalTo("89898989"))
        .body("chats[0].status", equalTo("WAITING"));
  }

  @Test
  void whenGetWithInvalidIdThenReturnError() {
    given()
        .when()
        .get("/v1/message/{id}", "123123")
        .then()
        .statusCode(404)
        .body("message",
            equalTo("Message with Id 123123 not found"));
  }

  @Test
  void whenDeleteMessageThenRemove() {
    var now = ZonedDateTime.now();
    Integer id = given()
        .contentType(ContentType.JSON)
        .body(buildDefaultMessage(now))
        .when()
        .post("/v1/message/")
        .then()
        .statusCode(201)
        .extract()
        .path("id");
    given()
        .when()
        .delete("/v1/message/{id}", id)
        .then()
        .statusCode(204);
    given()
        .when()
        .get("/v1/message/{id}", id)
        .then()
        .statusCode(404);
  }

  @Test
  void whenDeleteWithInvalidIdThenReturnError() {
    given()
        .when()
        .delete("/v1/message/{id}", "123123")
        .then()
        .statusCode(404)
        .body("message", equalTo("Message id: 123123 dont exists"));
  }

  @Test
  void whenCreateNewEmailScheduleThenReturn() {
    given()
        .contentType(ContentType.JSON)
        .body(buildMessage(ZonedDateTime.now(), CommunicationChannelDto.EMAIL))
        .when()
        .post("/v1/message/")
        .then()
        .statusCode(201)
        .body("id", notNullValue())
        .body("channel", equalTo("EMAIL"))
        .body("recipient.name", equalTo("John James"))
        .body("chats[0].status", equalTo("WAITING"));
  }

  @Test
  void whenCreateNewPushScheduleThenReturn() {
    given()
        .contentType(ContentType.JSON)
        .body(buildMessage(ZonedDateTime.now(), CommunicationChannelDto.PUSH))
        .when()
        .post("/v1/message/")
        .then()
        .statusCode(201)
        .body("id", notNullValue())
        .body("channel", equalTo("PUSH"))
        .body("recipient.name", equalTo("John James"))
        .body("chats[0].status", equalTo("WAITING"));
  }

  @Test
  void whenCreateNewSmsScheduleThenReturn() {
    given()
        .contentType(ContentType.JSON)
        .body(buildMessage(ZonedDateTime.now(), CommunicationChannelDto.SMS))
        .when()
        .post("/v1/message/")
        .then()
        .statusCode(201)
        .body("id", notNullValue())
        .body("channel", equalTo("SMS"))
        .body("recipient.name", equalTo("John James"))
        .body("chats[0].status", equalTo("WAITING"));
  }

  private MessageCreateDto buildDefaultMessage(ZonedDateTime now) {
    return buildMessage(now, CommunicationChannelDto.WHATSAPP);
  }

  private MessageCreateDto buildMessage(ZonedDateTime now, CommunicationChannelDto channel) {
    return MessageCreateDto.builder()
        .channel(channel)
        .scheduleDate(now)
        .body("Hello")
        .recipient(RecipientDto.builder()
            .name("John James")
            .email("johm@jame.com")
            .phoneId("123")
            .phoneNumber("89898989")
            .build())
        .build();
  }

}
