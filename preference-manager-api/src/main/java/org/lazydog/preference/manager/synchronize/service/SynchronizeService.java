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

    public String exportDocument()
            throws ServiceException;
    public String exportDocument(String path)
            throws ServiceException;
    public void importDocument(String document)
            throws ServiceException;
    public void importDocument(String path, String document)
            throws ServiceException;
}
