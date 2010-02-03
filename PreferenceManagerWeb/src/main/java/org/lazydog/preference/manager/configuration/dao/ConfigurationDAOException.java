package org.lazydog.preference.manager.configuration.dao;

import java.io.Serializable;


/**
 * Thrown to indicate that an error occurred in the ConfigurationDAO class.
 *
 * @author  Ronald Rickard
 */
public class ConfigurationDAOException
        extends Exception
        implements Serializable {

    /**
     * Constructs a new exception.
     */
    public ConfigurationDAOException() {
        super();
    }

    /**
     * Constructs a new exception with the specified message.
     *
     * @param  message  the message.
     */
    public ConfigurationDAOException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified message and cause.
     *
     * @param  message  the message.
     * @param  cause    the cause.
     */
    public ConfigurationDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     *
     * @param  cause  the cause.
     */
    public ConfigurationDAOException(Throwable cause) {
        super(cause);
    }
}
