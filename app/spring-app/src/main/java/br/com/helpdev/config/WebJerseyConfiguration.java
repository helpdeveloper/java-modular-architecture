package br.com.helpdev.config;

import java.util.Objects;
import java.util.stream.Collectors;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

@Configuration
public class WebJerseyConfiguration extends ResourceConfig {

  public WebJerseyConfiguration() {
    packages("br.com.helpdev.controller");
    var scanner = new ClassPathScanningCandidateComponentProvider(false);
    scanner.addIncludeFilter(new AnnotationTypeFilter(Provider.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(Path.class));
    registerClasses(
        scanner.findCandidateComponents("br.com.helpdev.controller").stream()
            .map(
                beanDefinition ->
                    ClassUtils.resolveClassName(
                        Objects.requireNonNull(beanDefinition.getBeanClassName()),
                        getClassLoader()))
            .collect(Collectors.toSet()));
  }
}
