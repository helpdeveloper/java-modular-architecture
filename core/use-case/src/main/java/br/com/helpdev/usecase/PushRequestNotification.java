package br.com.helpdev.usecase;

import br.com.helpdev.domain.Chat;
import br.com.helpdev.domain.Message;
import br.com.helpdev.domain.Status;
import br.com.helpdev.domain.exception.NotificationException;
import br.com.helpdev.usecase.port.MessageRepository;
import br.com.helpdev.usecase.port.ProtocolGeneratorClient;
import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class PushRequestNotification {

  private final MessageRepository repository;
  private final ProtocolGeneratorClient protocolGeneratorClient;

  @Inject
  PushRequestNotification(final MessageRepository repository,
                          final ProtocolGeneratorClient protocolGeneratorClient) {
    this.repository = repository;
    this.protocolGeneratorClient = protocolGeneratorClient;
  }

  public Message push(final Message message) throws NotificationException {
    final var messageBuilder = message.toBuilder()
        .chats(getSingleListWithCurrentTimesAndWaitingStatus())
        .protocol(protocolGeneratorClient.generateNewProtocol());

    return repository.create(messageBuilder.build());
  }

  private List<Chat> getSingleListWithCurrentTimesAndWaitingStatus() {
    return List.of(Chat.builder()
        .date(ZonedDateTime.now())
        .status(Status.WAITING)
        .build());
  }

}
