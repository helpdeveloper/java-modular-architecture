package br.com.helpdev.output.repository;

import static org.mockito.Mockito.verify;

import br.com.helpdev.domain.vo.MessageId;
import br.com.helpdev.output.repository.entity.MessageEntityRepository;
import br.com.helpdev.output.repository.mapper.MessageMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MessageGatewayTest {

  @InjectMocks
  private MessageGateway messageGateway;
  @Mock
  private MessageEntityRepository repository;
  @Spy
  private MessageMapper messageMapper = new MessageMapper();

  @Test
  void wheDeleteThenCallRepository() {
    messageGateway.delete(MessageId.from(25L));
    verify(repository).deleteById(25L);
  }

}