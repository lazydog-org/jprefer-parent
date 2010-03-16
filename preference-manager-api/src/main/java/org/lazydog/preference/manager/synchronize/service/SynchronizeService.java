package org.lazydog.preference.manager.synchronize.service;

import org.lazydog.preference.manager.service.ServiceException;


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

    public Object exportDocument()
            throws ServiceException;
    public Object exportDocument(String path)
            throws ServiceException;
    public void importDocument(Object document)
            throws ServiceException;
    public void importDocument(String path, Object document)
            throws ServiceException;
}
