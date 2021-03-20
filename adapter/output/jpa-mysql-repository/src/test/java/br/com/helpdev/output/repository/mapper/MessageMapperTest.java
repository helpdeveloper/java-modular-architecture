package br.com.helpdev.output.repository.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.helpdev.domain.CommunicationChannel;
import br.com.helpdev.domain.Message;
import br.com.helpdev.domain.Recipient;
import br.com.helpdev.domain.vo.MessageBody;
import br.com.helpdev.domain.vo.MessageId;
import br.com.helpdev.domain.vo.Phone;
import br.com.helpdev.output.repository.entity.CommunicationChannelEntity;
import br.com.helpdev.output.repository.entity.MessageEntity;
import br.com.helpdev.output.repository.entity.RecipientEntity;
import org.junit.jupiter.api.Test;

class MessageMapperTest {

  private final MessageMapper messageMapper = new MessageMapper();

  @Test
  void whenDomainHasIdThenMap() {
    var message = messageMapper.toDomain(entityBuilder().id(1L).build());
    assertThat(message.getId()).get().isEqualTo(MessageId.from(1L));
  }

  @Test
  void whenEntityHasIdThenMap() {
    var message = messageMapper.toEntity(domainBuilder().id(MessageId.from(1L)).build());
    assertThat(message.getId()).isEqualTo(1L);
  }

  private MessageEntity.MessageEntityBuilder entityBuilder() {
    return MessageEntity.builder()
        .channel(CommunicationChannelEntity.EMAIL)
        .recipient(RecipientEntity.builder()
            .phoneNumber("78924")
            .build())
        .body("Test");
  }

  private Message.MessageBuilder domainBuilder() {
    return Message.builder()
        .channel(CommunicationChannel.WHATSAPP)
        .recipient(Recipient.builder()
            .phone(Phone.from("78924", "1243243"))
            .build())
        .body(MessageBody.from("Hello!"));
  }

}