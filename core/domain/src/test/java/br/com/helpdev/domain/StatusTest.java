package br.com.helpdev.domain;

import java.util.Arrays;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class StatusTest {

  @Test
  void dummyTest() {
    Arrays.stream(Status.values()).forEach(each -> Assertions.assertThat(each).isNotNull());
  }
}