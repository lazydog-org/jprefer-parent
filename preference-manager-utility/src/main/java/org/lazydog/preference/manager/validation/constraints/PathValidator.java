package org.lazydog.preference.manager.validation.constraints;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * Path validator.
 *
 * @author  Ron Rickard
 */
public class PathValidator implements ConstraintValidator<Path, String> {

    public static final String VALUE_REGEX = "^/[\\w]{1,80}(/[\\w]{1,80}){0,10}$";

    /**
     * Initialize.
     *
     * @param  path  the path constraint.
     */
    @Override
    public void initialize(Path path) {
    }

    /**
     * Is the value valid?
     *
     * @param  value    the value.
     * @param  context  the constraint validator context.
     *
     * @return  true if the value is valid, otherwise false.
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (value != null) ? Pattern.matches(VALUE_REGEX, value) : false;
    }
}
