package org.lazydog.preference.api;

import org.lazydog.preference.service.AgentPreferenceServiceMBean;
import org.lazydog.preference.service.internal.LocalPreferenceServiceImpl;


/**
 * Agent preference service.
 * 
 * @author  Ron Rickard
 */
public class AgentPreferenceService
        extends LocalPreferenceServiceImpl
        implements AgentPreferenceServiceMBean {
}