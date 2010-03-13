package org.lazydog.preference.configuration.service;

import java.io.Serializable;


/**
 * Thrown to indicate that an error occurred in the ConfigurationService class.
 *
 * @author  Ronald Rickard
 */
public class ConfigurationServiceException
        extends Exception
        implements Serializable {

    /**
     * Constructs a new exception.
     */
    public ConfigurationServiceException() {
        super();
    }

    /**
     * Constructs a new exception with the specified message.
     *
     * @param  message  the message.
     */
    public ConfigurationServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified message and cause.
     *
     * @param  message  the message.
     * @param  cause    the cause.
     */
    public ConfigurationServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     *
     * @param  cause  the cause.
     */
    public ConfigurationServiceException(Throwable cause) {
        super(cause);
    }
}
