package br.com.helpdev.controller;

import br.com.helpdev.controller.dto.MessageCreateDto;
import br.com.helpdev.controller.dto.MessageResponseDto;
import br.com.helpdev.controller.mapper.ControllerMessageMapper;
import br.com.helpdev.domain.exception.NotificationException;
import br.com.helpdev.domain.vo.MessageId;
import br.com.helpdev.usecase.DeleteRequestNotification;
import br.com.helpdev.usecase.FindRequestNotification;
import br.com.helpdev.usecase.PushRequestNotification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/v1/message")
@Produces(MediaType.APPLICATION_JSON)
public class MessageController {

  private final FindRequestNotification findRequestNotification;
  private final PushRequestNotification pushRequestNotification;
  private final DeleteRequestNotification deleteRequestNotification;
  private final ControllerMessageMapper mapper;

  @Inject
  MessageController(final FindRequestNotification findRequestNotification,
                           final PushRequestNotification pushRequestNotification,
                           final DeleteRequestNotification deleteRequestNotification,
                           final ControllerMessageMapper mapper) {
    this.findRequestNotification = findRequestNotification;
    this.pushRequestNotification = pushRequestNotification;
    this.deleteRequestNotification = deleteRequestNotification;
    this.mapper = mapper;
  }

  @Operation(summary = "Create a new message",
      responses = {
          @ApiResponse(description = "The message",
              content = @Content(mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = MessageResponseDto.class))))
      })
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response create(final MessageCreateDto messageDto) throws NotificationException {
    var entity = mapper.toDomain(messageDto);
    var message = pushRequestNotification.push(entity);
    return Response.status(Response.Status.CREATED)
        .entity(mapper.toDto(message))
        .build();
  }

  @Operation(summary = "Get message by id",
      responses = {
          @ApiResponse(description = "The message",
              content = @Content(mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = MessageResponseDto.class)))),
          @ApiResponse(responseCode = "404", description = "Order not found")})
  @GET
  @Path("/{id}")
  public Response find(@PathParam("id") final Long id) throws NotificationException {
    var message = findRequestNotification.find(MessageId.from(id));
    return Response.ok(mapper.toDto(message)).build();
  }

  @Operation(summary = "Delete message by id",
      responses = {
          @ApiResponse(description = "The message"),
          @ApiResponse(responseCode = "404", description = "Order not found")})
  @DELETE
  @Path("/{id}")
  public Response delete(@PathParam("id") final Long id) throws NotificationException {
    deleteRequestNotification.delete(MessageId.from(id));
    return Response.noContent().build();
  }


}
