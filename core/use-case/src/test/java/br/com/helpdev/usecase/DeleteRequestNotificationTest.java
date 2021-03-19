package br.com.helpdev.usecase;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.helpdev.domain.exception.NotificationException;
import br.com.helpdev.domain.vo.MessageId;
import br.com.helpdev.usecase.port.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteRequestNotificationTest {

  @InjectMocks
  private DeleteRequestNotification deleteRequestNotification;
  @Mock
  private MessageRepository repository;

  @Test
  public void whenFindMessageThenReturn() throws NotificationException {
    var id = 2L;
    var messageId = MessageId.from(id);
    when(repository.exists(messageId)).thenReturn(true);
    deleteRequestNotification.delete(messageId);
    verify(repository).exists(messageId);
    verify(repository).delete(messageId);
  }

  @Test
  public void whenHasNoMessageThenReturnNotFound() throws NotificationException {
    var id = 1L;
    var messageId = MessageId.from(id);
    when(repository.exists(messageId)).thenReturn(false);
    assertThatThrownBy(() -> deleteRequestNotification.delete(messageId))
        .hasMessage("Mensagem com o id: " + id + " não existe ou já foi removida, confira se o identificador está correto.");
    verify(repository).exists(messageId);
    verify(repository, never()).delete(messageId);
    Mockito.verifyNoMoreInteractions(repository);
  }

}
