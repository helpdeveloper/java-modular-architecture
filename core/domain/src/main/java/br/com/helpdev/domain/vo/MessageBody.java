package br.com.helpdev.domain.vo;

import br.com.helpdev.domain.exception.InvalidMessageException;
import java.util.Objects;

public class MessageBody {

  public static MessageBody from(final String body) {
    return new MessageBody(body);
  }

  private final String body;

  MessageBody(final String body) {
    Objects.requireNonNull(body);
    if (body.isEmpty()) {
      throw new InvalidMessageException("Body cant be empty");
    }
    this.body = body;
  }

  public String value() {
    return body;
  }
}
