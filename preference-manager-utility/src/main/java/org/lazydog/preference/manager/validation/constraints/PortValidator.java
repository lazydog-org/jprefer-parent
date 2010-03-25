package org.lazydog.preference.manager.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * Port validator.
 *
 * @author  Ron Rickard
 */
public class PortValidator implements ConstraintValidator<Port, String> {

    /**
     * Initialize.
     *
     * @param  port  the port constraint.
     */
    @Override
    public void initialize(Port port) {
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

        // Declare.
        boolean isValid;

        try {

            // Covert string a an integer.
            new Integer(value);

            // Is valid is true.
            isValid = true;
        }
        catch(NumberFormatException e) {

            // Is valid is false.
            isValid = false;
        }

        return isValid;
    }
}
