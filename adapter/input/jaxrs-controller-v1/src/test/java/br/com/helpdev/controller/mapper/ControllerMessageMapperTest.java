package br.com.helpdev.controller.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.helpdev.controller.dto.CommunicationChannelDto;
import br.com.helpdev.controller.dto.MessageCreateDto;
import br.com.helpdev.controller.dto.RecipientDto;
import br.com.helpdev.controller.dto.StatusResponseDto;
import br.com.helpdev.domain.Chat;
import br.com.helpdev.domain.CommunicationChannel;
import br.com.helpdev.domain.Message;
import br.com.helpdev.domain.Recipient;
import br.com.helpdev.domain.Status;
import br.com.helpdev.domain.vo.MessageBody;
import br.com.helpdev.domain.vo.MessageId;
import br.com.helpdev.domain.vo.Phone;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ControllerMessageMapperTest {

  private final ControllerMessageMapper mapper = new ControllerMessageMapper();

  @Test
  void whenBodyHasEntityThenConvertToDto() {
    var message = fakeBuilder()
        .body(MessageBody.from("Test"))
        .build();
    var dto = mapper.toDto(message);
    assertThat(dto).isNotNull();
    assertThat(dto.getBody()).isEqualTo("Test");
  }

  @Test
  void whenHasBodyInCreateDtoThenConvertToEntity() {
    var message = fakeDto()
        .body("Test")
        .build();
    var entity = mapper.toDomain(message);
    assertThat(entity).isNotNull();
    assertThat(entity.getBody().value()).isEqualTo("Test");
  }

  @Test
  void whenDateHasEntityThenConvertToDto() {
    var date = ZonedDateTime.now();
    var message = fakeBuilder()
        .scheduleDate(date)
        .build();
    var dto = mapper.toDto(message);
    assertThat(dto).isNotNull();
    assertThat(dto.getScheduleDate()).isEqualTo(date);
  }


  @Test
  void whenEntityHasWhatsAppThenConvertToDto() {
    var message = fakeBuilder()
        .channel(CommunicationChannel.WHATSAPP)
        .build();
    var dto = mapper.toDto(message);
    assertThat(dto).isNotNull();
    assertThat(dto.getChannel()).isEqualTo(CommunicationChannelDto.WHATSAPP);
  }

  @Test
  void whenHasDateInCreateDtoThenConvertToEntity() {
    var date = ZonedDateTime.now();
    var message = fakeDto()
        .scheduleDate(date)
        .build();
    var entity = mapper.toDomain(message);
    assertThat(entity).isNotNull();
    assertThat(entity.getScheduleDate()).isEqualTo(date);
  }

  @Test
  void whenHasWhatsAppInCreateDtoThenConvertToEntity() {
    var message = fakeDto()
        .channel(CommunicationChannelDto.WHATSAPP)
        .build();
    var entity = mapper.toDomain(message);
    assertThat(entity).isNotNull();
    assertThat(entity.getChannel()).isEqualTo(CommunicationChannel.WHATSAPP);
  }

  @Test
  void whenEntityHasEmailThenConvertToDto() {
    var message = fakeBuilder()
        .channel(CommunicationChannel.EMAIL)
        .build();
    var dto = mapper.toDto(message);
    assertThat(dto).isNotNull();
    assertThat(dto.getChannel()).isEqualTo(CommunicationChannelDto.EMAIL);
  }

  @Test
  void whenHasEmailInCreateDtoThenConvertToEntity() {
    var message = fakeDto()
        .channel(CommunicationChannelDto.EMAIL)
        .build();
    var entity = mapper.toDomain(message);
    assertThat(entity).isNotNull();
    assertThat(entity.getChannel()).isEqualTo(CommunicationChannel.EMAIL);
  }


  @Test
  void whenEntityHasSmsThenConvertToDto() {
    var message = fakeBuilder()
        .channel(CommunicationChannel.SMS)
        .build();
    var dto = mapper.toDto(message);
    assertThat(dto).isNotNull();
    assertThat(dto.getChannel()).isEqualTo(CommunicationChannelDto.SMS);
  }

  @Test
  void whenHasSmsInCreateDtoThenConvertToEntity() {
    var message = fakeDto()
        .channel(CommunicationChannelDto.SMS)
        .build();
    var entity = mapper.toDomain(message);
    assertThat(entity).isNotNull();
    assertThat(entity.getChannel()).isEqualTo(CommunicationChannel.SMS);
  }

  @Test
  void whenEntityHasPushThenConvertToDto() {
    var message = fakeBuilder()
        .channel(CommunicationChannel.PUSH)
        .build();
    var dto = mapper.toDto(message);
    assertThat(dto).isNotNull();
    assertThat(dto.getChannel()).isEqualTo(CommunicationChannelDto.PUSH);
  }

  @Test
  void whenHasPushInCreateDtoThenConvertToEntity() {
    var message = fakeDto()
        .channel(CommunicationChannelDto.PUSH)
        .build();
    var entity = mapper.toDomain(message);
    assertThat(entity).isNotNull();
    assertThat(entity.getChannel()).isEqualTo(CommunicationChannel.PUSH);
  }

  @Test
  void whenWaitingStatusOnChannelThenConvertToDto() {
    var message = fakeBuilder()
        .chats(List.of(Chat.builder().status(Status.WAITING).build()))
        .build();
    var dto = mapper.toDto(message);
    assertThat(dto).isNotNull();
    assertThat(dto.getChats()).hasSize(1).first().satisfies(
        c -> assertThat(c.getStatus()).isEqualTo(StatusResponseDto.WAITING)
    );
  }

  @Test
  void whenSentStatusOnChannelThenConvertToDto() {
    var message = fakeBuilder()
        .chats(List.of(Chat.builder().status(Status.SENT).build()))
        .build();
    var dto = mapper.toDto(message);
    assertThat(dto).isNotNull();
    assertThat(dto.getChats()).hasSize(1).first().satisfies(
        c -> assertThat(c.getStatus()).isEqualTo(StatusResponseDto.SENT)
    );
  }

  @Test
  void whenSendingStatusOnChannelThenConvertToDto() {
    var message = fakeBuilder()
        .chats(List.of(Chat.builder().status(Status.SENDING).build()))
        .build();
    var dto = mapper.toDto(message);
    assertThat(dto).isNotNull();
    assertThat(dto.getChats()).hasSize(1).first().satisfies(
        c -> assertThat(c.getStatus()).isEqualTo(StatusResponseDto.SENDING)
    );
  }

  @Test
  void whenDateOnChannelThenConvertToDto() {
    var date = ZonedDateTime.now();
    var message = fakeBuilder()
        .chats(List.of(Chat.builder().date(date).build()))
        .build();
    var dto = mapper.toDto(message);
    assertThat(dto).isNotNull();
    assertThat(dto).isNotNull();
    assertThat(dto.getChats()).hasSize(1).first().satisfies(c ->
        {
          assertThat(c.getDate()).isEqualTo(date);
          assertThat(c.getStatus()).isEqualTo(StatusResponseDto.WAITING);
        }
    );
  }

  @Test
  void whenHasIdThenConvertToDto() {
    var message = fakeBuilder()
        .id(MessageId.from(123L))
        .build();
    var dto = mapper.toDto(message);
    assertThat(dto).isNotNull();
    assertThat(dto.getId()).isEqualTo(123L);
  }

  @Test
  void whenEntityHasRecipientNameThenConvertToDto() {
    var message = fakeBuilder()
        .recipient(Recipient.builder()
            .name("Alisson")
            .phone(Phone.newNumber("123"))
            .build())
        .build();
    var dto = mapper.toDto(message);
    assertThat(dto).isNotNull();
    assertThat(dto.getRecipient().getName()).isEqualTo("Alisson");
  }

  @Test
  void whenHasRecipientNameInCreateDtoThenConvertToEntity() {
    var message = MessageCreateDto.builder()
        .body("empty")
        .recipient(RecipientDto.builder()
            .name("Alisson")
            .phoneNumber("123")
            .build())
        .build();
    var entity = mapper.toDomain(message);
    assertThat(entity).isNotNull();
    assertThat(entity.getRecipient().getName()).isEqualTo("Alisson");
  }

  @Test
  void whenEntityHasRecipientEmailThenConvertToDto() {
    var message = fakeBuilder()
        .recipient(Recipient.builder()
            .email("alisson@mail.com")
            .phone(Phone.newNumber("123"))
            .build())
        .build();
    var dto = mapper.toDto(message);
    assertThat(dto).isNotNull();
    assertThat(dto.getRecipient().getEmail()).isEqualTo("alisson@mail.com");
  }

  @Test
  void whenHasRecipientEmailInCreateDtoThenConvertToEntity() {
    var message = MessageCreateDto.builder().body("empty")
        .recipient(RecipientDto.builder()
            .phoneNumber("123")
            .email("alisson@mail.com")
            .build())
        .build();
    var entity = mapper.toDomain(message);
    assertThat(entity).isNotNull();
    assertThat(entity.getRecipient().getEmail()).isEqualTo("alisson@mail.com");
  }

  @Test
  void whenEntityHasRecipientPhoneIdThenConvertToDto() {
    var message = fakeBuilder()
        .recipient(Recipient.builder()
            .phone(Phone.from("123", "16000000"))
            .build())
        .build();
    var dto = mapper.toDto(message);
    assertThat(dto).isNotNull();
    assertThat(dto.getRecipient().getPhoneId()).isEqualTo("123");
  }

  @Test
  void whenHasRecipientPhoneIdInCreateDtoThenConvertToEntity() {
    var message = MessageCreateDto.builder().body("empty")
        .recipient(RecipientDto.builder()
            .phoneId("123")
            .phoneNumber("123")
            .build())
        .build();
    var entity = mapper.toDomain(message);
    assertThat(entity).isNotNull();
    assertThat(entity.getRecipient().getPhone().getPhoneId()).isEqualTo(Optional.of("123"));
  }

  @Test
  void whenEntityHasRecipientPhoneNumberThenConvertToDto() {
    var message = fakeBuilder()
        .recipient(Recipient.builder()
            .phone(Phone.from("123", "999988887777"))
            .build())
        .build();
    var dto = mapper.toDto(message);
    assertThat(dto).isNotNull();
    assertThat(dto.getRecipient().getPhoneNumber()).isEqualTo("999988887777");
  }

  @Test
  void whenHasRecipientPhoneNumberInCreateDtoThenConvertToEntity() {
    var message = MessageCreateDto.builder().body("body")
        .recipient(RecipientDto.builder()
            .phoneNumber("999988887777")
            .build())
        .build();
    var entity = mapper.toDomain(message);
    assertThat(entity).isNotNull();
    assertThat(entity.getRecipient().getPhone().getPhoneNumber()).isEqualTo("999988887777");
  }

  private MessageCreateDto.MessageCreateDtoBuilder fakeDto() {
    return MessageCreateDto.builder().body("empty")
        .recipient(RecipientDto.builder()
            .phoneNumber("123")
            .build());
  }

  private Message.MessageBuilder fakeBuilder() {
    return Message.builder().body(MessageBody.from("empty"))
        .recipient(Recipient.builder()
            .phone(Phone.newNumber("123123"))
            .build())
        .id(MessageId.from(1L));
  }
  
}
