package br.com.helpdev.quarkus;

import br.com.helpdev.controller.config.CustomObjectMapperConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.jackson.ObjectMapperCustomizer;
import javax.inject.Singleton;

@Singleton
public class ObjectMapperConfig implements ObjectMapperCustomizer {

  public void customize(final ObjectMapper mapper) {
    CustomObjectMapperConfig.customize(mapper);
  }

}
