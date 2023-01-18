package br.com.helpdev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {"br.com.helpdev"}
)
@EnableAutoConfiguration
public class SpringSampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringSampleApplication.class, args);
  }

}
