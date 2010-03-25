package org.lazydog.preference.manager.validation.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;


/**
 * Port constraint.
 *
 * @author  Ron Rickard
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PortValidator.class)
public @interface Port {

    String message() default "{org.lazydog.preference.manager.validation.constraints.Port}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
