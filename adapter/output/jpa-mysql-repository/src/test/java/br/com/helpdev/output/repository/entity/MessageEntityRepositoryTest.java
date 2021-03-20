package br.com.helpdev.output.repository.entity;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MessageEntityRepositoryTest {

  @InjectMocks
  private MessageEntityRepository repository;
  @Mock
  private EntityManager entityManager;

  @Test
  void whenDeleteThenCallEntityManager() {
    var message = MessageEntity.builder().id(2L).build();
    when(entityManager.find(MessageEntity.class, 2L)).thenReturn(message);
    repository.deleteById(2L);
    verify(entityManager).find(MessageEntity.class, 2L);
    verify(entityManager).remove(message);
    verifyNoMoreInteractions(entityManager);
  }

}