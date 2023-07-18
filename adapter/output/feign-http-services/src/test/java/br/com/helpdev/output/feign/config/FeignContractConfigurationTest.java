package br.com.helpdev.output.feign.config;

import feign.jaxrs.JakartaContract;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FeignContractConfigurationTest {
  @InjectMocks
  private FeignContractConfiguration feignContractConfiguration;

  @Test
  void shouldGeneratedNonNullJaxRsContract() {
    final var contract = feignContractConfiguration.contract();

    assertThat(contract)
        .isInstanceOf(JakartaContract.class)
        .isNotNull();
  }
}