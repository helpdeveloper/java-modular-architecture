package br.com.helpdev.controller.handler;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.helpdev.controller.dto.ErrorDto;
import br.com.helpdev.usecase.exception.MessageNotFoundException;
import org.junit.jupiter.api.Test;

class NotFoundExceptionMapperTest {

  @Test
  void whenReturnErrorThenCreateResponse() {
    var mapper = new NotFoundExceptionMapper();
    var response = mapper.toResponse(new MessageNotFoundException("Error"));
    assertThat(response.getStatus()).isEqualTo(404);
    assertThat(response.getEntity()).isEqualTo(ErrorDto.builder().message("Error").build());
  }

}