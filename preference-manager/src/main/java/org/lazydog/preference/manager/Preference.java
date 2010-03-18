package org.lazydog.preference.manager;

import java.util.Map;
import org.lazydog.preference.manager.model.PreferencesTree;
import org.lazydog.preference.manager.preference.service.PreferenceService;
import org.lazydog.preference.manager.preference.service.PreferenceServiceFactory;
import org.lazydog.preference.manager.service.ServiceException;


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
        preferenceService.copyPreferences(sourcePath, targetPath);
    }

    public static String exportDocument()
            throws ServiceException {
        return (String)preferenceService.exportDocument();
    }
    
    public static PreferencesTree getPreferencesTree()
            throws ServiceException {
        return preferenceService.findPreferencesTree();
    }

    public static Map<String,String> getPreferences(String path)
            throws ServiceException {
        return preferenceService.findPreferences(path);
    }

    public static void importDocument(String document)
            throws ServiceException {
        preferenceService.importDocument(document);
    }

    public static void movePreferences(String sourcePath, String targetPath)
            throws ServiceException {
        preferenceService.movePreferences(sourcePath, targetPath);
    }
    
    public static void removePreference(String path, String key)
            throws ServiceException {
        preferenceService.removePreference(path, key);
    }

    public static void removePreferences(String path)
            throws ServiceException {
        preferenceService.removePreferences(path);
    }

    public static void savePreference(String path, String key, String value)
            throws ServiceException {
        preferenceService.persistPreference(path, key, value);
    }

    public static void savePreferences(String path)
            throws ServiceException {
        preferenceService.persistPreferences(path);
    }
}
