package kv.polyanskiy.realworld.validation;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class EntityValidator {
  public void validate(Object object) throws ConstraintViolationException {
    final Validator validator = javax.validation.Validation.buildDefaultValidatorFactory()
        .getValidator();

    final var violations = validator.validate(object);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
