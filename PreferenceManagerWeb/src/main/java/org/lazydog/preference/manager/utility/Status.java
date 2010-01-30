/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lazydog.preference.manager.utility;

/**
 * Status.
 *
 * @author  Ron Rickard
 */
public enum Status {
    UP         ("Up"),
    DOWN       ("Down"),
    SYNCED     ("Sync'd"),
    NOT_SYNCED ("Not Sync'd");

    private String toString;

    Status(String toString) {
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

