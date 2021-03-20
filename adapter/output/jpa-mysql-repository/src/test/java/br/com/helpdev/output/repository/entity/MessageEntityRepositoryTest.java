package br.com.helpdev.output.repository.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

  @Test
  void whenExistsThenCallEntityManager() {
    var message = MessageEntity.builder().id(3L).build();
    when(entityManager.find(MessageEntity.class, 3L)).thenReturn(message);
    var result = repository.existsById(3L);
    assertThat(result).isTrue();
    verify(entityManager).find(MessageEntity.class, 3L);
    verifyNoMoreInteractions(entityManager);
  }

  @Test
  void whenDoNotExistsThenCallEntityManager() {
    var result = repository.existsById(3L);
    assertThat(result).isFalse();
    verify(entityManager).find(MessageEntity.class, 3L);
    verifyNoMoreInteractions(entityManager);
  }

  @Test
  void whenFindThenCallEntityManager() {
    var message = MessageEntity.builder().id(4L).build();
    when(entityManager.find(MessageEntity.class, 4L)).thenReturn(message);
    var result = repository.findById(4L);
    assertThat(result).isNotNull()
        .get()
        .satisfies(r -> assertThat(r.getId()).isEqualTo(4L));
    verify(entityManager).find(MessageEntity.class, 4L);
    verifyNoMoreInteractions(entityManager);
  }

  @Test
  void whenCreateThenCallEntityManager() {
    var message = MessageEntity.builder().id(4L).build();
    repository.save(message);
    verify(entityManager).persist(message);
    verifyNoMoreInteractions(entityManager);
  }

}