package br.com.helpdev.output.feign;


import br.com.helpdev.output.feign.client.RandomDataApiClient;
import br.com.helpdev.usecase.port.ProtocolGeneratorClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@ApplicationScoped
@Named
public class ProtocolGeneratorClientImpl implements ProtocolGeneratorClient {

  private final RandomDataApiClient randomDataApiClient;

  @Inject
  public ProtocolGeneratorClientImpl(final RandomDataApiClient randomDataApiClient) {
    this.randomDataApiClient = randomDataApiClient;
  }

  @Override
  public String generateNewProtocol() {
    /* Generate US-SSN to simulate protocol */
    return randomDataApiClient.generate().getValidUsSsn();
  }

}
