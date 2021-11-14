package br.com.helpdev.output.restclient.client;

import br.com.helpdev.output.restclient.client.dto.RandomIdNumberDto;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/id_number/random_id_number")
@RegisterRestClient(configKey = "random-data-api")
public interface RandomDataApiClient {

  @GET
  @Produces("application/json")
  RandomIdNumberDto generate();
}
