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
package org.lazydog.jprefer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.Validation;
import javax.validation.Validator;
import org.lazydog.jprefer.validation.constraints.Path;


/**
 * Preference.
 *
 * @author  Ron Rickard
 */
public class Preference implements Serializable {

    @NotNull(message="Key is required.")
    @Size(min=1, max=80, message="Key has to be between 1 and 80 characters long.")
    private String key;
    @NotNull(message="Path is required.")
    @Size(min=1, max=800, message="Path has to be between 1 and 800 characters long.")
    @Path(message="Path is invalid.")
    private String path;
    @NotNull(message="Value is required.")
    @Size(min=1, max=8192, message="Value has to be between 1 and 8192 characters long.")
    private String value;

    /**
     * Get the key.
     *
     * @return  the key.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Get the path.
     *
     * @return  the path.
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Get the value.
     *
     * @return  the value.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Set the key.
     *
     * @param  key  the key.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Set the path.
     *
     * @param  path  the path.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Set the value.
     *
     * @param  value  the value.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Get this object as a String.
     *
     * @return  this object as a String.
     */
    @Override
    public String toString() {

        // Declare.
        StringBuffer toString;

        // Initialize.
        toString = new StringBuffer();

        toString.append("Preference [");
        toString.append("path = ").append(this.getPath());
        toString.append(", key = ").append(this.getKey());
        toString.append(", value = ").append(this.getValue());
        toString.append("]");

        return toString.toString();
    }

    /**
     * Validate this object.
     *
     * @return  the list of violation messages.
     */
    public List<String> validate() {

        // Declare.
        Set<ConstraintViolation<Preference>> constraintViolations;
        Validator validator;
        List<String> violationMessages;

        // Initialize.
        violationMessages = new ArrayList<String>();

        // Get the validator.
        validator = Validation.buildDefaultValidatorFactory().getValidator();

        // Validate this object.
        constraintViolations = validator.validate(this);

        // Loop through the constraint violations.
        for (ConstraintViolation constraintViolation : constraintViolations) {

            // Add the constraint violation message to the violation messages.
            violationMessages.add(constraintViolation.getMessage());
        }

        return violationMessages;
    }

    /**
     * Validate the path.
     *
     * @param  path  the path.
     *
     * @return  the list of violation messages.
     */
    public static List<String> validatePath(String path) {

        // Declare.
        Set<ConstraintViolation<Preference>> constraintViolations;
        Validator validator;
        List<String> violationMessages;

        // Initialize.
        violationMessages = new ArrayList<String>();

        // Get the validator.
        validator = Validation.buildDefaultValidatorFactory().getValidator();

        // Validate the path.
        constraintViolations = validator.validateValue(
                Preference.class, "path", path);

        // Loop through the constraint violations.
        for (ConstraintViolation constraintViolation : constraintViolations) {

            // Add the constraint violation message to the violation messages.
            violationMessages.add(constraintViolation.getMessage());
        }

        return violationMessages;
    }
}
