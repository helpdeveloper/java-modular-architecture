package br.com.helpdev.controller.dto;

import br.com.helpdev.domain.Status;

public enum StatusResponseDto {
  WAITING,
  SENDING,
  SENT;

  public static StatusResponseDto findByStatus(final Status status) {
    if (status == null) {
      return WAITING;
    }
    return StatusResponseDto.valueOf(status.name());
  }

}
