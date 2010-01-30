package org.lazydog.preference.manager.utility;

import org.lazydog.preference.api.PreferenceService;
import org.lazydog.preference.model.Agent;
import org.lazydog.preference.service.PreferenceServiceFactory;


/**
 * Agent utility.
 *
 * @author  Ron Rickard
 */
public class AgentUtility {

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

    /**
     * Get the status of the specified agent.
     *
     * @param  agent  the agent.
     *
     * @return  the status of the specified agent.
     */
    public String getStatus(Agent agent) {

        // Declare.
        String status;

        // Set the status to down.
        status = Status.DOWN.toString();

        try {

            // Declare.
            String agentConfiguration;
            PreferenceService agentPreferenceService;
            String localConfiguration;
            PreferenceService localPreferenceService;

            // Get the preference services.
            agentPreferenceService = PreferenceServiceFactory.createPreferenceService(agent);
            localPreferenceService = PreferenceServiceFactory.createPreferenceService();

            // Get the agent configuration for all the nodes.
            agentConfiguration = agentPreferenceService.getAllNodes();

            // Get the local configuration for all the nodes.
            localConfiguration = localPreferenceService.getAllNodes();

            // The agent is up.
            status = Status.UP.toString() + ", ";

            // Check if the agent configuration is the same as the local
            // configuration.
            if (agentConfiguration.equals(localConfiguration)) {

                // The agent is synced.
                status += Status.SYNCED.toString();
            }
            else {

                // The agent is not synced.
                status += Status.NOT_SYNCED.toString();
            }
        }
        catch(Exception e) {
            // Already handled.
        }

        return status;
    }
}
