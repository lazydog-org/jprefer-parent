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
package org.lazydog.preference.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.lazydog.preference.manager.model.Agent;
import org.lazydog.preference.manager.model.Preference;
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
     * Copy the preference path.
     *
     * @param  sourcePath  the source path.
     * @param  targetPath  the target path.
     *
     * @throws  ServiceException  if unable to copy the preference path.
     */
    public void copyPreferencePath(String sourcePath, String targetPath)
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
     * Get the preferences tree.
     *
     * @return  the preferences tree.
     *
     * @throws  ServiceException  if unable to get the preferences tree.
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
     * Move the preference path.
     * 
     * @param  sourcePath  the source path.
     * @param  targetPath  the target path.
     * 
     * @throws  ServiceException  if unable to move the preference path.
     */
    public void movePreferencePath(String sourcePath, String targetPath)
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
     * Remove the preference path.
     *
     * @param  path  the path.
     *
     * @throws  ServiceException  if unable to remove the preference path.
     */
    public void removePreferencePath(String path)
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
     * @throws  ServiceException  if unable to save the agent.
     */
    public void saveAgent(Agent agent)
            throws ServiceException;

    /**
     * Save the setup type.
     *
     * @param  setupType  the setup type.
     *
     * @throws  ServiceException  if unable to save the setup type.
     */
    public void saveSetupType(SetupType setupType)
            throws ServiceException;

    /**
     * Save the preference.
     *
     * @param  preference   the preference.
     *
     * @throws  ServiceException  if unable to save the preference.
     */
    public void savePreference(Preference preference)
            throws ServiceException;

    /**
     * Save the preference path.
     *
     * @param  path  the path.
     *
     * @throws  ServiceException  if unable to save the preference path.
     */
    public void savePreferencePath(String path)
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
