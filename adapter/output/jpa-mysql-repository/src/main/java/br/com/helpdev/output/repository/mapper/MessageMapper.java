package br.com.helpdev.output.repository.mapper;


import br.com.helpdev.domain.Chat;
import br.com.helpdev.domain.CommunicationChannel;
import br.com.helpdev.domain.Message;
import br.com.helpdev.domain.Recipient;
import br.com.helpdev.domain.Status;
import br.com.helpdev.domain.vo.MessageBody;
import br.com.helpdev.domain.vo.MessageId;
import br.com.helpdev.domain.vo.Phone;
import br.com.helpdev.output.repository.entity.ChatEntity;
import br.com.helpdev.output.repository.entity.CommunicationChannelEntity;
import br.com.helpdev.output.repository.entity.MessageEntity;
import br.com.helpdev.output.repository.entity.RecipientEntity;
import br.com.helpdev.output.repository.entity.StatusEntity;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class MessageMapper {

  public MessageEntity toEntity(final Message message) {
    return MessageEntity.builder()
        .id(message.getId() == null ? null : message.getId().value())
        .body(message.getBody().value())
        .channel(CommunicationChannelEntity.valueOf(message.getChannel().name()))
        .chats(toChatEntity(message.getChats()))
        .scheduleDate(message.getScheduleDate())
        .recipient(RecipientEntity.builder()
            .email(message.getRecipient().getEmail())
            .name(message.getRecipient().getName())
            .phoneId(message.getRecipient().getPhone().getPhoneId().orElse(null))
            .phoneNumber(message.getRecipient().getPhone().getPhoneNumber())
            .build())
        .build();
  }

  public Message toDomain(final MessageEntity message) {
    return Message.builder()
        .id(MessageId.from(message.getId()))
        .body(MessageBody.from(message.getBody()))
        .channel(CommunicationChannel.valueOf(message.getChannel().name()))
        .chats(toChatDomain(message.getChats()))
        .scheduleDate(message.getScheduleDate())
        .recipient(Recipient.builder()
            .email(message.getRecipient().getEmail())
            .name(message.getRecipient().getName())
            .phone(Phone.from(message.getRecipient().getPhoneId(), message.getRecipient().getPhoneNumber()))
            .build())
        .build();
  }

  private List<Chat> toChatDomain(final List<ChatEntity> chats) {
    return chats != null ? chats.stream()
        .map(c -> Chat.builder()
            .id(c.getId())
            .status(Status.valueOf(c.getStatus().name()))
            .date(c.getDate())
            .build())
        .collect(Collectors.toList()) : Collections.emptyList();
  }


  private List<ChatEntity> toChatEntity(final Collection<Chat> chats) {
    return chats != null ? chats.stream()
        .map(c -> ChatEntity.builder()
            .id(c.getId())
            .status(StatusEntity.valueOf(c.getStatus().name()))
            .date(c.getDate())
            .build())
        .collect(Collectors.toList()) : Collections.emptyList();
  }
}
