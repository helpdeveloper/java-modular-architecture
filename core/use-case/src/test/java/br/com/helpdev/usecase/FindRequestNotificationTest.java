package br.com.helpdev.usecase;

import br.com.helpdev.usecase.port.MessageRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindRequestNotificationTest {

  @Mock
  private MessageRepository repository;

  @InjectMocks
  private FindRequestNotification deleteRequestNotification;
}