package br.com.helpdev.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.ZonedDateTime;
import java.util.Collection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponseDto {

  private Long id;
  private String protocol;
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
  private ZonedDateTime scheduleDate;
  private String body;
  private RecipientDto recipient;
  private CommunicationChannelDto channel;
  private Collection<ChatResponseDto> chats;
}

