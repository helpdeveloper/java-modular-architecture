package br.com.helpdev.controller.handler;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.helpdev.controller.dto.ErrorDto;
import br.com.helpdev.domain.exception.NotificationException;
import org.junit.jupiter.api.Test;

class NotificationExceptionMapperTest {

  @Test
  void whenReturnErrorThenCreateResponse() {
    var mapper = new NotificationExceptionMapper();
    var response = mapper.toResponse(new NotificationException("Error"));
    assertThat(response.getStatus()).isEqualTo(400);
    assertThat(response.getEntity()).isEqualTo(ErrorDto.builder().message("Error").build());
  }

}