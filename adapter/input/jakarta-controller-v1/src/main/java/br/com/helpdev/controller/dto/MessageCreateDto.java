package br.com.helpdev.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageCreateDto {

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
  @NotNull(message = "Schedule date can't be null.")
  private ZonedDateTime scheduleDate;
  @NotBlank(message = "Body can't be null.")
  private String body;
  @Valid
  @NotNull(message = "Message need a recipient!")
  private RecipientDto recipient;
  @NotNull(message = "Channel can't be null.")
  private CommunicationChannelDto channel;

}

