package org.lazydog.preference.manager.model;

/**
 * Agent status.
 *
 * @author  Ron Rickard
 */
public enum AgentStatus {
    UP         ("Up"),
    DOWN       ("Down"),
    SYNCED     ("Sync'd"),
    NOT_SYNCED ("Not Sync'd");

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

