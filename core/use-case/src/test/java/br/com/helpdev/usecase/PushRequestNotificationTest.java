package br.com.helpdev.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.helpdev.domain.Message;
import br.com.helpdev.domain.Recipient;
import br.com.helpdev.domain.Status;
import br.com.helpdev.domain.vo.MessageBody;
import br.com.helpdev.domain.vo.MessageId;
import br.com.helpdev.domain.vo.Phone;
import br.com.helpdev.usecase.port.MessageRepository;
import br.com.helpdev.usecase.port.ProtocolGeneratorClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PushRequestNotificationTest {

  @Mock
  private MessageRepository repository;

  @Mock
  private ProtocolGeneratorClient protocolGeneratorClient;

  @InjectMocks
  private PushRequestNotification deleteRequestNotification;

  @Test
  void shouldBeCreateMessageWithSuccess() {
    final var message = fakeBuilder().build();
    final var requiredResponse = mock(Message.class);
    final var protocol = "xpto-protocol";

    when(repository.create(message))
        .then(invocationOnMock -> {
          Message messageInCreate = invocationOnMock.getArgument(0);
          assertThat(messageInCreate.getChats())
              .isNotNull()
              .allMatch(x -> x.getStatus().equals(Status.WAITING))
              .hasSize(1);
          return requiredResponse;
        });
    when(protocolGeneratorClient.generateNewProtocol()).thenReturn(protocol);

    final var response = deleteRequestNotification.push(message);

    assertThat(response)
        .isEqualTo(requiredResponse);
  }

  private Message.MessageBuilder fakeBuilder() {
    return Message.builder().body(MessageBody.from("empty"))
        .recipient(Recipient.builder()
            .phone(Phone.newNumber("123123"))
            .build())
        .id(MessageId.from(1L));
  }
}