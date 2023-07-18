package br.com.helpdev.output.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipientEntity {

  @Column(name = "message_recipient_name")
  private String name;
  @Column(name = "message_recipient_email")
  private String email;
  @Column(name = "message_recipient_phone_number")
  private String phoneNumber;
  @Column(name = "message_recipient_phone_id")
  private String phoneId;

}
