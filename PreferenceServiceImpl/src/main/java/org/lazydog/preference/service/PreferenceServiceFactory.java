package org.lazydog.preference.service;

import org.lazydog.preference.api.PreferenceService;
import org.lazydog.preference.exception.PreferenceServiceException;
import org.lazydog.preference.model.Agent;


/**
 * Preference service factory.
 *
 * @author  Ron Rickard
 */
public class PreferenceServiceFactory {

    /**
     * Create the preference service.
     *
     * @return  the preference service.
     */
    public static PreferenceService createPreferenceService() {
        return new LocalPreferenceService();
    }

    /**
     * Create the preference service for the specified agent.
     *
     * @param  agent  the agent.
     *
     * @return  the preference service.
     *
     * @throws  PreferenceServiceException  if unable to create the
     *                                      preference service.
     */
    public static PreferenceService createPreferenceService(Agent agent)
            throws PreferenceServiceException {
        return new RemotePreferenceService(agent);
    }
}
