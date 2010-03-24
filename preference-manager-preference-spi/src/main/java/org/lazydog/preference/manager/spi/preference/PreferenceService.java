package org.lazydog.preference.manager.spi.preference;

import java.util.Map;
import org.lazydog.preference.manager.model.Preference;
import org.lazydog.preference.manager.model.PreferencesTree;
import org.lazydog.preference.manager.ServiceException;


/**
 * Preference service.
 *
 * @author  Ron Rickard
 */
public interface PreferenceService {

    public void copyPreferencePath(String sourcePath, String targetPath)
            throws ServiceException;
    public Map<String,String> findPreferences(String path)
            throws ServiceException;
    public PreferencesTree findPreferencesTree()
            throws ServiceException;
    public void movePreferencePath(String sourcePath, String targetPath)
            throws ServiceException;
    public Preference persistPreference(Preference preference)
            throws ServiceException;
    public String persistPreferencePath(String path)
            throws ServiceException;
    public void removePreference(String path, String key)
            throws ServiceException;
    public void removePreferencePath(String path)
            throws ServiceException;
}
