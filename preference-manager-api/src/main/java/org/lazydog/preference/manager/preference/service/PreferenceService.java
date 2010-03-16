package org.lazydog.preference.manager.preference.service;

import org.lazydog.preference.manager.model.PreferenceGroup;
import org.lazydog.preference.manager.model.PreferenceGroupTree;


/**
 * Preference service.
 *
 * @author  Ron Rickard
 */
public interface PreferenceService {

    public void copyPreferenceGroup(
            String sourceAbsolutePath, String targetAbsolutePath)
            throws PreferenceServiceException;
    public Object exportDocument()
            throws PreferenceServiceException;
    public Object exportDocument(String absolutePath)
            throws PreferenceServiceException;
    public PreferenceGroup findPreferenceGroup(String absolutePath)
            throws PreferenceServiceException;
    public PreferenceGroupTree findPreferenceGroupTree()
            throws  PreferenceServiceException;
    public void importDocument(Object document)
            throws PreferenceServiceException;
    public void importDocument(String absolutePath, Object document)
            throws PreferenceServiceException;
    public void movePreferenceGroup(
            String sourceAbsolutePath, String targetAbsolutePath)
            throws PreferenceServiceException;
    public void persistPreferenceGroup(PreferenceGroup preferenceGroup)
            throws PreferenceServiceException;
    public void removePreferenceGroup(String absolutePath)
            throws PreferenceServiceException;
}
