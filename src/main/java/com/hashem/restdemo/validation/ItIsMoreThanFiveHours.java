package com.hashem.restdemo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ItIsMoreThanFiveHours implements ConstraintValidator<MoreThanFiveHours , Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if(value.intValue() >= 5)
            return true;
        return false;
    }
}
