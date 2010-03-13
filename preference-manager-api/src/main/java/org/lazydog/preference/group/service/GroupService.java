package org.lazydog.preference.group.service;


/**
 * Group service.
 *
 * @author  Ron Rickard
 */
public interface GroupService {

    public static final String JMX_PORT = "jmxPort";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String SERVER_NAME = "serverName";

    public Object exportGroup(Object id)
            throws GroupServiceException;
    public Object exportGroups()
            throws GroupServiceException;
    public void importGroup(Object id, Object preferenceGroup)
            throws GroupServiceException;
    public void importGroups(Object preferenceGroups)
            throws GroupServiceException;
    public void removeGroup(Object id)
            throws GroupServiceException;
    public void removeGroups()
            throws GroupServiceException;
}
