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
 * along with JPrefer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.lazydog.jprefer.validation.constraints;

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
