/**
 * Copyright 2009, 2010 lazydog.org.
 *
 * This file is part of JPrefer.
 *
 * JPrefer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JPrefer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Preference Manager.  If not, see <http://www.gnu.org/licenses/>.
 */
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
