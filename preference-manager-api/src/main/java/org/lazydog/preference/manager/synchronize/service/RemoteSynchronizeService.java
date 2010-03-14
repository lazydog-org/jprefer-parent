package org.lazydog.preference.manager.synchronize.service;

import java.util.Hashtable;


/**
 * Remote synchronize service.
 *
 * @author  Ron Rickard
 */
public interface RemoteSynchronizeService extends SynchronizeService {

    public Hashtable<String,String> getEnvironment();

    public void setEnvironment(Hashtable<String,String> environment);
}
