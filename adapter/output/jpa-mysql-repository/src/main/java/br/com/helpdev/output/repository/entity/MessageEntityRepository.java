package br.com.helpdev.output.repository.entity;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.Optional;

@Named
@ApplicationScoped
public class MessageEntityRepository {

  private final EntityManager entityManager;

  @Inject
  public MessageEntityRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional
  public void save(MessageEntity entity) {
    entityManager.persist(entity);
  }

  @Transactional
  public void deleteById(Long id) {
    MessageEntity entity = entityManager.find(MessageEntity.class, id);
    entityManager.remove(entity);
  }

  public Optional<MessageEntity> findById(Long id) {
    MessageEntity entity = entityManager.find(MessageEntity.class, id);
    return Optional.ofNullable(entity);
  }

  public boolean existsById(Long id) {
    return entityManager.find(MessageEntity.class, id) != null;
  }

}
