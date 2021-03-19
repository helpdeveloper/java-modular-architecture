package br.com.helpdev.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.helpdev.controller.dto.MessageCreateDto;
import br.com.helpdev.controller.dto.MessageResponseDto;
import br.com.helpdev.controller.dto.RecipientDto;
import br.com.helpdev.controller.mapper.ControllerMessageMapper;
import br.com.helpdev.domain.Message;
import br.com.helpdev.domain.Recipient;
import br.com.helpdev.domain.exception.NotificationException;
import br.com.helpdev.domain.vo.MessageBody;
import br.com.helpdev.domain.vo.MessageId;
import br.com.helpdev.domain.vo.Phone;
import br.com.helpdev.usecase.DeleteRequestNotification;
import br.com.helpdev.usecase.FindRequestNotification;
import br.com.helpdev.usecase.PushRequestNotification;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

  @InjectMocks
  private MessageController messageController;
  @Mock
  private FindRequestNotification findRequestNotification;
  @Mock
  private PushRequestNotification pushRequestNotification;
  @Mock
  private DeleteRequestNotification deleteRequestNotification;
  @Spy
  private final ControllerMessageMapper mapper = new ControllerMessageMapper();

  @Test
  public void whenDeleteTheReturn204() throws NotificationException {
    var id = 1L;
    var messageId = MessageId.from(id);
    var response = messageController.delete(id);
    assertThat(response.getStatus()).isEqualTo(204);
    verify(deleteRequestNotification).delete(messageId);
    verify(mapper, never()).toEntity(any());
    verify(mapper, never()).toDto(any());
  }

  @Test
  public void whenFindByIdTheReturn200() throws NotificationException {
    var id = 2L;
    var messageId = MessageId.from(id);
    when(findRequestNotification.find(messageId)).thenReturn(Message.builder()
        .recipient(Recipient.builder()
            .name("jorge")
            .phone(Phone.newNumber("123"))
            .build())
        .id(messageId)
        .body(MessageBody.from("body"))
        .build());
    var response = messageController.find(id);
    assertThat(response.getEntity()).isEqualTo(MessageResponseDto.builder()
        .id(id)
        .recipient(RecipientDto.builder()
            .name("jorge")
            .phoneNumber("123")
            .build())
        .body("body")
        .chats(Collections.emptyList())
        .build());
    assertThat(response.getStatus()).isEqualTo(200);
    verify(findRequestNotification).find(messageId);
    verify(mapper, never()).toEntity(any());
    verify(mapper).toDto(any());
  }

  @Test
  public void whenCreateTheReturn201() throws NotificationException {
    var id = 4L;
    var messageId = MessageId.from(id);
    var body = "Olá";
    var messageBody = MessageBody.from(body);
    when(pushRequestNotification.push(any())).thenReturn(Message.builder()
        .recipient(Recipient.builder()
            .name("jorge")
            .phone(Phone.newNumber("123"))
            .build())
        .id(messageId)
        .body(messageBody)
        .build());

    var response = messageController.create(MessageCreateDto.builder()
        .body(body)
        .recipient(RecipientDto.builder()
            .name("jorge")
            .phoneNumber("123")
            .build())
        .build());

    assertThat(response.getEntity()).isEqualTo(MessageResponseDto.builder()
        .id(id).recipient(RecipientDto.builder()
            .name("jorge")
            .phoneNumber("123")
            .build())
        .body(body)
        .chats(Collections.emptyList())
        .build());
    assertThat(response.getStatus()).isEqualTo(201);
    verify(pushRequestNotification).push(any());
    verify(mapper).toEntity(any());
    verify(mapper).toDto(any());
  }

}
