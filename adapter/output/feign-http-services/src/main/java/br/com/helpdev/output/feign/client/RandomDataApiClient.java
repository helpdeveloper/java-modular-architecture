package br.com.helpdev.output.feign.client;

import br.com.helpdev.output.feign.client.dto.RandomIdNumberDto;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "random-data-api", url = "${random-data-api.url}", path = "/api/id_number/random_id_number")
public interface RandomDataApiClient {

  @GET
  @Produces("application/json")
  RandomIdNumberDto generate();
}
