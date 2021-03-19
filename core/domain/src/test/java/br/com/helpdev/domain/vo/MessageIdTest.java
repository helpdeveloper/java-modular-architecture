package br.com.helpdev.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class MessageIdTest {

  @Test
  void shouldThrowsNullPointerWhenMessageIdIsNull() {
    assertThatThrownBy(() -> MessageId.from(null))
        .hasMessage("Id cant be null");
  }

  @Test
  void shouldThrowsInvalidMessageExceptionWhenBodyIsntGreaterThan0() {
    assertThatThrownBy(() -> MessageId.from(0L))
        .hasMessage("Id should be greater than 0");
    assertThatThrownBy(() -> MessageId.from(-1L))
        .hasMessage("Id should be greater than 0");
  }

  @Test
  void shouldBeCreatedWithSuccess() {
    final var messageBody = MessageId.from(1L);
    assertThat(messageBody.value())
        .isNotNull()
        .isEqualTo(1L);
  }
}