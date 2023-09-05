package com.hashem.restdemo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<IsValidPassword , String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value.length() < 6)
            return false;
        int counter = 0;

        for(int i = 0; i < value.length(); ++i){
            Character indexChar = value.charAt(i);
            if(indexChar >= 'a' && indexChar <= 'z')
                    ++counter;
            if(indexChar >= 'A' && indexChar <= 'Z')
                    ++counter;
        }

        if(counter < 2)
            return false;

        return true;
    }
}
