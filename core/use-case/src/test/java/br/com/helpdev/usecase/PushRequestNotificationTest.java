package br.com.helpdev.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.helpdev.domain.CommunicationChannel;
import br.com.helpdev.domain.Message;
import br.com.helpdev.domain.Recipient;
import br.com.helpdev.domain.Status;
import br.com.helpdev.domain.exception.NotificationException;
import br.com.helpdev.domain.vo.MessageBody;
import br.com.helpdev.domain.vo.Phone;
import br.com.helpdev.usecase.port.MessageRepository;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PushRequestNotificationTest {

  @InjectMocks
  private PushRequestNotification pushRequestNotification;
  @Mock
  private MessageRepository repository;

  @Test
  public void whenReceiveNotificationThenSchedule() throws NotificationException {
    when(repository.create(any())).thenAnswer(a -> a.getArgument(0));
    var message = pushRequestNotification.push(Message.builder()
        .body(MessageBody.from("Test"))
        .scheduleDate(ZonedDateTime.now())
        .channel(CommunicationChannel.WHATSAPP)
        .recipient(Recipient.builder()
            .name("João Graça")
            .phone(Phone.newNumber("047 977665544"))
            .build())
        .build());
    assertThat(message).isNotNull()
        .satisfies(m -> assertThat(m.getChats()).hasSize(1)
            .first()
            .satisfies(c -> {
              assertThat(c.getStatus()).isEqualTo(Status.WAITING);
              assertThat(c.getDate()).isCloseTo(ZonedDateTime.now(), within(1, ChronoUnit.SECONDS));
            }));
    verify(repository).create(message);
  }

}
