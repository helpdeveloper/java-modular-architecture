package br.com.helpdev.domain;

import br.com.helpdev.domain.exception.InvalidRecipientException;
import br.com.helpdev.domain.vo.MessageBody;
import br.com.helpdev.domain.vo.MessageId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Objects;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Message {

  @EqualsAndHashCode.Include
  private final MessageId id;
  private final ZonedDateTime scheduleDate;
  private final MessageBody body;
  private final Recipient recipient;
  private final CommunicationChannel channel;
  private final Collection<Chat> chats;

  @Builder
  public Message(final MessageId id, final ZonedDateTime scheduleDate, final MessageBody body, final Recipient recipient,
                 final CommunicationChannel channel, final Collection<Chat> chats) {
    Objects.requireNonNull(body, "Body cant be null");
    if (recipient == null) {
      throw new InvalidRecipientException("Mensagem precisa de um destinatário, favor informar nome, telefone e e-mail!");
    }
    this.id = id;
    this.scheduleDate = scheduleDate;
    this.body = body;
    this.recipient = recipient;
    this.channel = channel;
    this.chats = chats;
  }
}

