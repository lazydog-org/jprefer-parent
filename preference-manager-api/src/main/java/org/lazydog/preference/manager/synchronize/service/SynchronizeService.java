package org.lazydog.preference.manager.synchronize.service;


/**
 * Synchronize service.
 *
 * @author  Ron Rickard
 */
public interface SynchronizeService {

    public static final String JMX_PORT = "jmxPort";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String SERVER_NAME = "serverName";

    public Object exportDocument(String id)
            throws SynchronizeServiceException;
    public Object exportDocument()
            throws SynchronizeServiceException;
    public void importDocument(String id, Object document)
            throws SynchronizeServiceException;
    public void importDocument(Object document)
            throws SynchronizeServiceException;
}
