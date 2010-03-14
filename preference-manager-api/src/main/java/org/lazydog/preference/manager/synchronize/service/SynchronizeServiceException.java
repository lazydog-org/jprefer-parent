package org.lazydog.preference.manager.synchronize.service;

import java.io.Serializable;


/**
 * Thrown to indicate that an error occurred in the SynchronizeService class.
 *
 * @author  Ronald Rickard
 */
public class SynchronizeServiceException
        extends Exception
        implements Serializable {

    /**
     * Constructs a new exception.
     */
    public SynchronizeServiceException() {
        super();
    }

    /**
     * Constructs a new exception with the specified message.
     *
     * @param  message  the message.
     */
    public SynchronizeServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified message and cause.
     *
     * @param  message  the message.
     * @param  cause    the cause.
     */
    public SynchronizeServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     *
     * @param  cause  the cause.
     */
    public SynchronizeServiceException(Throwable cause) {
        super(cause);
    }
}
