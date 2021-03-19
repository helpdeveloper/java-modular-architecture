package br.com.helpdev.usecase.exception;

import br.com.helpdev.domain.exception.NotificationException;

public class MessageNotFoundException extends NotificationException {

  public MessageNotFoundException(String message) {
    super(message);
  }
}
