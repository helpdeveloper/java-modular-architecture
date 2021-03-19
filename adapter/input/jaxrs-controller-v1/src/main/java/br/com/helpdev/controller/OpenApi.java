package br.com.helpdev.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import java.io.InputStream;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
public class OpenApi {

  @GET
  @Operation(hidden = true)
  public InputStream openApi() {
    return OpenApi.class.getResourceAsStream("openapi.json");
  }
}
