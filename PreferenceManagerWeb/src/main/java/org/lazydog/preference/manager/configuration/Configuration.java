package org.lazydog.preference.manager.configuration;

import java.util.List;
import org.lazydog.preference.api.PreferenceService;
import org.lazydog.preference.manager.configuration.dao.ConfigurationDAO;
import org.lazydog.preference.manager.model.Agent;
import org.lazydog.preference.service.PreferenceServiceFactory;


/**
 * Configuration.
 *
 * @author  Ron Rickard
 */
public class Configuration {

    /**
     * Determine the status of the specified agent.
     *
     * @param  agent  the agent.
     *
     * @return  the status of the specified agent.
     */
    private static String determineStatus(Agent agent) {

        // Declare.
        String status;

        // Set the status to down.
        status = AgentStatus.DOWN.toString();

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
            status = AgentStatus.UP.toString() + ", ";

            // Check if the agent configuration is the same as the local
            // configuration.
            if (agentConfiguration.equals(localConfiguration)) {

                // The agent is synced.
                status += AgentStatus.SYNCED.toString();
            }
            else {

                // The agent is not synced.
                status += AgentStatus.NOT_SYNCED.toString();
            }
        }
        catch(Exception e) {
            // Already handled.
        }

        return status;
    }

    /**
     * Disable the agent specified by the ID.
     * 
     * @param  id  the ID.
     * 
     * @return  the agent.
     */
    public static Agent disableAgent(int id) {

        // Declare.
        Agent agent;

        agent = null;

        try {

            // Declare.
            ConfigurationDAO configurationDAO;

            // Initialize.
            configurationDAO = new ConfigurationDAO();

            // Disable the agent.
            agent = configurationDAO.findAgent(id);
            agent.setEnabled(Boolean.FALSE);
            agent = configurationDAO.persistAgent(agent);
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }

        return agent;
    }

    /**
     * Enable the agent specified by the ID.
     *
     * @param  id  the ID.
     *
     * @return  the agent.
     */
    public static Agent enableAgent(int id) {

        // Declare.
        Agent agent;

        agent = null;

        try {

            // Declare.
            ConfigurationDAO configurationDAO;

            // Initialize.
            configurationDAO = new ConfigurationDAO();

            // Enable the agent.
            agent = configurationDAO.findAgent(id);
            agent.setEnabled(Boolean.TRUE);
            agent = configurationDAO.persistAgent(agent);
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }

        return agent;
    }

    /**
     * Get the agent specified by the ID.
     * 
     * @param  id  the ID.
     * 
     * @return  the agent.
     */
    public static Agent getAgent(int id) {

        // Declare.
        Agent agent;

        // Initialize.
        agent = null;

        try {

            // Declare.
            ConfigurationDAO configurationDAO;

            // Initialize.
            configurationDAO = new ConfigurationDAO();

            // Get the agent.
            agent = configurationDAO.findAgent(id);

            // Set the status for the agent.
            agent.setStatus(determineStatus(agent));
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }

        return agent;
    }

    /**
     * Get the agents.
     *
     * @return  the agents.
     */
    public static List<Agent> getAgents() {

        // Declare.
        List<Agent> agents;

        // Initialize.
        agents = null;

        try {

            // Declare.
            ConfigurationDAO configurationDAO;

            // Initialize.
            configurationDAO = new ConfigurationDAO();

            // Get the agents.
            agents = configurationDAO.findAgents();

            // Loop through the agents.
            for (Agent agent : agents) {

                // Set the status for the agent.
                agent.setStatus(determineStatus(agent));
            }
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }

        return agents;
    }

    /**
     * Is the configuration an agent service.
     *
     * @return  true if the configuration is an agent service, otherwise false.
     */
    public static boolean isAgent() {

        // Declare.
        boolean isAgent;

        // Initialize.
        isAgent = false;

        try {

            // Declare.
            ConfigurationDAO configurationDAO;

            // Initialize.
            configurationDAO = new ConfigurationDAO();

            // Check if the configuration is an agent service.
            if (configurationDAO.findType() == ConfigurationType.AGENT) {
                isAgent = true;
            }
        }
        catch(Exception e) {
            // Ignore.
        }

        return isAgent;
    }

    /**
     * Is the configuration a manager service.
     *
     * @return  true if the configuration is a manager service, otherwise false.
     */
    public static boolean isManager() {

        // Declare.
        boolean isManager;

        // Initialize.
        isManager = false;

        try {

            // Declare.
            ConfigurationDAO configurationDAO;

            // Initialize.
            configurationDAO = new ConfigurationDAO();

            // Check if the configuration is a manager service.
            if (configurationDAO.findType() == ConfigurationType.MANAGER) {
                isManager = true;
            }
        }
        catch(Exception e) {
            // Ignore.
        }

        return isManager;
    }

    /**
     * Is the configuration a standalone service.
     *
     * @return  true if the configuration is a standalone service, otherwise false.
     */
    public static boolean isStandalone() {

        // Declare.
        boolean isStandalone;

        // Initialize.
        isStandalone = false;

        try {

            // Declare.
            ConfigurationDAO configurationDAO;

            // Initialize.
            configurationDAO = new ConfigurationDAO();

            // Check if the configuration is a standalone service.
            if (configurationDAO.findType() == ConfigurationType.STANDALONE) {
                isStandalone = true;
            }
        }
        catch(Exception e) {
            // Ignore.
        }

        return isStandalone;
    }

    /**
     * Save the agent.
     *
     * @param  agent  the agent.
     *
     * @return  the agent.
     */
    public static Agent saveAgent(Agent agent) {

        try {

            // Declare.
            ConfigurationDAO configurationDAO;

            // Initialize.
            configurationDAO = new ConfigurationDAO();

            // Save the agent.
            agent = configurationDAO.persistAgent(agent);
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }

        return agent;
    }
}
