package org.lazydog.preference.group.service;

import java.io.Serializable;


/**
 * Thrown to indicate that an error occurred in the GroupService class.
 *
 * @author  Ronald Rickard
 */
public class GroupServiceException
        extends Exception
        implements Serializable {

    /**
     * Constructs a new exception.
     */
    public GroupServiceException() {
        super();
    }

    /**
     * Constructs a new exception with the specified message.
     *
     * @param  message  the message.
     */
    public GroupServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified message and cause.
     *
     * @param  message  the message.
     * @param  cause    the cause.
     */
    public GroupServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     *
     * @param  cause  the cause.
     */
    public GroupServiceException(Throwable cause) {
        super(cause);
    }
}
