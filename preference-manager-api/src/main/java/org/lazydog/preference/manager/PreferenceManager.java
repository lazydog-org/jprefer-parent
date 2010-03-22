package org.lazydog.preference.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.lazydog.preference.manager.model.Agent;
import org.lazydog.preference.manager.model.PreferencesTree;
import org.lazydog.preference.manager.model.SetupType;


/**
 * Preference manager.
 *
 * @author  Ron Rickard
 */
public interface PreferenceManager {

    /**
     * Clear the configuration.
     * 
     * @throws  ServiceException  if unable to clear the configuration.
     */
    public void clearConfiguration()
            throws ServiceException;

    /**
     * Copy the preferences.
     *
     * @param  sourcePath  the source path.
     * @param  targetPath  the target path.
     *
     * @throws  ServiceException  if unable to copy the preferences.
     */
    public void copyPreferences(String sourcePath, String targetPath)
            throws ServiceException;

    /**
     * Create the snapshot.
     *
     * @param  name  the name.
     *
     * @throws  ServiceException  if unable to create the snapshot.
     */
    public void createSnapshot(String name)
            throws ServiceException;

    /**
     * Disable the agent.
     * 
     * @param  id  the ID.
     * 
     * @return  the agent.
     *
     * @throws  ServiceException  if unable to disable the agent.
     */
    public Agent disableAgent(int id) 
            throws ServiceException;

    /**
     * Enable the agent.
     *
     * @param  id  the ID.
     *
     * @return  the agent.
     *
     * @throws  ServiceException  if unable to enable the agent.
     */
    public Agent enableAgent(int id) 
            throws ServiceException;

    /**
     * Export the document.
     *
     * @return  the document.
     *
     * @throws  ServiceException  if unable to export the document.
     */
    public String exportDocument()
            throws ServiceException;

    /**
     * Get the agent.
     * 
     * @param  id  the ID.
     * 
     * @return  the agent.
     *
     * @throws  ServiceException  if unable to get the agent.
     */
    public Agent getAgent(int id)
            throws ServiceException;

    /**
     * Get the agents.
     *
     * @return  the agents.
     *
     * @throws  ServiceException  if unable to get the agents.
     */
    public List<Agent> getAgents() 
            throws ServiceException;

    /**
     * Get the preferences.
     *
     * @param  path  the path.
     *
     * @return  the preferences.
     *
     * @throws  ServiceException  if unable to get the preferences.
     */
    public Map<String,String> getPreferences(String path)
            throws ServiceException;

    /**
     * Get the preference tree.
     *
     * @return  the preference tree.
     *
     * @throws  ServiceException  if unable to get the preference tree.
     */
    public PreferencesTree getPreferencesTree()
            throws ServiceException;

    /**
     * Get the setup type.
     * 
     * @return  the setup type.
     * 
     * @throws  ServiceException  if unable to get the setup type.
     */
    public SetupType getSetupType()
            throws ServiceException;

    /**
     * Get the snapshots.
     *
     * @return  the snapshots.
     *
     * @throws  ServiceException  if unable to get the snapshots.
     */
    public Map<String,Date> getSnapshots()
            throws ServiceException;

    /**
     * Import the document.
     *
     * @param  document  the document.
     *
     * @throws  ServiceException  if unable to import the document.
     */
    public void importDocument(String document)
            throws ServiceException;

    /**
     * Move the preferences.
     * 
     * @param  sourcePath  the source path.
     * @param  targetPath  the target path.
     * 
     * @throws  ServiceException  if unable to move the preferences.
     */
    public void movePreferences(String sourcePath, String targetPath)
            throws ServiceException;

    /**
     * Remove the agent.
     *
     * @param  id  the ID.
     *
     * @throws  ServiceException  if unable to remove the agent.
     */
    public void removeAgent(int id)
            throws ServiceException;

    /**
     * Remove the preference.
     * 
     * @param  path  the path.
     * @param  key   the key.
     *  
     * @throws  ServiceException  if unable to remove the preference.
     */
    public void removePreference(String path, String key)
            throws ServiceException;

    /**
     * Remove the preferences.
     *
     * @param  path  the path.
     *
     * @throws  ServiceException  if unable to remove the preferences.
     */
    public void removePreferences(String path)
            throws ServiceException;

    /**
     * Remove the snapshot.
     *
     * @param  name  the name.
     *
     * @throws  ServiceException  if unable to remove the snapshot.
     */
    public void removeSnapshot(String name)
            throws ServiceException;

    /**
     * Rename the snapshot.
     *
     * @param  sourceName  the source name.
     * @param  targetName  the target name.
     *
     * @throws  ServiceException  if unable to rename the snapshot.
     */
    public void renameSnapshot(String sourceName, String targetName)
            throws ServiceException;

    /**
     * Restore the snapshot.
     *
     * @param  name  the name.
     *
     * @throws  ServiceException  if unable to restore the snapshot.
     */
    public void restoreSnapshot(String name)
            throws ServiceException;

    /**
     * Save the agent.
     *
     * @param  agent  the agent.
     *
     * @return  the agent.
     *
     * @throws  ServiceException  if unable to save the agent.
     */
    public Agent saveAgent(Agent agent) 
            throws ServiceException;

    /**
     * Save the setup type.
     *
     * @param  setupType  the setup type.
     *
     * @return  the setup type.
     *
     * @throws  ServiceException  if unable to save the setup type.
     */
    public SetupType saveSetupType(SetupType setupType) 
            throws ServiceException;

    /**
     * Save the preference.
     *
     * @param  path   the path.
     * @param  key    the key.
     * @param  value  the value.
     *
     * @throws  ServiceException  if unable to save the preference.
     */
    public void savePreference(String path, String key, String value)
            throws ServiceException;

    /**
     * Save the preferences.
     *
     * @param  path  the path.
     *
     * @throws  ServiceException  if unable to save the preferences.
     */
    public void savePreferences(String path)
            throws ServiceException;

    /**
     * Synchronize the agent.
     *
     * @param  agent  the agent.
     *
     * @throws  ServiceException  if unable to synchronize the agent.
     */
    public void synchronizeAgent(Agent agent)
            throws ServiceException;

    /**
     * Synchronize the agents.
     *
     * @throws  ServiceException  if unable to synchronize the agents.
     */
    public void synchronizeAgents()
            throws ServiceException;
}
