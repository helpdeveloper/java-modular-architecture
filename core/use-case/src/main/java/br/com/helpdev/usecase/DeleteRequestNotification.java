package br.com.helpdev.usecase;

import br.com.helpdev.domain.exception.NotificationException;
import br.com.helpdev.domain.vo.MessageId;
import br.com.helpdev.usecase.exception.MessageNotFoundException;
import br.com.helpdev.usecase.port.MessageRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

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
          "Mensagem com o id: " + id + " não existe ou já foi removida, confira se o identificador está correto.");
    }
  }

}
