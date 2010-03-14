package org.lazydog.preference.manager.preference.service;

import java.util.List;
import java.util.Map;
import org.lazydog.preference.manager.model.PreferenceGroupTree;


/**
 * Preference service.
 *
 * @author  Ron Rickard
 */
public interface PreferenceService extends PreferenceGroupTree {

    public Object exportDocument()
            throws PreferenceServiceException;
    public List<PreferenceGroupTree> getChildren();
    public String getId();
    public Map<String,String> getPreferences();
    public void importDocument(Object document)
            throws PreferenceServiceException;
    public void remove()
            throws PreferenceServiceException;
    public void setId(String id)
            throws PreferenceServiceException;
    public void setPreferences(Map<String,String> preferences)
            throws PreferenceServiceException;
    @Override
    public String toString();
}
