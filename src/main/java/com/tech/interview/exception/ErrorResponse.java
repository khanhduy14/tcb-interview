package com.tech.interview.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {
  private String message;
  private String code;

  public ErrorResponse(String message, String code) {
    this.message = message;
    this.code = code;
  }
}
