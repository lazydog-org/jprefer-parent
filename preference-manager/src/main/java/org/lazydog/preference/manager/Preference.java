package org.lazydog.preference.manager;

import java.util.Map;
import org.lazydog.preference.manager.model.PreferencesTree;
import org.lazydog.preference.manager.ServiceException;
import org.lazydog.preference.manager.spi.preference.PreferenceService;
import org.lazydog.preference.manager.spi.preference.PreferenceServiceFactory;


/**
 * Preference.
 *
 * @author  Ron Rickard
 */
public class Preference {

    private static PreferenceService preferenceService
            = PreferenceServiceFactory.create();

    public static void copyPreferences(String sourcePath, String targetPath)
            throws ServiceException {

        // Copy the preferences.
        preferenceService.copyPreferences(sourcePath, targetPath);

        // Synchronize the agents.
        Configuration.synchronizeAgents();
    }

    public static PreferencesTree getPreferencesTree()
            throws ServiceException {
        return preferenceService.findPreferencesTree();
    }

    public static Map<String,String> getPreferences(String path)
            throws ServiceException {
        return preferenceService.findPreferences(path);
    }

    public static void movePreferences(String sourcePath, String targetPath)
            throws ServiceException {

        // Move the preferences.
        preferenceService.movePreferences(sourcePath, targetPath);

        // Synchronize the agents.
        Configuration.synchronizeAgents();
    }
    
    public static void removePreference(String path, String key)
            throws ServiceException {

        // Remove the preference.
        preferenceService.removePreference(path, key);

        // Synchronize the agents.
        Configuration.synchronizeAgents();
    }

    public static void removePreferences(String path)
            throws ServiceException {

        // Remove the preferences.
        preferenceService.removePreferences(path);

        // Synchronize the agents.
        Configuration.synchronizeAgents();
    }

    public static void savePreference(String path, String key, String value)
            throws ServiceException {

        // Persist the preference.
        preferenceService.persistPreference(path, key, value);

        // Synchronize the agents.
        Configuration.synchronizeAgents();
    }

    public static void savePreferences(String path)
            throws ServiceException {

        // Persist the preferences.
        preferenceService.persistPreferences(path);

        // Synchronize the agents.
        Configuration.synchronizeAgents();
    }
}
