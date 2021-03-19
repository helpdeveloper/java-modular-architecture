package br.com.helpdev.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class OpenApiControllerTest {

  @InjectMocks
  private OpenApiController openApiController = new OpenApiController();

  @Test
  @DisplayName("Should not be null")
  void whenCallingShouldNotBeNull() {
    assertThat(openApiController.openApi())
        .isNotNull();
  }

}
