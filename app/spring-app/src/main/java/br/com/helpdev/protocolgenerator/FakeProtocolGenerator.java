package br.com.helpdev.protocolgenerator;

import br.com.helpdev.usecase.port.ProtocolGeneratorClient;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class FakeProtocolGenerator implements ProtocolGeneratorClient {

  public static final Random RANDOM = new Random();

  @Override
  public String generateNewProtocol() {
    return "PROT-" + RANDOM.nextInt(Integer.MAX_VALUE);
  }
}
