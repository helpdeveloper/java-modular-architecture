package br.com.helpdev.domain.vo;

import java.util.Objects;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class MessageId {

  public static MessageId from(final Long id) {
    return new MessageId(id);
  }

  @EqualsAndHashCode.Include
  private final Long id;

  MessageId(final Long id) {
    Objects.requireNonNull(id, "Id cant be null");
    if (id <= 0) {
      throw new IllegalArgumentException("Id should be greater than 0");
    }
    this.id = id;
  }

  public Long value() {
    return id;
  }

  @Override
  public String toString() {
    return String.valueOf(id);
  }
}
