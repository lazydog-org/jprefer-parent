package org.lazydog.preference.manager;

import java.io.Serializable;


/**
 * Thrown to indicate that an error occurred in a service class.
 *
 * @author  Ronald Rickard
 */
public class ServiceException
        extends Exception
        implements Serializable {

    /**
     * Constructs a new exception.
     */
    public ServiceException() {
        super();
    }

    /**
     * Constructs a new exception with the specified message.
     *
     * @param  message  the message.
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified message and cause.
     *
     * @param  message  the message.
     * @param  cause    the cause.
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     *
     * @param  cause  the cause.
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
