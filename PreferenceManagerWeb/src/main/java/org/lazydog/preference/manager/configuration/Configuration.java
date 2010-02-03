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
     * Is this an agent setup.
     *
     * @return  true if this is an agent setup, otherwise false.
     */
    public static boolean isAgentSetup() {

        // Declare.
        boolean isAgentSetup;

        // Initialize.
        isAgentSetup = false;

        try {

            // Declare.
            ConfigurationDAO configurationDAO;

            // Initialize.
            configurationDAO = new ConfigurationDAO();

            // Check if this is an agent setup.
            if (configurationDAO.findSetupType() == SetupType.AGENT) {
                isAgentSetup = true;
            }
        }
        catch(Exception e) {
            // Ignore.
        }

        return isAgentSetup;
    }

    /**
     * Is this a manager setup.
     *
     * @return  true if this is a manager setup, otherwise false.
     */
    public static boolean isManagerSetup() {

        // Declare.
        boolean isManagerSetup;

        // Initialize.
        isManagerSetup = false;

        try {

            // Declare.
            ConfigurationDAO configurationDAO;

            // Initialize.
            configurationDAO = new ConfigurationDAO();

            // Check if this is a manager setup.
            if (configurationDAO.findSetupType() == SetupType.MANAGER) {
                isManagerSetup = true;
            }
        }
        catch(Exception e) {
            // Ignore.
        }

        return isManagerSetup;
    }

    /**
     * Is this setup.
     *
     * @return  true if this is setup, otherwise false.
     */
    public static boolean isSetup() {

        // Declare.
        boolean isSetup;

        // Initialize.
        isSetup = false;

        try {

            // Declare.
            ConfigurationDAO configurationDAO;

            // Initialize.
            configurationDAO = new ConfigurationDAO();

            // Check if this is setup.
            if (configurationDAO.findSetupType() != null) {
                isSetup = true;
            }
        }
        catch(Exception e) {
            // Ignore.
        }

        return isSetup;
    }

    /**
     * Is this a standalone setup.
     *
     * @return  true if this is a standalone setup, otherwise false.
     */
    public static boolean isStandaloneSetup() {

        // Declare.
        boolean isStandaloneSetup;

        // Initialize.
        isStandaloneSetup = false;

        try {

            // Declare.
            ConfigurationDAO configurationDAO;

            // Initialize.
            configurationDAO = new ConfigurationDAO();

            // Check if this is a standalone setup.
            if (configurationDAO.findSetupType() == SetupType.STANDALONE) {
                isStandaloneSetup = true;
            }
        }
        catch(Exception e) {
            // Ignore.
        }

        return isStandaloneSetup;
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

    /**
     * Save the setup type.
     *
     * @param  setupType  the setup type.
     *
     * @return  the setup type.
     */
    public static SetupType saveSetupType(SetupType setupType) {

        try {

            // Declare.
            ConfigurationDAO configurationDAO;

            // Initialize.
            configurationDAO = new ConfigurationDAO();

            // Save the setup type.
            setupType = configurationDAO.persistSetupType(setupType);
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }

        return setupType;
    }
}
