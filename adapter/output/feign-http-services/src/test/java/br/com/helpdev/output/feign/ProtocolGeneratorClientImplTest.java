package br.com.helpdev.output.feign;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.helpdev.output.feign.client.RandomDataApiClient;
import br.com.helpdev.output.feign.client.dto.RandomIdNumberDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProtocolGeneratorClientImplTest {

  @Mock
  private RandomDataApiClient randomDataApiClient;

  @InjectMocks
  private ProtocolGeneratorClientImpl protocolGeneratorClient;

  @Test
  void shouldGeneratedProtocolWithSuccess() {
    final var randomIdNumberDto = mock(RandomIdNumberDto.class);
    final var expectedProtocol = "xpto";

    when(randomDataApiClient.generate()).thenReturn(randomIdNumberDto);
    when(randomIdNumberDto.getValidUsSsn()).thenReturn(expectedProtocol);

    final var result = protocolGeneratorClient.generateNewProtocol();

    Assertions.assertThat(result).isEqualTo(expectedProtocol);
  }

}