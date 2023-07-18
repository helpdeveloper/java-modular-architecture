package br.com.helpdev.usecase;

import br.com.helpdev.domain.exception.NotificationException;
import br.com.helpdev.domain.vo.MessageId;
import br.com.helpdev.usecase.exception.MessageNotFoundException;
import br.com.helpdev.usecase.port.MessageRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class DeleteRequestNotification {

  private final MessageRepository repository;

  @Inject
  DeleteRequestNotification(final MessageRepository repository) {
    this.repository = repository;
  }

  public void delete(final MessageId id) throws NotificationException {
    if (repository.exists(id)) {
      repository.delete(id);
    } else {
      throw new MessageNotFoundException(
          "Message id: " + id + " dont exists");
    }
  }

}
