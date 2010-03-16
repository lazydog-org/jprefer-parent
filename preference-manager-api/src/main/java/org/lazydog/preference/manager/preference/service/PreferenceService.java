package org.lazydog.preference.manager.preference.service;

import org.lazydog.preference.manager.model.PreferenceGroup;
import org.lazydog.preference.manager.model.PreferenceGroupTree;
import org.lazydog.preference.manager.service.ServiceException;


/**
 * Preference service.
 *
 * @author  Ron Rickard
 */
public interface PreferenceService {

    public void copyPreferences(
            String sourcePath, String targetPath)
            throws ServiceException;
    public Object exportDocument()
            throws ServiceException;
    public Object exportDocument(String path)
            throws ServiceException;
    public PreferenceGroup findPreferenceGroup(String path)
            throws ServiceException;
    public PreferenceGroupTree findPreferenceGroupTree();
    public void importDocument(Object document)
            throws ServiceException;
    public void importDocument(String path, Object document)
            throws ServiceException;
    public void movePreferences(
            String sourcePath, String targetPath)
            throws ServiceException;
    public void persistPreferenceGroup(PreferenceGroup preferenceGroup)
            throws ServiceException;
    public void removePreferences(String path)
            throws ServiceException;
}
