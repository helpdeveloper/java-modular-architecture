package br.com.helpdev.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.helpdev.domain.Message;
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

  @Mock
  private MessageRepository repository;

  @InjectMocks
  private FindRequestNotification findRequestNotification;

  @Test
  void shouldBeFindMessageWhenMessageIdExists() {
    final var messageId = MessageId.from(12L);
    final var message = mock(Message.class);

    when(repository.find(messageId)).thenReturn(Optional.of(message));

    final var response = findRequestNotification.find(messageId);

    verify(repository, times(1))
        .find(messageId);
    assertThat(response)
        .isEqualTo(message);
  }

  @Test
  void shouldThrowsMessageNotFoundExceptionWhenNotFoundId() {
    final var messageId = MessageId.from(12L);

    when(repository.find(messageId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> findRequestNotification.find(messageId))
        .hasMessage("Message with Id " + messageId.value() + " not found");

    verify(repository, times(1))
        .find(messageId);
  }
}