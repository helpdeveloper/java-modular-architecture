package br.com.helpdev.config;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;

@Configuration
@Slf4j
public class WebJerseyConfiguration extends ResourceConfig {

  public WebJerseyConfiguration() {
    var scanner = new ClassPathScanningCandidateComponentProvider(false);
    scanner.addIncludeFilter(new AnnotationTypeFilter(Provider.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(Path.class));
    scanner.findCandidateComponents("br.com.helpdev.controller").forEach(beanDefinition -> {
      try {
        register(Class.forName(beanDefinition.getBeanClassName()));
      } catch (ClassNotFoundException e) {
        log.warn("Failed to register: {}", beanDefinition.getBeanClassName());
      }
    });
  }
}
