package org.lazydog.preference.snapshot.service;

import java.io.Serializable;


/**
 * Thrown to indicate that an error occurred in the SnapshotService class.
 *
 * @author  Ronald Rickard
 */
public class SnapshotServiceException
        extends Exception
        implements Serializable {

    /**
     * Constructs a new exception.
     */
    public SnapshotServiceException() {
        super();
    }

    /**
     * Constructs a new exception with the specified message.
     *
     * @param  message  the message.
     */
    public SnapshotServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified message and cause.
     *
     * @param  message  the message.
     * @param  cause    the cause.
     */
    public SnapshotServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     *
     * @param  cause  the cause.
     */
    public SnapshotServiceException(Throwable cause) {
        super(cause);
    }
}
