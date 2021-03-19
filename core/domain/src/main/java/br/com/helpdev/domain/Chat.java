package br.com.helpdev.domain;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Chat {

  @EqualsAndHashCode.Include
  private final Long id;
  private final Status status;
  private final ZonedDateTime date;

}
