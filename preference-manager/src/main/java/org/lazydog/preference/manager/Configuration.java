package org.lazydog.preference.manager;

import java.util.Hashtable;
import java.util.List;
import org.lazydog.preference.manager.configuration.service.ConfigurationService;
import org.lazydog.preference.manager.configuration.service.ConfigurationServiceFactory;
import org.lazydog.preference.manager.model.Agent;
import org.lazydog.preference.manager.model.AgentState;
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
     * Clear the configuration.
     * 
     * @throws  ServiceException  if unable to clear the configuration.
     */
    public static void clear()
            throws ServiceException {

        // Remove the agent state and setup type.
        configurationService.removeAgentState();
        configurationService.removeSetupType();

        // Loop through the agents.
        for (Agent agent : configurationService.findAgents()) {

            // Remove the agent.
            configurationService.removeAgent(agent.getId());
        }
    }

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
            String remoteDocument;
            Hashtable<String,String> env;
            SynchronizeService localSynchronizeService;
            String localDocument;

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
            if (remoteDocument.equals(localDocument)) {

                // The agent is synced.
                status = AgentStatus.UP_SYNCED;
            }
            else {

                // The agent is not synced.
                status = AgentStatus.UP_NOT_SYNCED;
            }
        }
        catch(Exception e) {
e.printStackTrace();
            // Set the status to down.
            status = AgentStatus.DOWN;
        }

        return status;
    }

    /**
     * Disable the agent.
     * 
     * @param  id  the ID.
     * 
     * @return  the agent.
     *
     * @throws  ServiceException  if unable to disable the agent.
     */
    public static Agent disableAgent(int id) 
            throws ServiceException {

        // Declare.
        Agent agent;

        // Disable the agent.
        agent = configurationService.findAgent(id);
        agent.setEnabled(Boolean.FALSE);
        agent = configurationService.persistAgent(agent);

        return agent;
    }

    /**
     * Enable the agent.
     *
     * @param  id  the ID.
     *
     * @return  the agent.
     *
     * @throws  ServiceException  if unable to enable the agent.
     */
    public static Agent enableAgent(int id) 
            throws ServiceException {

        // Declare.
        Agent agent;

        // Enable the agent.
        agent = configurationService.findAgent(id);
        agent.setEnabled(Boolean.TRUE);
        agent = configurationService.persistAgent(agent);

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

        // Get the agent.
        agent = configurationService.findAgent(id);

        // Set the status for the agent.
        agent.setStatus(determineStatus(agent));

        return agent;
    }

    /**
     * Get the agents.
     *
     * @return  the agents.
     *
     * @throws  ServiceException  if unable to get the agents.
     */
    public static List<Agent> getAgents() 
            throws ServiceException {

        // Declare.
        List<Agent> agents;

        // Get the agents.
        agents = configurationService.findAgents();

        // Loop through the agents.
        for (Agent agent : agents) {

            // Set the status for the agent.
            agent.setStatus(determineStatus(agent));
        }

        return agents;
    }

    /**
     * Is the agent state disabled.
     *
     * @return  true if the agent state is disabled, otherwise false.
     */
    public static boolean isAgentStateDisabled() {
        return (configurationService.findAgentState() == AgentState.DISABLED) ?
                true : false;
    }

    /**
     * Is the agent state enabled.
     *
     * @return  true if the agent state is enabled, otherwise false.
     */
    public static boolean isAgentStateEnabled() {
        return (configurationService.findAgentState() == AgentState.ENABLED) ?
                true : false;
    }

    /**
     * Is this an agent setup.
     *
     * @return  true if this is an agent setup, otherwise false.
     */
    public static boolean isAgentSetup() {
        return (configurationService.findSetupType() == SetupType.AGENT) ?
                true : false;
    }

    /**
     * Is this a manager setup.
     *
     * @return  true if this is a manager setup, otherwise false.
     */
    public static boolean isManagerSetup() {
        return (configurationService.findSetupType() == SetupType.MANAGER) ?
                true : false;
    }

    /**
     * Is this setup.
     *
     * @return  true if this is setup, otherwise false.
     */
    public static boolean isSetup() {
        return (configurationService.findSetupType() != SetupType.UNKNOWN) ?
                true : false;
    }

    /**
     * Is this a standalone setup.
     *
     * @return  true if this is a standalone setup, otherwise false.
     */
    public static boolean isStandaloneSetup() {
        return (configurationService.findSetupType() == SetupType.STANDALONE) ?
                true : false;
    }

    /**
     * Remove the agent.
     *
     * @param  id  the ID.
     *
     * @throws  ServiceException  if unable to remove the agent.
     */
    public static void removeAgent(int id)
            throws ServiceException {
        configurationService.removeAgent(id);
    }

    /**
     * Save the agent.
     *
     * @param  agent  the agent.
     *
     * @return  the agent.
     *
     * @throws  ServiceException  if unable to save the agent.
     */
    public static Agent saveAgent(Agent agent) 
            throws ServiceException {
        return configurationService.persistAgent(agent);
    }

    /**
     * Save the agent state.
     *
     * @param  agentState  the agent state.
     *
     * @return  the agent state.
     *
     * @throws  ServiceException  if unable to save the agent state.
     */
    public static AgentState saveAgentState(AgentState agentState)
            throws ServiceException {
        return configurationService.persistAgentState(agentState);
    }
    /**
     * Save the setup type.
     *
     * @param  setupType  the setup type.
     *
     * @return  the setup type.
     *
     * @throws  ServiceException  if unable to save the setup type.
     */
    public static SetupType saveSetupType(SetupType setupType) 
            throws ServiceException {
        return configurationService.persistSetupType(setupType);
    }

    public static void main(String[] args) throws Exception {

        for (Agent agent : getAgents()) {
            System.out.println(agent);
        }
    }
}
