package com.tech.interview.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Locale;
import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class MyExceptionHandler {

  private final MessageSource messageSource;

  @Autowired
  public MyExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  /**
   * Custom error exception
   */
  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ErrorResponse> handlerCourseException(BaseException ex, Locale locale) {
    log.debug(ex.getMessage(), ex);
    String message = messageSource.getMessage(ex.getMessageCode(), ex.getArgs(), locale);
    String code = ex.getErrCode();
    ErrorResponse errorResponse = ErrorResponse.builder().message(message).code(code).build();

    return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
  }

  //region 4xx
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<ErrorResponse> httpMediaTypeNotSupportedException(
      final HttpMediaTypeNotSupportedException ex, final WebRequest request) {
    log.warn("(httpMediaTypeNotSupportedException)path: {}, ex: {}", request.getContextPath(),
        ex.getMessage());

    return new ResponseEntity<>(
        ErrorResponse.builder().message(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> httpMessageNotReadableException(
      final HttpMessageNotReadableException ex, final WebRequest request) {
    log.warn("(httpMessageNotReadableException)path: {}, ex: {}", request.getContextPath(),
        ex.getMessage());

    return new ResponseEntity<>(
        ErrorResponse.builder().message(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    log.warn("(handleArgumentNotValidException)ex: {}", ex.getMessage());

    BindingResult result = ex.getBindingResult();
    Optional<ObjectError> objectError = result.getAllErrors().stream().findFirst();
    String msg = ex.getMessage();
    if (objectError.isPresent() && objectError.get() instanceof FieldError) {
      FieldError fieldError = (FieldError) objectError.get();
      msg = fieldError.getField() + " " + fieldError.getDefaultMessage();
    }

    ErrorResponse errorResponse = ErrorResponse.builder().message(msg).build();

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({BindException.class})
  public ResponseEntity<ErrorResponse> handleValidationErrors(BindException ex) {
    log.warn("(handleValidationErrors)ex: {}", ex.getMessage());

    Optional<FieldError> fieldError = ex.getFieldErrors().stream().findFirst();
    String msg = ex.getMessage();
    if (fieldError.isPresent()) {
      msg = fieldError.get().getField() + " " + fieldError.get().getDefaultMessage();
    }
    ErrorResponse errorResponse = ErrorResponse.builder().message(msg).build();

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  protected ResponseEntity<ErrorResponse> handleIllegalArgument(final IllegalArgumentException ex) {
    log.warn("(handleIllegalArgument)ex: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder().message(ex.getMessage()).build();
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }
  //endregion

  /**
   * 500 Internal server error
   */
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorResponse> handleAll(
      final Exception ex, Locale locale, final WebRequest request) {
    log.error(ex.getMessage(), ex);
    String message = "SYSTEM_ERROR";
    ErrorResponse errorResponse = ErrorResponse.builder().message(message).build();

    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
