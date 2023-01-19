package br.com.helpdev.output.feign.config;

import static org.assertj.core.api.Assertions.assertThat;

import feign.jaxrs.JAXRSContract;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FeignContractConfigurationTest {
  @InjectMocks
  private FeignContractConfiguration feignContractConfiguration;

  @Test
  void shouldGeneratedNonNullJaxRsContract() {
    final var contract = feignContractConfiguration.contract();

    assertThat(contract)
        .isInstanceOf(JAXRSContract.class)
        .isNotNull();
  }
}