package br.com.helpdev.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class MessageBodyTest {

  @Test
  void shouldThrowsNullPointerWhenBodyIsNull() {
    assertThatThrownBy(() -> MessageBody.from(null))
        .hasMessage("Body cant be null");
  }

  @Test
  void shouldThrowsInvalidMessageExceptionWhenBodyIsEmpty() {
    assertThatThrownBy(() -> MessageBody.from(""))
        .hasMessage("Body cant be empty");
  }

  @Test
  void shouldBeCreatedWithSuccess() {
    final var messageBody = MessageBody.from("test");
    assertThat(messageBody.value())
        .isNotNull()
        .isEqualTo("test");
  }

}