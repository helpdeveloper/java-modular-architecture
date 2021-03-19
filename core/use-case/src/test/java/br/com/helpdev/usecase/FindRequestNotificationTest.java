package br.com.helpdev.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.helpdev.domain.Message;
import br.com.helpdev.domain.Recipient;
import br.com.helpdev.domain.exception.NotificationException;
import br.com.helpdev.domain.vo.MessageBody;
import br.com.helpdev.domain.vo.MessageId;
import br.com.helpdev.usecase.port.MessageRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindRequestNotificationTest {

  @InjectMocks
  private FindRequestNotification findRequestNotification;
  @Mock
  private MessageRepository repository;

  @Test
  public void whenFindMessageThenReturn() throws NotificationException {
    var id = 3L;
    var messageId = MessageId.from(id);
    when(repository.find(messageId)).thenReturn(Optional.of(fakeBuilder(messageId).build()));
    var message = findRequestNotification.find(messageId);
    assertThat(message).isNotNull()
        .satisfies(m -> assertThat(m.getId().value()).isEqualTo(id));
    verify(repository).find(messageId);
  }

  private Message.MessageBuilder fakeBuilder(final MessageId messageId) {
    return Message.builder()
        .body(MessageBody.from("empty"))
        .recipient(Recipient.builder().build())
        .id(messageId);
  }

  @Test
  public void whenHasNoMessageThenReturnNotFound() throws NotificationException {
    var id = 4L;
    var messageId = MessageId.from(id);
    when(repository.find(messageId)).thenReturn(Optional.empty());
    assertThatThrownBy(() -> findRequestNotification.find(messageId))
        .hasMessage("Mensagem com o id: " + id + " não foi encontrada, confira se o identificador está correto e tente novamente.");
  }

}
