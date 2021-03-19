package br.com.helpdev.domain.vo;

import java.util.Objects;
import java.util.Optional;
import lombok.Getter;

public class Phone {

  public static Phone newNumber(final String phoneNumber) {
    return new Phone(null, phoneNumber);
  }

  public static Phone from(final String phoneId, final String phoneNumber) {
    return new Phone(phoneId, phoneNumber);
  }

  private final String phoneId;
  @Getter
  private final String phoneNumber;

  Phone(final String phoneId, final String phoneNumber) {
    Objects.requireNonNull(phoneNumber);
    this.phoneId = phoneId;
    this.phoneNumber = phoneNumber;
  }

  public Optional<String> getPhoneId() {
    return Optional.ofNullable(phoneId);
  }
}
