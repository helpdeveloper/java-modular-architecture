package br.com.helpdev.usecase;


import br.com.helpdev.domain.Message;
import br.com.helpdev.domain.exception.NotificationException;
import br.com.helpdev.domain.vo.MessageId;
import br.com.helpdev.usecase.exception.MessageNotFoundException;
import br.com.helpdev.usecase.port.MessageRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class FindRequestNotification {

  private final MessageRepository repository;

  @Inject
  FindRequestNotification(final MessageRepository repository) {
    this.repository = repository;
  }

  public Message find(final MessageId id) throws NotificationException {
    return repository.find(id).orElseThrow(() ->
        new MessageNotFoundException("Message with Id " + id + " not found")
    );
  }

}
