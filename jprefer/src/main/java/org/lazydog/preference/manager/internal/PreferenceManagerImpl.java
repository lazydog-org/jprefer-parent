/**
 * Copyright 2009, 2010 lazydog.org.
 *
 * This file is part of JPrefer.
 *
 * JPrefer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JPrefer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Preference Manager.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.lazydog.preference.manager.internal;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.DecoderException;
import org.lazydog.preference.manager.model.Agent;
import org.lazydog.preference.manager.model.AgentStatus;
import org.lazydog.preference.manager.model.Preference;
import org.lazydog.preference.manager.model.PreferencesTree;
import org.lazydog.preference.manager.model.SetupType;
import org.lazydog.preference.manager.PreferenceManager;
import org.lazydog.preference.manager.ServiceException;
import org.lazydog.preference.manager.spi.configuration.ConfigurationService;
import org.lazydog.preference.manager.spi.configuration.ConfigurationServiceFactory;
import org.lazydog.preference.manager.spi.preference.PreferenceService;
import org.lazydog.preference.manager.spi.preference.PreferenceServiceFactory;
import org.lazydog.preference.manager.spi.snapshot.SnapshotService;
import org.lazydog.preference.manager.spi.snapshot.SnapshotServiceFactory;
import org.lazydog.preference.manager.spi.synchronize.AgentSynchronizeService;
import org.lazydog.preference.manager.spi.synchronize.AgentSynchronizeServiceFactory;
import org.lazydog.preference.manager.spi.synchronize.RemoteSynchronizeService;
import org.lazydog.preference.manager.spi.synchronize.SynchronizeService;
import org.lazydog.preference.manager.spi.synchronize.SynchronizeServiceFactory;
import org.lazydog.preference.manager.utility.MBeanUtility;
import org.lazydog.preference.manager.utility.MBeanUtilityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Preference manager implementation.
 *
 * @author  Ron Rickard
 */
public class PreferenceManagerImpl implements PreferenceManager {

    private static final Logger logger = LoggerFactory.getLogger(PreferenceManagerImpl.class);
    private static final ConfigurationService configurationService
            = ConfigurationServiceFactory.create();
    private static final PreferenceService preferenceService
            = PreferenceServiceFactory.create();
    private static final SnapshotService snapshotService
            = SnapshotServiceFactory.create();

    /**
     * Clear the configuration.
     * 
     * @throws  ServiceException  if unable to clear the configuration.
     */
    @Override
    public void clearConfiguration()
            throws ServiceException {

        try {

            // Remove the setup type.
            configurationService.removeSetupType();

            // Loop through the agents.
            for (Agent agent : configurationService.findAgents()) {

                // Remove the agent.
                configurationService.removeAgent(agent.getId());
            }

            // Unregister the agent synchronize service MBean.
            MBeanUtility.unregister(AgentSynchronizeService.OBJECT_NAME);
        }
        catch(MBeanUtilityException e) {
            logger.error("Unable to clear configuration.", e);
            throw new ServiceException("Unable to clear configuration.", e);
        }
        catch(ServiceException e) {
            logger.error("Unable to clear configuration.", e);
            throw e;
        }
    }

    /**
     * Copy the preference path.
     *
     * @param  sourcePath  the source path.
     * @param  targetPath  the target path.
     *
     * @throws  ServiceException  if unable to copy the preference path.
     */
    @Override
    public void copyPreferencePath(String sourcePath, String targetPath)
            throws ServiceException {

        try {

            // Copy the preference path.
            preferenceService.copyPreferencePath(sourcePath, targetPath);

            // Synchronize the agents.
            synchronizeAgents();
        }
        catch(ServiceException e) {
            logger.error("Unable to copy the preference path.", e);
            throw e;
        }
    }

    /**
     * Create the snapshot.
     *
     * @param  name  the name.
     *
     * @throws  ServiceException  if unable to create the snapshot.
     */
    @Override
    public void createSnapshot(String name)
            throws ServiceException {

        try {
            
            // Create the snapshot.
            snapshotService.createSnapshot(name);
        }
        catch(ServiceException e) {
            logger.error("Unable to create the snapshot.", e);
            throw e;
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
        env.put(RemoteSynchronizeService.JMX_PORT, agent.getJmxPort().toString());
        env.put(RemoteSynchronizeService.LOGIN, agent.getLogin());
        env.put(RemoteSynchronizeService.PASSWORD, agent.getPassword());
        env.put(RemoteSynchronizeService.SERVER_NAME, agent.getServerName());

        return env;
    }

    /**
     * Decode the data.
     *
     * @param  data  the data.
     *
     * @return  the data decoded.
     *
     * @throws  IllegalArgumentException  if the data cannot be decoded.
     */
    private static String decode(String data) {

        // Declare.
        String decodedData;

        // Initialize.
        decodedData = null;

        try {

            // Check if the data is not null.
            if (data != null) {

                // Decode the data.
                decodedData = new String(Hex.decodeHex(data.toCharArray()));
            }
        }
        catch(DecoderException e) {
            throw new IllegalArgumentException(
                    "The data cannot be decoded.", e);
        }

        return decodedData;
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
    public Agent disableAgent(int id) 
            throws ServiceException {

        // Declare.
        Agent agent;

        try {

            // Disable the agent.
            agent = configurationService.findAgent(id);
            agent.setEnabled(Boolean.FALSE);
            agent = configurationService.persistAgent(agent);
        }
        catch(ServiceException e) {
            logger.error("Unable to disable the agent.", e);
            throw e;
        }

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
    public Agent enableAgent(int id) 
            throws ServiceException {

        // Declare.
        Agent agent;

        try {
            
            // Enable the agent.
            agent = configurationService.findAgent(id);
            agent.setEnabled(Boolean.TRUE);
            agent = configurationService.persistAgent(agent);
        }
        catch(ServiceException e) {
            logger.error("Unable to enable the agent.", e);
            throw e;
        }

        return agent;
    }

    /**
     * Encode the data.
     *
     * @param  data  the data.
     *
     * @return  the data encoded.
     */
    private static String encode(String data) {

        // Declare.
        String encodedData;

        // Initialize.
        encodedData = null;

        // Check if the data is not null.
        if (data != null) {

            // Encode the data.
            encodedData = Hex.encodeHexString(data.getBytes());
        }

        return encodedData;
    }

    /**
     * Export the document.
     *
     * @return  the document.
     *
     * @throws  ServiceException  if unable to export the document.
     */
    @Override
    public String exportDocument()
            throws ServiceException {

        // Declare.
        String document;

        try {

            // Declare.
            SynchronizeService synchronizeService;

            // Export the document locally.
            synchronizeService = SynchronizeServiceFactory.create();
            document = synchronizeService.exportDocument();
        }
        catch(ServiceException e) {
            logger.error("Unable to export the document.", e);
            throw e;
        }

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
    public Agent getAgent(int id) 
            throws ServiceException {

        // Declare.
        Agent agent;

        try {
            
            // Get the agent.
            agent = configurationService.findAgent(id);

            // Check if the password exists.
            if (agent.getPassword() != null) {

                // Decode the password.
                agent.setPassword(decode(agent.getPassword()));
            }

            // Set the status for the agent.
            agent.setStatus(determineStatus(agent));
        }
        catch(ServiceException e) {
            logger.error("Unable to get the agent.", e);
            throw e;
        }

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
    public List<Agent> getAgents() 
            throws ServiceException {

        // Declare.
        List<Agent> agents;

        try {
            
            // Get the agents.
            agents = configurationService.findAgents();

            // Loop through the agents.
            for (Agent agent : agents) {

                // Check if the password exists.
                if (agent.getPassword() != null) {

                    // Decode the password.
                    agent.setPassword(decode(agent.getPassword()));
                }

                // Set the status for the agent.
                agent.setStatus(determineStatus(agent));
            }
        }
        catch(ServiceException e) {
            logger.error("Unable to get the agents.", e);
            throw e;
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
    public Map<String,String> getPreferences(String path)
            throws ServiceException {

        // Declare.
        Map<String,String> preferences;

        try {

            // Find the preferences.
            preferences = preferenceService.findPreferences(path);
        }
        catch(ServiceException e) {
            logger.error("Unable to get the preferences.", e);
            throw e;
        }

        return preferences;
    }

    /**
     * Get the preferences tree.
     *
     * @return  the preferences tree.
     *
     * @throws  ServiceException  if unable to get the preferences tree.
     */
    @Override
    public PreferencesTree getPreferencesTree()
            throws ServiceException {

        // Declare.
        PreferencesTree preferencesTree;

        try {

            // Find the preferences tree.
            preferencesTree = preferenceService.findPreferencesTree();
        }
        catch(ServiceException e) {
            logger.error("Unable to get the preferences tree.", e);
            throw e;
        }

        return preferencesTree;
    }

    /**
     * Get the setup type.
     *
     * @return  the setup type.
     *
     * @throws  ServiceException  if unable to get the setup type.
     */
    @Override
    public SetupType getSetupType()
            throws ServiceException {

        // Declare.
        SetupType setupType;

        try {

            // Find the setup type.
            setupType = configurationService.findSetupType();
        }
        catch(ServiceException e) {
            logger.error("Unable to get the setup type.", e);
            throw e;
        }

        return setupType;
    }

    /**
     * Get the snapshots.
     *
     * @return  the snapshots.
     *
     * @throws  ServiceException  if unable to get the snapshots.
     */
    @Override
    public Map<String,Date> getSnapshots()
            throws ServiceException {

        // Declare.
        Map<String,Date> snapshots;

        try {
        
            // Find the snapshots.
            snapshots = snapshotService.findSnapshots();
        }
        catch(ServiceException e) {
            logger.error("Unable to get the snapshots.", e);
            throw e;
        }

        return snapshots;
    }

    /**
     * Import the document.
     *
     * @param  document  the document.
     *
     * @throws  ServiceException  if unable to import the document.
     */
    @Override
    public void importDocument(String document)
            throws ServiceException {

        // Declare.
        SynchronizeService synchronizeService;

        try {
            
            // Import the document locally.
            synchronizeService = SynchronizeServiceFactory.create();
            synchronizeService.importDocument(document);

            // Synchronize the agents.
            synchronizeAgents();
        }
        catch(ServiceException e) {
            logger.error("Unable to import the document.", e);
            throw e;
        }
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
     * Move the preference path.
     * 
     * @param  sourcePath  the source path.
     * @param  targetPath  the target path.
     * 
     * @throws  ServiceException  if unable to move the preference path.
     */
    @Override
    public void movePreferencePath(String sourcePath, String targetPath)
            throws ServiceException {

        try {
            
            // Move the preference path.
            preferenceService.movePreferencePath(sourcePath, targetPath);

            // Synchronize the agents.
            synchronizeAgents();
        }
        catch(ServiceException e) {
            logger.error("Unable to move the preference path.", e);
            throw e;
        }
    }

    /**
     * Remove the agent.
     *
     * @param  id  the ID.
     *
     * @throws  ServiceException  if unable to remove the agent.
     */
    @Override
    public void removeAgent(int id)
            throws ServiceException {

        try {
            
            // Remove the agent.
            configurationService.removeAgent(id);
        }
        catch(ServiceException e) {
            logger.error("Unable to remove the agent.", e);
            throw e;
        }
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
    public void removePreference(String path, String key)
            throws ServiceException {

        try {
            
            // Remove the preference.
            preferenceService.removePreference(path, key);

            // Synchronize the agents.
            synchronizeAgents();
        }
        catch(ServiceException e) {
            logger.error("Unable to remove the preference.", e);
            throw e;
        }
    }

    /**
     * Remove the preference path.
     *
     * @param  path  the path.
     *
     * @throws  ServiceException  if unable to remove the preference path.
     */
    @Override
    public void removePreferencePath(String path)
            throws ServiceException {

        try {
            
            // Remove the preference path.
            preferenceService.removePreferencePath(path);

            // Synchronize the agents.
            synchronizeAgents();
        }
        catch(ServiceException e) {
            logger.error("Unable to remove the preference path.", e);
            throw e;
        }
    }

    /**
     * Remove the snapshot.
     *
     * @param  name  the name.
     *
     * @throws  ServiceException  if unable to remove the snapshot.
     */
    @Override
    public void removeSnapshot(String name)
            throws ServiceException {

        try {
            
            // Remove the snapshot.
            snapshotService.removeSnapshot(name);
        }
        catch(ServiceException e) {
            logger.error("Unable to remove the snapshot.", e);
            throw e;
        }
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
    public void renameSnapshot(String sourceName, String targetName)
            throws ServiceException {

        try {
            
            // Rename the snapshot.
            snapshotService.renameSnapshot(sourceName, targetName);
        }
        catch(ServiceException e) {
            logger.error("Unable to rename the snapshot.", e);
            throw e;
        }
    }

    /**
     * Restore the snapshot.
     *
     * @param  name  the name.
     *
     * @throws  ServiceException  if unable to restore the snapshot.
     */
    @Override
    public void restoreSnapshot(String name)
            throws ServiceException {

        try {
            
            // Restore the snapshot.
            snapshotService.restoreSnapshot(name);

            // Synchronize the agents.
            synchronizeAgents();
        }
        catch(ServiceException e) {
            logger.error("Unable to restore the snapshot.", e);
            throw e;
        }
    }

    /**
     * Save the agent.
     *
     * @param  agent  the agent.
     *
     * @throws  ServiceException  if unable to save the agent.
     */
    @Override
    public void saveAgent(Agent agent)
            throws ServiceException {

        try {
            
            // Check if the password exists.
            if (agent.getPassword() != null) {

                // Encode the password.
                agent.setPassword(encode(agent.getPassword()));
            }

            // Persist the agent.
            configurationService.persistAgent(agent);
        }
        catch(ServiceException e) {
            logger.error("Unable to save the agent.", e);
            throw e;
        }
    }

    /**
     * Save the setup type.
     *
     * @param  setupType  the setup type.
     *
     * @throws  ServiceException  if unable to save the setup type.
     */
    @Override
    public void saveSetupType(SetupType setupType)
            throws ServiceException {

        try {

            // Persist the setup type.
            configurationService.persistSetupType(setupType);

            // Check if the setup type is an agent setup type.
            if (setupType == SetupType.AGENT) {

                // Register the agent synchronize service MBean.
                MBeanUtility.register(AgentSynchronizeService.OBJECT_NAME,
                        AgentSynchronizeServiceFactory.create());
            }
        }
        catch(MBeanUtilityException e) {
            logger.error("Unable to save the setup type.", e);
            throw new ServiceException("Unable to save the setup type.", e);
        }
        catch(ServiceException e) {
            logger.error("Unable to save the setup type.", e);
            throw e;
        }
    }

    /**
     * Save the preference.
     *
     * @param  preference  the preference.
     *
     * @throws  ServiceException  if unable to save the preference.
     */
    @Override
    public void savePreference(Preference preference)
            throws ServiceException {

        try {

            // Persist the preference.
            preferenceService.persistPreference(preference);

            // Synchronize the agents.
            synchronizeAgents();
        }
        catch(ServiceException e) {
            logger.error("Unable to save the preference.", e);
            throw e;
        }
    }

    /**
     * Save the preference path.
     *
     * @param  path  the path.
     *
     * @throws  ServiceException  if unable to save the preference path.
     */
    @Override
    public void savePreferencePath(String path)
            throws ServiceException {

        try {

            // Persist the preference path.
            preferenceService.persistPreferencePath(path);

            // Synchronize the agents.
            synchronizeAgents();
        }
        catch(ServiceException e) {
            logger.error("Unable to save the preference path.", e);
            throw e;
        }
    }

    /**
     * Synchronize the agent.
     *
     * @param  agent  the agent.
     *
     * @throws  ServiceException  if unable to synchronize the agent.
     */
    @Override
    public void synchronizeAgent(Agent agent)
            throws ServiceException {

        try {

            // Declare.
            String document;

            // Export the document locally.
            document = exportDocument();

            // Import the document remotely.
            importDocument(agent, document);
        }
        catch(ServiceException e) {
            logger.error("Unable to synchronize the agent.", e);
            throw e;
        }
    }

    /**
     * Synchronize the agents.
     *
     * @throws  ServiceException  if unable to synchronize the agents.
     */
    @Override
    public void synchronizeAgents()
            throws ServiceException {

        try {
            
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
        catch(ServiceException e) {
            logger.error("Unable to synchronize the agents.", e);
            throw e;
        }
    }
}
