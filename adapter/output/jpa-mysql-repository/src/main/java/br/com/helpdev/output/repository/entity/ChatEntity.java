package br.com.helpdev.output.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
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
