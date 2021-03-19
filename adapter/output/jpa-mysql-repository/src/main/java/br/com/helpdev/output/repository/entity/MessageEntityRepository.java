package br.com.helpdev.output.repository.entity;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
