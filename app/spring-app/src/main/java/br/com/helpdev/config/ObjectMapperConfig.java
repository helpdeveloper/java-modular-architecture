package br.com.helpdev.config;

import br.com.helpdev.controller.config.CustomObjectMapperConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class ObjectMapperConfig {

  @Bean
  public ObjectMapper objectMapper(final Jackson2ObjectMapperBuilder builder) {
    final var objectMapper = builder.build();
    CustomObjectMapperConfig.customize(objectMapper);
    return objectMapper;
  }
}
