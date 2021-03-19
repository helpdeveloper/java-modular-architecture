package br.com.helpdev.output.repository.entity;

import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
  @Embedded
  private RecipientEntity recipient;
  @Column(name = "message_channel")
  @Enumerated(EnumType.STRING)
  private CommunicationChannelEntity channel;
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "message", cascade = CascadeType.ALL)
  private List<ChatEntity> chats;

}
