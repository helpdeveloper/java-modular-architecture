package br.com.helpdev.output.repository.entity;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat_entity")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "chat_status")
  @Enumerated(EnumType.STRING)
  private StatusEntity status;
  @Column(name = "chat_date")
  private ZonedDateTime date;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "message_id", referencedColumnName = "id")
  private MessageEntity message;

}
