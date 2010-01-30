package org.lazydog.preference.api;

import org.lazydog.preference.exception.PreferenceServiceException;


/**
 * Preference service.
 *
 * @author  Ron Rickard
 */
public interface PreferenceService {

    public static final String XMLSTRING_ENCODING = "UTF-8";

    public void createOrReplaceNode(String pathName, String xmlString)
            throws PreferenceServiceException;
    public void removeNode(String pathName)
            throws PreferenceServiceException;
    public String getAllNodes()
            throws PreferenceServiceException;
    public String getNode(String pathName)
            throws PreferenceServiceException;
    public void replaceAllNodes(String xmlString)
            throws PreferenceServiceException;
}
