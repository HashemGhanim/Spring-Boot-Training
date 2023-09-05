package com.hashem.restdemo.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.FIELD , ElementType.PARAMETER , ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NameValidator.class)
public @interface IsValidName {
    String message() default "Is Not Valid Name";

    // to make the annotation execute
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
