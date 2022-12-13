package kv.polyanskiy.realworld.controllers.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {BadCredentialsException.class})
  protected ResponseEntity<Object> handleBadCredentials(RuntimeException ex, WebRequest request) {
    return new ResponseEntity<>("Username or password is wrong", HttpStatus.FORBIDDEN);
  }
}
