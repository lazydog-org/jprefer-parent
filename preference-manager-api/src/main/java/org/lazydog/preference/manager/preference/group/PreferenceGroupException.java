package org.lazydog.preference.manager.preference.group;

import java.io.Serializable;


/**
 * Thrown to indicate that an error occurred in the PreferenceGroup class.
 *
 * @author  Ronald Rickard
 */
public class PreferenceGroupException
        extends Exception
        implements Serializable {

    /**
     * Constructs a new exception.
     */
    public PreferenceGroupException() {
        super();
    }

    /**
     * Constructs a new exception with the specified message.
     *
     * @param  message  the message.
     */
    public PreferenceGroupException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified message and cause.
     *
     * @param  message  the message.
     * @param  cause    the cause.
     */
    public PreferenceGroupException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     *
     * @param  cause  the cause.
     */
    public PreferenceGroupException(Throwable cause) {
        super(cause);
    }
}
