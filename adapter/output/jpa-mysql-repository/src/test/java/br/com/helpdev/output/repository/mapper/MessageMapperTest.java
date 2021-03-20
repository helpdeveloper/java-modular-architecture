package br.com.helpdev.output.repository.mapper;

import static org.assertj.core.api.Assertions.assertThat;

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
import java.time.ZonedDateTime;
import java.util.List;
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

  @Test
  void whenDomainHasBodyThenMap() {
    var message = messageMapper.toDomain(entityBuilder().body("Hello ;)").build());
    assertThat(message.getBody()).isEqualTo(MessageBody.from("Hello ;)"));
  }

  @Test
  void whenEntityHasBodyThenMap() {
    var message = messageMapper.toEntity(domainBuilder().body(MessageBody.from("Hello ;)")).build());
    assertThat(message.getBody()).isEqualTo("Hello ;)");
  }

  @Test
  void whenDomainHasChannelThenMap() {
    var message = messageMapper.toDomain(entityBuilder().channel(CommunicationChannelEntity.WHATSAPP).build());
    assertThat(message.getChannel()).isEqualTo(CommunicationChannel.WHATSAPP);
  }

  @Test
  void whenEntityHasChannelThenMap() {
    var message = messageMapper.toEntity(domainBuilder().channel(CommunicationChannel.PUSH).build());
    assertThat(message.getChannel()).isEqualTo(CommunicationChannelEntity.PUSH);
  }

  @Test
  void whenDomainHasDateThenMap() {
    ZonedDateTime now = ZonedDateTime.now();
    var message = messageMapper.toDomain(entityBuilder().scheduleDate(now).build());
    assertThat(message.getScheduleDate()).isEqualTo(now);
  }

  @Test
  void whenEntityHasDateThenMap() {
    ZonedDateTime now = ZonedDateTime.now();
    var message = messageMapper.toEntity(domainBuilder().scheduleDate(now).build());
    assertThat(message.getScheduleDate()).isEqualTo(now);
  }

  @Test
  void whenDomainHasChatThenMap() {
    ZonedDateTime now = ZonedDateTime.now();
    var message = messageMapper.toDomain(entityBuilder().chats(List.of(ChatEntity.builder()
        .id(22L)
        .date(now)
        .status(StatusEntity.SENT)
        .build())).build());
    assertThat(message.getChats()).hasSize(1)
        .first()
        .satisfies(c -> {
              assertThat(c.getId()).isEqualTo(22L);
              assertThat(c.getDate()).isEqualTo(now);
              assertThat(c.getStatus()).isEqualTo(Status.SENT);
            }
        );
  }

  @Test
  void whenEntityHasChatThenMap() {
    ZonedDateTime now = ZonedDateTime.now();
    var message = messageMapper.toEntity(domainBuilder().chats(List.of(Chat.builder()
        .id(23L)
        .date(now)
        .status(Status.SENDING)
        .build())).build());
    assertThat(message.getChats()).hasSize(1)
        .first()
        .satisfies(c -> {
              assertThat(c.getId()).isEqualTo(23L);
              assertThat(c.getDate()).isEqualTo(now);
              assertThat(c.getStatus()).isEqualTo(StatusEntity.SENDING);
            }
        );
  }

  @Test
  void whenDomainHasRecipientThenMap() {
    ZonedDateTime now = ZonedDateTime.now();
    var message = messageMapper.toDomain(entityBuilder().recipient(RecipientEntity.builder()
        .email("a@b.com")
        .phoneId("3")
        .phoneNumber("4")
        .name("test")
        .build()).build());
    assertThat(message.getRecipient().getEmail()).isEqualTo("a@b.com");
    assertThat(message.getRecipient().getName()).isEqualTo("test");
    assertThat(message.getRecipient().getPhone()).isEqualTo(Phone.from("3", "4"));
  }

  @Test
  void whenEntityHasRecipientThenMap() {
    ZonedDateTime now = ZonedDateTime.now();
    var message = messageMapper.toEntity(domainBuilder().recipient(Recipient.builder()
        .email("a@b.com")
        .phone(Phone.from("1", "2"))
        .name("test")
        .build()).build());
    assertThat(message.getRecipient().getEmail()).isEqualTo("a@b.com");
    assertThat(message.getRecipient().getName()).isEqualTo("test");
    assertThat(message.getRecipient().getPhoneId()).isEqualTo("1");
    assertThat(message.getRecipient().getPhoneNumber()).isEqualTo("2");
  }

  private MessageEntity.MessageEntityBuilder entityBuilder() {
    return MessageEntity.builder()
        .id(87L)
        .channel(CommunicationChannelEntity.EMAIL)
        .recipient(RecipientEntity.builder()
            .phoneNumber("78924")
            .build())
        .body("Test");
  }

  private Message.MessageBuilder domainBuilder() {
    return Message.builder()
        .id(MessageId.from(88L))
        .channel(CommunicationChannel.WHATSAPP)
        .recipient(Recipient.builder()
            .phone(Phone.from("78924", "1243243"))
            .build())
        .body(MessageBody.from("Hello!"));
  }

}