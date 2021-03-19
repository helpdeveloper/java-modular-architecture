package br.com.helpdev.controller.mapper;

import br.com.helpdev.controller.dto.ChatResponseDto;
import br.com.helpdev.controller.dto.CommunicationChannelDto;
import br.com.helpdev.controller.dto.MessageCreateDto;
import br.com.helpdev.controller.dto.MessageResponseDto;
import br.com.helpdev.controller.dto.RecipientDto;
import br.com.helpdev.controller.dto.StatusResponseDto;
import br.com.helpdev.domain.Chat;
import br.com.helpdev.domain.CommunicationChannel;
import br.com.helpdev.domain.Message;
import br.com.helpdev.domain.Recipient;
import br.com.helpdev.domain.vo.MessageBody;
import br.com.helpdev.domain.vo.Phone;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ControllerMessageMapper {

  public MessageResponseDto toDto(final Message message) {
    return MessageResponseDto.builder()
        .id(message.getId().value())
        .scheduleDate(message.getScheduleDate())
        .body(message.getBody().value())
        .channel(parse(message.getChannel()))
        .recipient(parse(message.getRecipient()))
        .chats(parse(message.getChats()))
        .build();
  }

  public Message toEntity(final MessageCreateDto message) {
    return Message.builder()
        .body(MessageBody.from(message.getBody()))
        .scheduleDate(message.getScheduleDate())
        .channel(parse(message.getChannel()))
        .recipient(parse(message.getRecipient()))
        .build();
  }

  public Collection<ChatResponseDto> parse(final Collection<Chat> chats) {
    if (chats == null) {
      return Collections.emptyList();
    }
    return chats.stream()
        .map(c -> ChatResponseDto.builder()
            .date(c.getDate())
            .status(StatusResponseDto.findByStatus(c.getStatus()))
            .build())
        .collect(Collectors.toList());
  }

  public CommunicationChannel parse(final CommunicationChannelDto dto) {
    if (dto == null) {
      return null;
    }
    return CommunicationChannel.valueOf(dto.name());
  }

  public CommunicationChannelDto parse(final CommunicationChannel value) {
    if (value == null) {
      return null;
    }
    return CommunicationChannelDto.valueOf(value.name());
  }

  public Recipient parse(final RecipientDto recipient) {
    if (recipient == null) {
      return null;
    }
    return Recipient.builder()
        .email(recipient.getEmail())
        .name(recipient.getName())
        .phone(Phone.from(recipient.getPhoneId(), recipient.getPhoneNumber()))
        .build();
  }

  public RecipientDto parse(final Recipient recipient) {
    if (recipient == null) {
      return null;
    }
    return RecipientDto.builder()
        .email(recipient.getEmail())
        .name(recipient.getName())
        .phoneId(recipient.getPhone().getPhoneId().orElse(null))
        .phoneNumber(recipient.getPhone().getPhoneNumber())
        .build();
  }


}
