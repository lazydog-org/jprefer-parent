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
     * Get the environment.
     *
     * @param  agent  the agent.
     *
     * @return  the environment.
     */
    private static Hashtable<String,String> getEnvironment(Agent agent) {

        // Declare.
        Hashtable<String,String> env;

        // Set the environment.
        env = new Hashtable<String,String>();
        env.put(SynchronizeService.JMX_PORT, agent.getJmxPort().toString());
        env.put(SynchronizeService.LOGIN, agent.getLogin());
        env.put(SynchronizeService.PASSWORD, agent.getPassword());
        env.put(SynchronizeService.SERVER_NAME, agent.getServerName());

        return env;
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
            String localDocument;
            String remoteDocument;

            // Export the local document.
            localDocument = exportDocument();

            // Export the remote document.
            remoteDocument = exportDocument(agent);

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
     * Export the document locally.
     *
     * @return  the document.
     *
     * @throws  ServiceException  if unable to export the document locally.
     */
    public static String exportDocument()
            throws ServiceException {

        // Declare.
        String document;
        SynchronizeService synchronizeService;

        // Export the document locally.
        synchronizeService = SynchronizeServiceFactory.create();
        document = synchronizeService.exportDocument();

        return document;
    }

    /**
     * Export the document locally.
     *
     * @param  path  the path.
     *
     * @return  the document.
     *
     * @throws  ServiceException  if unable to export the document locally.
     */
    private static String exportDocument(String path)
            throws ServiceException {

        // Declare.
        String document;
        SynchronizeService synchronizeService;

        // Export the document locally.
        synchronizeService = SynchronizeServiceFactory.create();
        document = synchronizeService.exportDocument(path);

        return document;
    }

    /**
     * Export the document remotely.
     *
     * @return  the document.
     *
     * @throws  ServiceException  if unable to export the document locally.
     */
    private static String exportDocument(Agent agent)
            throws ServiceException {

        // Declare.
        String document;
        SynchronizeService synchronizeService;

        // Export the document locally.
        synchronizeService = SynchronizeServiceFactory.create(getEnvironment(agent));
        document = synchronizeService.exportDocument();

        return document;
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
     * Import the document locally.
     *
     * @param  document  the document.
     *
     * @throws  ServiceException  if unable to import the document locally.
     */
    public static void importDocument(String document)
            throws ServiceException {

        // Declare.
        SynchronizeService synchronizeService;

        // Import the document locally.
        synchronizeService = SynchronizeServiceFactory.create();
        synchronizeService.importDocument(document);

        // Synchronize the agents.
        synchronizeAgents();
    }

    /**
     * Import the document remotely.
     *
     * @param  agent     the agent.
     * @param  path      the path.
     * @param  document  the document.
     */
    private static void importDocument(Agent agent, String path, String document) {

        // Check if the agent is enabled.
        if (agent.getEnabled()) {

            try {

                // Declare.
                SynchronizeService synchronizeService;

                // Import the document remotely.
                synchronizeService = SynchronizeServiceFactory.create(getEnvironment(agent));
                synchronizeService.importDocument(path, document);
            }
            catch(Exception e) {
                // Ignore.
            }
        }
    }

    /**
     * Import the document remotely.
     *
     * @param  agent     the agent.
     * @param  document  the document.
     */
    private static void importDocument(Agent agent, String document) {

        // Check if the agent is enabled.
        if (agent.getEnabled()) {

            try {

                // Declare.
                SynchronizeService synchronizeService;

                // Import the document remotely.
                synchronizeService = SynchronizeServiceFactory.create(getEnvironment(agent));
                synchronizeService.importDocument(document);
            }
            catch(Exception e) {
                // Ignore.
            }
        }
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
     * Synchronize the agent.
     *
     * @param  agent  the agent.
     *
     * @throws  ServiceException  if unable to synchronize the agent.
     */
    public static void synchronizeAgent(Agent agent)
            throws ServiceException {

        // Declare.
        String document;

        // Export the document locally.
        document = exportDocument();

        // Import the document remotely.
        importDocument(agent, document);
    }

    /**
     * Synchronize the agents.
     *
     * @throws  ServiceException  if unable to synchronize the agents.
     */
    public static void synchronizeAgents()
            throws ServiceException {

        // Declare.
        List<Agent> agents;
        String document;

        // Get the agents.
        agents = configurationService.findAgents();

        // Export the document locally.
        document = exportDocument();

        // Loop through the agents.
        for (Agent agent : agents) {

            // Import the document remotely.
            importDocument(agent, document);
        }
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
}
