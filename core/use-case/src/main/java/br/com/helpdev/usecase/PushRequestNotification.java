package br.com.helpdev.usecase;

import br.com.helpdev.domain.Chat;
import br.com.helpdev.domain.Message;
import br.com.helpdev.domain.Status;
import br.com.helpdev.domain.exception.NotificationException;
import br.com.helpdev.usecase.port.MessageRepository;
import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class PushRequestNotification {

  private final MessageRepository repository;

  @Inject
  PushRequestNotification(final MessageRepository repository) {
    this.repository = repository;
  }

  public Message push(final Message message) throws NotificationException {
    return repository.create(message.toBuilder()
        .chats(getSingleListWithCurrentTimesAndWaitingStatus())
        .build());
  }

  private List<Chat> getSingleListWithCurrentTimesAndWaitingStatus() {
    return List.of(Chat.builder()
        .date(ZonedDateTime.now())
        .status(Status.WAITING)
        .build());
  }

}
