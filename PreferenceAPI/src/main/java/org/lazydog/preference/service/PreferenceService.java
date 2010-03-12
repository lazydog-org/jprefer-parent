package org.lazydog.preference.service;


/**
 * Preference service.
 *
 * @author  Ron Rickard
 */
public interface PreferenceService {

    public static final String JMX_PORT = "jmxPort";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String SERVER_NAME = "serverName";

    public boolean areEqual(String preferences)
            throws PreferenceServiceException;
    public void createOrReplace(String pathName, String preferences)
            throws PreferenceServiceException;
    public String get(String pathName)
            throws PreferenceServiceException;
    public String getAll()
            throws PreferenceServiceException;
    public void remove(String pathName)
            throws PreferenceServiceException;
    public void replaceAll(String preferences)
            throws PreferenceServiceException;
}
