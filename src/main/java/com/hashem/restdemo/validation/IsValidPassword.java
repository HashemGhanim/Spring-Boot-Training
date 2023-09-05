package com.hashem.restdemo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;



@Target({ElementType.FIELD , ElementType.PARAMETER , ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface IsValidPassword {
    String message() default "Password is not valid !! ";

    // to make
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
