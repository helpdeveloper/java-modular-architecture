package br.com.helpdev.output.restclient.client.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RandomIdNumberDto {
  private String id;
  private String uid;
  private String validUsSsn;
  private String invalidUsSsn;
}
