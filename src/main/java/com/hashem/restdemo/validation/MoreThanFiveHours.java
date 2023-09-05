package com.hashem.restdemo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.FIELD , ElementType.PARAMETER , ElementType.METHOD}) // Where This Validation Will Run
@Retention(RetentionPolicy.RUNTIME) // When This Validation Will Run
@Constraint(validatedBy = ItIsMoreThanFiveHours.class) // Class Who is Responsible to implement Validation
public @interface MoreThanFiveHours {
    String message() default "Hours Is Less Than 5 and this is not possible !";

    // to make
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
