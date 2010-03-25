package org.lazydog.preference.manager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.Validation;
import javax.validation.Validator;
import org.lazydog.preference.manager.validation.constraints.Path;


/**
 * Preference.
 *
 * @author  Ron Rickard
 */
public class Preference implements Serializable {

    @NotNull(message="Key is required.")
    @Size(min=1, message="Key is required.")
    private String key;
    @NotNull(message="Path is required.")
    @Path(message="Path is invalid.")
    private String path;
    @NotNull(message="Value is required.")
    @Size(min=1, message="Value is required.")
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
