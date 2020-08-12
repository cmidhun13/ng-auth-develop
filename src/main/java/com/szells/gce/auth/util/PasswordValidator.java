package com.szells.gce.auth.util;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (!StringUtils.hasText(value)) {
            context.buildConstraintViolationWithTemplate("{requestBody.field-missing}").addConstraintViolation();
            return false;
        } else if (StringUtils.containsWhitespace(value) || value.length() < 8) {
            context.buildConstraintViolationWithTemplate("{password.invalid}").addConstraintViolation();
            return false;
        }
        return true;
    }
}
