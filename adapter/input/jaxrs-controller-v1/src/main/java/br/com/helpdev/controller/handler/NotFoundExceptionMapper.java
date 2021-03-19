package br.com.helpdev.controller.handler;

import br.com.helpdev.controller.dto.ErrorDto;
import br.com.helpdev.usecase.exception.MessageNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<MessageNotFoundException> {

  @Override
  public Response toResponse(final MessageNotFoundException e) {
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ErrorDto.builder().message(e.getMessage()).build())
        .build();
  }

}
