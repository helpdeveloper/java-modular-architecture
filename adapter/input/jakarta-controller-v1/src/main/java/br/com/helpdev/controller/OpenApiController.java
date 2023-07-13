package br.com.helpdev.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.inject.Named;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.InputStream;

@Named
@Path("/v1/openapi")
@Produces(MediaType.APPLICATION_JSON)
@OpenAPIDefinition(
    info = @Info(
        title = "Sample API",
        version = "1",
        description = "Sample API",
        contact = @Contact(url = "https://helpdev.com.br", name = "HelpDev", email = "contato@helpdev.com.br")
    )
)
public class OpenApiController {

  @GET
  @Operation(hidden = true)
  public InputStream openApi() {
    return OpenApiController.class.getResourceAsStream("openapi.json");
  }

}
