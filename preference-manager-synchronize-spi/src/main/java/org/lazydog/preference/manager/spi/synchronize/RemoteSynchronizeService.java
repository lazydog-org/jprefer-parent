package org.lazydog.preference.manager.spi.synchronize;

import java.util.Hashtable;


/**
 * Remote synchronize service.
 *
 * @author  Ron Rickard
 */
public interface RemoteSynchronizeService extends SynchronizeService {

    public static final String JMX_PORT = "jmxPort";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String SERVER_NAME = "serverName";

    public Hashtable<String,String> getEnvironment();

    public void setEnvironment(Hashtable<String,String> environment);
}
