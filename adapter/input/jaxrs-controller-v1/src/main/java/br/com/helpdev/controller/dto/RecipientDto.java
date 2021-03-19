package br.com.helpdev.controller.dto;

import br.com.helpdev.domain.Recipient;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@Builder
public class RecipientDto {

  @NotBlank(message = "Recipient name can't be null!")
  private String name;
  @NotBlank(message = "Recipient email can't be null!")
  private String email;
  @NotBlank(message = "Recipient phone number can't be null!")
  private String phoneNumber;
  @NotBlank(message = "Recipient phone id can't be null!")
  private String phoneId;

}
