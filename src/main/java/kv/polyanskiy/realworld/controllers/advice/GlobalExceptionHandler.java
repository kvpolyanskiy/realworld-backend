package kv.polyanskiy.realworld.controllers.advice;

import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import kv.polyanskiy.realworld.dto.model.GenericErrorModel;
import kv.polyanskiy.realworld.dto.model.GenericErrorModelErrors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {BadCredentialsException.class})
  protected ResponseEntity<Object> handleBadCredentials() {
    return new ResponseEntity<>("Username or password is wrong", HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(value = {ConstraintViolationException.class})
  protected ResponseEntity<GenericErrorModel> handleValidation(
      ConstraintViolationException ex) {
    final var errors = new GenericErrorModelErrors();
    final var errorResponse = new GenericErrorModel();
    errorResponse.setErrors(errors);

    ex.getConstraintViolations().forEach(err -> errors.addBodyItem(err.getMessage()));

    return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(value = {UserNotFoundException.class})
  protected ResponseEntity<GenericErrorModel> handleUserNotFound(UserNotFoundException ex) {
    final var errors = new GenericErrorModelErrors();
    final var errorResponse = new GenericErrorModel();

    errorResponse.setErrors(errors);
    errors.addBodyItem(ex.getMessage());

    return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
