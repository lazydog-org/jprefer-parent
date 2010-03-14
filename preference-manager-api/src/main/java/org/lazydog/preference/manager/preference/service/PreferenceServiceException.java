package org.lazydog.preference.manager.preference.service;

import java.io.Serializable;


/**
 * Thrown to indicate that an error occurred in the PreferenceService class.
 *
 * @author  Ronald Rickard
 */
public class PreferenceServiceException
        extends Exception
        implements Serializable {

    /**
     * Constructs a new exception.
     */
    public PreferenceServiceException() {
        super();
    }

    /**
     * Constructs a new exception with the specified message.
     *
     * @param  message  the message.
     */
    public PreferenceServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified message and cause.
     *
     * @param  message  the message.
     * @param  cause    the cause.
     */
    public PreferenceServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     *
     * @param  cause  the cause.
     */
    public PreferenceServiceException(Throwable cause) {
        super(cause);
    }
}
