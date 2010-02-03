package org.lazydog.preference.manager.configuration.exception;

import java.io.Serializable;


/**
 * Thrown to indicate that an error occurred in the AgentDAO class.
 *
 * @author  Ronald Rickard
 */
public class AgentDAOException 
        extends Exception
        implements Serializable {

    /**
     * Constructs a new exception.
     */
    public AgentDAOException() {
        super();
    }

    /**
     * Constructs a new exception with the specified message.
     *
     * @param  message  the message.
     */
    public AgentDAOException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified message and cause.
     *
     * @param  message  the message.
     * @param  cause    the cause.
     */
    public AgentDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     *
     * @param  cause  the cause.
     */
    public AgentDAOException(Throwable cause) {
        super(cause);
    }
}
