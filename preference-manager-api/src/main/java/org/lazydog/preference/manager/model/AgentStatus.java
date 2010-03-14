package org.lazydog.preference.manager.model;

/**
 * Agent status.
 *
 * @author  Ron Rickard
 */
public enum AgentStatus {
    DOWN          ("Down"),
    UNKNOWN       ("Unknown"),
    UP_SYNCED     ("Up, Sync'd"),
    UP_NOT_SYNCED ("Up, Not Sync'd");

    private String toString;

    AgentStatus(String toString) {
        this.toString = toString;
    }

    /**
     * The status as a String.
     *
     * @return  the status as a String.
     */
    @Override
    public String toString() {
        return this.toString;
    }
};

