package br.com.helpdev.domain.exception;

public class InvalidRecipientException extends NotificationException {

  public InvalidRecipientException(final String message) {
    super(message);
  }
}
