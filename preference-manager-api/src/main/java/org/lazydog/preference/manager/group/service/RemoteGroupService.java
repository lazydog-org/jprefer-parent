package org.lazydog.preference.manager.group.service;

import java.util.Hashtable;


/**
 * Remote group service.
 *
 * @author  Ron Rickard
 */
public interface RemoteGroupService extends GroupService {

    public Hashtable<String,String> getEnvironment();

    public void setEnvironment(Hashtable<String,String> environment);
}
