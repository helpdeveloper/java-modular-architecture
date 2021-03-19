package br.com.helpdev.controller.handler;

import br.com.helpdev.controller.dto.ErrorDto;
import br.com.helpdev.domain.exception.NotificationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MethodArgumentNotValidExceptionMapper implements ExceptionMapper<NotificationException> {

  @Override
  public Response toResponse(final NotificationException e) {
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(ErrorDto.builder().message(e.getMessage()).build())
        .build();
  }

}
