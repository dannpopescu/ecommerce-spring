package com.danpopescu.shop.persistence.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

@ReportAsSingleViolation
@Pattern(regexp = RegexPatterns.USERNAME)
@Constraint(validatedBy = {})
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Username {

    String message() default "{UsernameNotValid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
