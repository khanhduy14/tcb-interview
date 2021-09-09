package com.tech.interview.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BaseException extends RuntimeException {

  protected final HttpStatus httpStatus;

  protected final String messageCode;
  private final String errCode;
  protected transient Object[] args;

  public BaseException(HttpStatus httpStatus, String messageCode) {
    super(messageCode);
    this.httpStatus = httpStatus;
    this.messageCode = messageCode;
    this.errCode = null;
  }
}
