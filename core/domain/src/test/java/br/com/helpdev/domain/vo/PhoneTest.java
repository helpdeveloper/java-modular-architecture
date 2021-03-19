package br.com.helpdev.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class PhoneTest {

  @Test
  void shouldThrowsNullPointerWhenPhoneNumberIsNull() {
    assertThatThrownBy(() -> Phone.from(null, null))
        .hasMessage("Phone number cant be null");
  }

  @Test
  void shouldNotThrowsWhenOnlyPhoneIdIsNull() {
    final var messageBody = Phone.from(null, "132123");
    assertThat(messageBody.getPhoneNumber())
        .isNotNull()
        .isEqualTo("132123");
    assertThat(messageBody.getPhoneId())
        .isNotNull()
        .isEqualTo(Optional.empty());
  }

  @Test
  void shouldCreatePhoneWithNewNumber() {
    final var messageBody = Phone.newNumber("132123");
    assertThat(messageBody.getPhoneNumber())
        .isNotNull()
        .isEqualTo("132123");
    assertThat(messageBody.getPhoneId())
        .isNotNull()
        .isEqualTo(Optional.empty());
  }

  @Test
  void shouldCreatePhoneWithIdAndNumber() {
    final var messageBody = Phone.from("1", "132123");
    assertThat(messageBody.getPhoneNumber())
        .isNotNull()
        .isEqualTo("132123");
    assertThat(messageBody.getPhoneId())
        .isNotNull()
        .isEqualTo(Optional.of("1"));
  }
}