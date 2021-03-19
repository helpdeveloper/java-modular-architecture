package br.com.helpdev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {"br.com.helpdev", "br.com.helpdev.output"}
)
public class SpringSampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringSampleApplication.class, args);
  }

}
