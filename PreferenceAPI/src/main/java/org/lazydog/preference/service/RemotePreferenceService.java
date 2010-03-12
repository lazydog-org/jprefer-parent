package org.lazydog.preference.service;

import java.util.Hashtable;


/**
 * Remote preference service.
 *
 * @author  Ron Rickard
 */
public interface RemotePreferenceService extends PreferenceService {

    public Hashtable<String,String> getEnvironment();

    public void setEnvironment(Hashtable<String,String> environment);
}
