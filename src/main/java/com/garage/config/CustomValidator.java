package com.garage.config;

import com.garage.exceptions.ValidationException;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.OptionalValidatorFactoryBean;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
public class CustomValidator extends OptionalValidatorFactoryBean {

    @Override
    public void validate(Object target, Errors errors) throws ValidationException {
        processConstraintViolations(super.validate(target), errors);

        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }
}