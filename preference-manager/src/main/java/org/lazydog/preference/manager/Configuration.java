package org.lazydog.preference.manager;

import java.util.Hashtable;
import java.util.List;
import org.lazydog.preference.manager.configuration.service.ConfigurationService;
import org.lazydog.preference.manager.configuration.service.ConfigurationServiceFactory;
import org.lazydog.preference.manager.model.Agent;
import org.lazydog.preference.manager.model.AgentStatus;
import org.lazydog.preference.manager.model.SetupType;
import org.lazydog.preference.manager.service.ServiceException;
import org.lazydog.preference.manager.synchronize.service.SynchronizeService;
import org.lazydog.preference.manager.synchronize.service.SynchronizeServiceFactory;


/**
 * Configuration.
 *
 * @author  Ron Rickard
 */
public class Configuration {

    private static ConfigurationService configurationService
            = ConfigurationServiceFactory.create();

    /**
     * Determine the status of the specified agent.
     *
     * @param  agent  the agent.
     *
     * @return  the status of the specified agent.
     */
    private static AgentStatus determineStatus(Agent agent) {

        // Declare.
        AgentStatus status;

        // Set the status to up.
        status = AgentStatus.UNKNOWN;

        try {

            // Declare.
            SynchronizeService remoteSynchronizeService;
            Object remoteDocument;
            Hashtable<String,String> env;
            SynchronizeService localSynchronizeService;
            Object localDocument;

            // Set the environment.
            env = new Hashtable<String,String>();
            env.put(SynchronizeService.JMX_PORT, agent.getJmxPort().toString());
            env.put(SynchronizeService.LOGIN, agent.getLogin());
            env.put(SynchronizeService.PASSWORD, agent.getPassword());
            env.put(SynchronizeService.SERVER_NAME, agent.getServerName());

            // Get the synchronize services.
            remoteSynchronizeService = SynchronizeServiceFactory.create(env);
            localSynchronizeService = SynchronizeServiceFactory.create();

            // Export the remote document.
            remoteDocument = remoteSynchronizeService.exportDocument();

            // Export the local document.
            localDocument = localSynchronizeService.exportDocument();
 
            // Check if the remote document is equal to the local document.
            if (((String)remoteDocument).equals((String)localDocument)) {

                // The agent is synced.
                status = AgentStatus.UP_SYNCED;
            }
            else {

                // The agent is not synced.
                status = AgentStatus.UP_NOT_SYNCED;
            }
        }
        catch(Exception e) {

            // Set the status to down.
            status = AgentStatus.DOWN;
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

            // Disable the agent.
            agent = configurationService.findAgent(id);
            agent.setEnabled(Boolean.FALSE);
            agent = configurationService.persistAgent(agent);
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

            // Enable the agent.
            agent = configurationService.findAgent(id);
            agent.setEnabled(Boolean.TRUE);
            agent = configurationService.persistAgent(agent);
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

            // Get the agent.
            agent = configurationService.findAgent(id);

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

            // Get the agents.
            agents = configurationService.findAgents();

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

            // Check if this is an agent setup.
            if (configurationService.findSetupType() == SetupType.AGENT) {
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

            // Check if this is a manager setup.
            if (configurationService.findSetupType() == SetupType.MANAGER) {
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

            // Check if this is setup.
            if (configurationService.findSetupType() != null) {
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

            // Check if this is a standalone setup.
            if (configurationService.findSetupType() == SetupType.STANDALONE) {
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

            // Save the agent.
            agent = configurationService.persistAgent(agent);
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

            // Save the setup type.
            setupType = configurationService.persistSetupType(setupType);
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }

        return setupType;
    }
}
