package org.lazydog.preference.manager.internal;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import org.lazydog.preference.manager.model.Agent;
import org.lazydog.preference.manager.model.AgentStatus;
import org.lazydog.preference.manager.model.PreferencesTree;
import org.lazydog.preference.manager.model.SetupType;
import org.lazydog.preference.manager.PreferenceManager;
import org.lazydog.preference.manager.ServiceException;
import org.lazydog.preference.manager.spi.configuration.ConfigurationService;
import org.lazydog.preference.manager.spi.configuration.ConfigurationServiceFactory;
import org.lazydog.preference.manager.spi.preference.PreferenceService;
import org.lazydog.preference.manager.spi.preference.PreferenceServiceFactory;
import org.lazydog.preference.manager.spi.synchronize.RemoteSynchronizeService;
import org.lazydog.preference.manager.spi.snapshot.SnapshotService;
import org.lazydog.preference.manager.spi.snapshot.SnapshotServiceFactory;
import org.lazydog.preference.manager.spi.synchronize.SynchronizeService;
import org.lazydog.preference.manager.spi.synchronize.SynchronizeServiceFactory;


/**
 * Preference manager EJB.
 *
 * @author  Ron Rickard
 */
@Stateless(mappedName="ejb/PreferenceManager")
@Remote(PreferenceManager.class)
public class PreferenceManagerEJB implements PreferenceManager {

    private static ConfigurationService configurationService
            = ConfigurationServiceFactory.create();
    private static PreferenceService preferenceService
            = PreferenceServiceFactory.create();
    private static SnapshotService snapshotService
            = SnapshotServiceFactory.create();

    /**
     * Clear the configuration.
     * 
     * @throws  ServiceException  if unable to clear the configuration.
     */
    @Override
    @RolesAllowed("ADMIN")
    public void clearConfiguration()
            throws ServiceException {

        // Remove the setup type.
        configurationService.removeSetupType();

        // Loop through the agents.
        for (Agent agent : configurationService.findAgents()) {

            // Remove the agent.
            configurationService.removeAgent(agent.getId());
        }
    }

    /**
     * Copy the preferences.
     *
     * @param  sourcePath  the source path.
     * @param  targetPath  the target path.
     *
     * @throws  ServiceException  if unable to copy the preferences.
     */
    @Override
    @RolesAllowed({"ADMIN","OPERATOR"})
    public void copyPreferences(String sourcePath, String targetPath)
            throws ServiceException {

        // Copy the preferences.
        preferenceService.copyPreferences(sourcePath, targetPath);

        // Synchronize the agents.
        synchronizeAgents();
    }

    /**
     * Create the snapshot.
     *
     * @param  name  the name.
     *
     * @throws  ServiceException  if unable to create the snapshot.
     */
    @Override
    @RolesAllowed({"ADMIN","OPERATOR"})
    public void createSnapshot(String name)
            throws ServiceException {
            snapshotService.createSnapshot(name);
    }

    /**
     * Get the environment.
     *
     * @param  agent  the agent.
     *
     * @return  the environment.
     */
    private Hashtable<String,String> getEnvironment(Agent agent) {

        // Declare.
        Hashtable<String,String> env;

        // Set the environment.
        env = new Hashtable<String,String>();
        env.put(RemoteSynchronizeService.JMX_PORT, agent.getJmxPort().toString());
        env.put(RemoteSynchronizeService.LOGIN, agent.getLogin());
        env.put(RemoteSynchronizeService.PASSWORD, agent.getPassword());
        env.put(RemoteSynchronizeService.SERVER_NAME, agent.getServerName());

        return env;
    }

    /**
     * Determine the status of the specified agent.
     *
     * @param  agent  the agent.
     *
     * @return  the status of the specified agent.
     */
    private AgentStatus determineStatus(Agent agent) {

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
    @Override
    @RolesAllowed({"ADMIN","OPERATOR"})
    public Agent disableAgent(int id) 
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
    @Override
    @RolesAllowed({"ADMIN","OPERATOR"})
    public Agent enableAgent(int id) 
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
     * Export the document.
     *
     * @return  the document.
     *
     * @throws  ServiceException  if unable to export the document.
     */
    @Override
    @RolesAllowed({"ADMIN","OPERATOR"})
    public String exportDocument()
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
     * Export the document remotely.
     *
     * @return  the document.
     *
     * @throws  ServiceException  if unable to export the document locally.
     */
    private String exportDocument(Agent agent)
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
     * Get the agent.
     * 
     * @param  id  the ID.
     * 
     * @return  the agent.
     *
     * @throws  ServiceException  if unable to get the agent.
     */
    @Override
    @RolesAllowed({"ADMIN","AUTHENTICATED","OPERATOR"})
    public Agent getAgent(int id) 
            throws ServiceException {

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
    @Override
    @RolesAllowed({"ADMIN","AUTHENTICATED","OPERATOR"})
    public List<Agent> getAgents() 
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
     * Get the preferences.
     *
     * @param  path  the path.
     *
     * @return  the preferences.
     *
     * @throws  ServiceException  if unable to get the preferences.
     */
    @Override
    @RolesAllowed({"ADMIN","AUTHENTICATED","OPERATOR"})
    public Map<String,String> getPreferences(String path)
            throws ServiceException {
        return preferenceService.findPreferences(path);
    }

    /**
     * Get the preference tree.
     *
     * @return  the preference tree.
     *
     * @throws  ServiceException  if unable to get the preference tree.
     */
    @Override
    @RolesAllowed({"ADMIN","AUTHENTICATED","OPERATOR"})
    public PreferencesTree getPreferencesTree()
            throws ServiceException {
        return preferenceService.findPreferencesTree();
    }

    /**
     * Get the snapshots.
     *
     * @return  the snapshots.
     *
     * @throws  ServiceException  if unable to get the snapshots.
     */
    @Override
    @RolesAllowed({"ADMIN","AUTHENTICATED","OPERATOR"})
    public Map<String,Date> getSnapshots()
            throws ServiceException {
        return snapshotService.findSnapshots();
    }

    /**
     * Import the document.
     *
     * @param  document  the document.
     *
     * @throws  ServiceException  if unable to import the document.
     */
    @Override
    @RolesAllowed({"ADMIN","OPERATOR"})
    public void importDocument(String document)
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
     * @param  document  the document.
     */
    private void importDocument(Agent agent, String document) {

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
     *
     * @throws  ServiceException  if unable to determine setup.
     */
    @Override
    public boolean isAgentSetup() 
            throws ServiceException {
        return (configurationService.findSetupType() == SetupType.AGENT) ?
                true : false;
    }

    /**
     * Is this a manager setup.
     *
     * @return  true if this is a manager setup, otherwise false.
     *
     * @throws  ServiceException  if unable to determine setup.
     */
    @Override
    public boolean isManagerSetup() 
            throws ServiceException {
        return (configurationService.findSetupType() == SetupType.MANAGER) ?
                true : false;
    }

    /**
     * Is this setup.
     *
     * @return  true if this is setup, otherwise false.
     *
     * @throws  ServiceException  if unable to determine setup.
     */
    @Override
    public boolean isSetup() 
            throws ServiceException {
        return (configurationService.findSetupType() != SetupType.UNKNOWN) ?
                true : false;
    }

    /**
     * Is this a standalone setup.
     *
     * @return  true if this is a standalone setup, otherwise false.
     *
     * @throws  ServiceException  if unable to determine setup.
     */
    @Override
    public boolean isStandaloneSetup() 
            throws ServiceException {
        return (configurationService.findSetupType() == SetupType.STANDALONE) ?
                true : false;
    }

    /**
     * Move the preferences.
     * 
     * @param  sourcePath  the source path.
     * @param  targetPath  the target path.
     * 
     * @throws  ServiceException  if unable to move the preferences.
     */
    @Override
    @RolesAllowed({"ADMIN","OPERATOR"})
    public void movePreferences(String sourcePath, String targetPath)
            throws ServiceException {

        // Move the preferences.
        preferenceService.movePreferences(sourcePath, targetPath);

        // Synchronize the agents.
        synchronizeAgents();
    }

    /**
     * Remove the agent.
     *
     * @param  id  the ID.
     *
     * @throws  ServiceException  if unable to remove the agent.
     */
    @Override
    @RolesAllowed("ADMIN")
    public void removeAgent(int id)
            throws ServiceException {
        configurationService.removeAgent(id);
    }

    /**
     * Remove the preference.
     * 
     * @param  path  the path.
     * @param  key   the key.
     *  
     * @throws  ServiceException  if unable to remove the preference.
     */
    @Override
    @RolesAllowed({"ADMIN","OPERATOR"})
    public void removePreference(String path, String key)
            throws ServiceException {

        // Remove the preference.
        preferenceService.removePreference(path, key);

        // Synchronize the agents.
        synchronizeAgents();
    }

    /**
     * Remove the preferences.
     *
     * @param  path  the path.
     *
     * @throws  ServiceException  if unable to remove the preferences.
     */
    @Override
    @RolesAllowed({"ADMIN","OPERATOR"})
    public void removePreferences(String path)
            throws ServiceException {

        // Remove the preferences.
        preferenceService.removePreferences(path);

        // Synchronize the agents.
        synchronizeAgents();
    }

    /**
     * Remove the snapshot.
     *
     * @param  name  the name.
     *
     * @throws  ServiceException  if unable to remove the snapshot.
     */
    @Override
    @RolesAllowed({"ADMIN","OPERATOR"})
    public void removeSnapshot(String name)
            throws ServiceException {
        snapshotService.removeSnapshot(name);
    }

    /**
     * Rename the snapshot.
     *
     * @param  sourceName  the source name.
     * @param  targetName  the target name.
     *
     * @throws  ServiceException  if unable to rename the snapshot.
     */
    @Override
    @RolesAllowed({"ADMIN","OPERATOR"})
    public void renameSnapshot(String sourceName, String targetName)
            throws ServiceException {
        snapshotService.renameSnapshot(sourceName, targetName);
    }

    /**
     * Restore the snapshot.
     *
     * @param  name  the name.
     *
     * @throws  ServiceException  if unable to restore the snapshot.
     */
    @Override
    @RolesAllowed({"ADMIN","OPERATOR"})
    public void restoreSnapshot(String name)
            throws ServiceException {

        // Restore the snapshot.
        snapshotService.restoreSnapshot(name);

        // Synchronize the agents.
        synchronizeAgents();
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
    @Override
    @RolesAllowed("ADMIN")
    public Agent saveAgent(Agent agent) 
            throws ServiceException {
        return configurationService.persistAgent(agent);
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
    @Override
    @RolesAllowed({"ADMIN"})
    public SetupType saveSetupType(SetupType setupType) 
            throws ServiceException {
        return configurationService.persistSetupType(setupType);
    }

    /**
     * Save the preference.
     *
     * @param  path   the path.
     * @param  key    the key.
     * @param  value  the value.
     *
     * @throws  ServiceException  if unable to save the preference.
     */
    @Override
    @RolesAllowed({"ADMIN","OPERATOR"})
    public void savePreference(String path, String key, String value)
            throws ServiceException {

        // Persist the preference.
        preferenceService.persistPreference(path, key, value);

        // Synchronize the agents.
        synchronizeAgents();
    }

    /**
     * Save the preferences.
     *
     * @param  path  the path.
     *
     * @throws  ServiceException  if unable to save the preferences.
     */
    @Override
    @RolesAllowed({"ADMIN","OPERATOR"})
    public void savePreferences(String path)
            throws ServiceException {

        // Persist the preferences.
        preferenceService.persistPreferences(path);

        // Synchronize the agents.
        synchronizeAgents();
    }

    /**
     * Synchronize the agent.
     *
     * @param  agent  the agent.
     *
     * @throws  ServiceException  if unable to synchronize the agent.
     */
    @Override
    @RolesAllowed({"ADMIN","OPERATOR"})
    public void synchronizeAgent(Agent agent)
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
    @Override
    @RolesAllowed({"ADMIN","OPERATOR"})
    public void synchronizeAgents()
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
}
