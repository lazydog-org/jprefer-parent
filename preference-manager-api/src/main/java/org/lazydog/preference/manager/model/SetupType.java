package org.lazydog.preference.manager.model;

import java.io.Serializable;


/**
 * Setup type ordered by precendence.
 *
 * @author  Ron Rickard
 */
public enum SetupType implements Serializable {
    MANAGER,
    STANDALONE,
    AGENT,
    UNKNOWN;
};

