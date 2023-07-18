package br.com.helpdev.output.repository.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "message_entity")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "message_schedule_date")
  private ZonedDateTime scheduleDate;
  @Column(name = "message_body")
  private String body;
  @Column(name = "protocol")
  private String protocol;
  @Embedded
  private RecipientEntity recipient;
  @Column(name = "message_channel")
  @Enumerated(EnumType.STRING)
  private CommunicationChannelEntity channel;
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "message", cascade = CascadeType.ALL)
  private List<ChatEntity> chats;

}
