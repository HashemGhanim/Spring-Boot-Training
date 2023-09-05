package com.hashem.restdemo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<IsValidName , String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value.length() > 0)
        {
            value = value.toLowerCase();
            Character indexChar = value.charAt(0);

            return !(indexChar >= 'a' && indexChar <= 'z') ? false : true;
        }
        return true;
    }
}
