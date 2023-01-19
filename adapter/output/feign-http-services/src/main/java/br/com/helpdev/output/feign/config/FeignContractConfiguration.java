package br.com.helpdev.output.feign.config;

import feign.Contract;
import feign.jaxrs.JAXRSContract;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableFeignClients(
    basePackages = {"br.com.helpdev.output.feign"}
)
public class FeignContractConfiguration {

  @Bean
  Contract contract() {
    return new JAXRSContract();
  }

}
