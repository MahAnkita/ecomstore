package com.ankitacodes.Ecom.helper;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//First, we create the custom constraint annotation ValidImage
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = ImageValidator.class)
@Documented
public @interface ValidImage {

    String message() default "{ImageName.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
