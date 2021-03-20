package br.com.helpdev.usecase;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.helpdev.domain.vo.MessageId;
import br.com.helpdev.usecase.port.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class DeleteRequestNotificationTest {

  @Mock
  private MessageRepository repository;

  @InjectMocks
  private DeleteRequestNotification deleteRequestNotification;

  @Test
  void shouldBeDeleteMessageWhenMessageIdExists() {
    final var messageId = MessageId.from(12L);

    when(repository.exists(messageId)).thenReturn(true);
    doNothing().when(repository).delete(messageId);

    deleteRequestNotification.delete(messageId);

    verify(repository, times(1))
        .exists(messageId);
    verify(repository, times(1))
        .delete(messageId);
  }

  @Test
  void shouldThrowsMessageNotFoundExceptionWhenNotFoundId() {
    final var messageId = MessageId.from(12L);

    when(repository.exists(messageId)).thenReturn(false);

    assertThatThrownBy(() -> deleteRequestNotification.delete(messageId))
        .hasMessage("Message id: " + messageId.value() + " dont exists");

    verify(repository, times(1))
        .exists(messageId);
  }


}