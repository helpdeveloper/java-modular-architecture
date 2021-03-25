package br.com.helpdev.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class WebJerseyConfiguration extends ResourceConfig {

  public WebJerseyConfiguration() {
    packages("br.com.helpdev.controller");
  }
}
