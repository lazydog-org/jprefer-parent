package org.lazydog.preference.manager.model;

import java.io.Serializable;


/**
 * Role ordered by precendence.
 *
 * @author  Ron Rickard
 */
public enum Role implements Serializable {
    ADMIN,
    OPERATOR,
    USER,
    UNKNOWN;
};

