package br.com.helpdev.usecase.port;

import br.com.helpdev.domain.Message;
import br.com.helpdev.domain.vo.MessageId;
import java.util.Optional;

public interface MessageRepository {

  Message create(Message message);

  void delete(MessageId id);

  Optional<Message> find(MessageId id);

  boolean exists(MessageId id);

}
