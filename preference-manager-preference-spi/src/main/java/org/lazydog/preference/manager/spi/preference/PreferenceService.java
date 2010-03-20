package org.lazydog.preference.manager.spi.preference;

import java.util.Map;
import org.lazydog.preference.manager.model.PreferencesTree;
import org.lazydog.preference.manager.ServiceException;


/**
 * Preference service.
 *
 * @author  Ron Rickard
 */
public interface PreferenceService {

    public void copyPreferences(String sourcePath, String targetPath)
            throws ServiceException;
    public Map<String,String> findPreferences(String path)
            throws ServiceException;
    public PreferencesTree findPreferencesTree()
            throws ServiceException;
    public void movePreferences(String sourcePath, String targetPath)
            throws ServiceException;
    public String persistPreference(String path, String key, String value)
            throws ServiceException;
    public String persistPreferences(String path)
            throws ServiceException;
    public void removePreference(String path, String key)
            throws ServiceException;
    public void removePreferences(String path)
            throws ServiceException;
}
