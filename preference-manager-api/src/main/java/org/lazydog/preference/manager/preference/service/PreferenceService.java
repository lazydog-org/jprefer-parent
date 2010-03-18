package org.lazydog.preference.manager.preference.service;

import java.util.Map;
import org.lazydog.preference.manager.model.PreferencesTree;
import org.lazydog.preference.manager.service.ServiceException;


/**
 * Preference service.
 *
 * @author  Ron Rickard
 */
public interface PreferenceService {

    public void copyPreferences(String sourcePath, String targetPath)
            throws ServiceException;
    public String exportDocument()
            throws ServiceException;
    public String exportDocument(String path)
            throws ServiceException;
    public Map<String,String> findPreferences(String path)
            throws ServiceException;
    public PreferencesTree findPreferencesTree()
            throws ServiceException;
    public void importDocument(String document)
            throws ServiceException;
    public void importDocument(String path, String document)
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
