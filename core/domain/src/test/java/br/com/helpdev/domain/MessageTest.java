package br.com.helpdev.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.helpdev.domain.vo.MessageBody;
import br.com.helpdev.domain.vo.MessageId;
import br.com.helpdev.domain.vo.Phone;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class MessageTest {

  @Test
  void shouldThrowsInvalidRecipientExceptionWhenRecipientIsNull() {
    var builder = Message.builder()
        .body(MessageBody.from("body"));

    assertThatThrownBy(() -> builder.build())
        .hasMessage("The message needs a recipient");
  }


  @Test
  void shouldThrowsNullPointerWhenMessageBodyIsNull() {
    var builder = Message.builder().body(null);

    assertThatThrownBy(() -> builder.build())
        .hasMessage("Body cant be null");
  }

  @Test
  void shouldReturnEmptyIdWhenMessageWasCreatedWithoutThat() {
    var builder = Message.builder()
        .body(MessageBody.from("body"))
        .recipient(Recipient.builder()
            .phone(Phone.newNumber("1"))
            .build());

    assertThat(builder.build().getId())
        .isEqualTo(Optional.empty());
  }

  @Test
  void shouldReturnIdWhenMessageWasCreatedWith() {
    var builder = Message.builder()
        .body(MessageBody.from("body"))
        .id(MessageId.from(123L))
        .recipient(Recipient.builder()
            .phone(Phone.newNumber("1"))
            .build());

    assertThat(builder.build().getId())
        .isEqualTo(Optional.of(MessageId.from(123L)));
  }

}