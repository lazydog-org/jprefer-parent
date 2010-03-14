package org.lazydog.preference.manager.group.service.internal;

import java.util.HashMap;
import java.util.Hashtable;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import org.lazydog.preference.manager.group.service.AgentGroupServiceMBean;
import org.lazydog.preference.manager.group.service.GroupServiceException;
import org.lazydog.preference.manager.group.service.RemoteGroupService;


/**
 * Remote group service.
 *
 * @author  Ron Rickard
 */
public class RemoteGroupServiceImpl implements RemoteGroupService {

    private Hashtable<String,String> environment;

    /**
     * Close the JMX service.
     *
     * @param  connector  the JMX connector.
     */
    private void close(JMXConnector connector) {

        try {

            // Check to see if the JMX connector exists.
            if (connector != null) {

                // Close the connection to the JMX service.
                connector.close();
            }
        }
        catch(Exception e) {
            // Ignore.
        }
    }

    /**
     * Connect to the JMX service.
     *
     * @return  the JMX connector.
     *
     * @throws  GroupServiceException  if unable to connect to the JMX service.
     */
    private JMXConnector connect()
            throws GroupServiceException {

        // Declare.
        JMXConnector connector;
        JMXServiceURL serviceUrl;

        // Initialize.
        connector = null;
        serviceUrl = null;

        try {

            // Check if the environment exists.
            if (this.environment != null) {

                // Declare.
                HashMap serviceEnv;
                String jmxPort;
                String login;
                String password;
                String serverName;

                jmxPort = this.environment.get(JMX_PORT);
                login = this.environment.get(LOGIN);
                password = this.environment.get(PASSWORD);
                serverName = this.environment.get(SERVER_NAME);

                // Check if the JMX port exists.
                if (jmxPort == null) {
                    throw new GroupServiceException(
                            "Unable to connect to the JMX service: "
                            + "jmxPort not supplied.");
                }

                // Check if the login exists.
                if (login == null) {
                    throw new GroupServiceException(
                            "Unable to connect to the JMX service: "
                            + "login not supplied.");
                }

                // Check if the password exists.
                if (password == null) {
                    throw new GroupServiceException(
                            "Unable to connect to the JMX service: "
                            + "password not supplied.");
                }

                // Check if the server name exists.
                if (serverName == null) {
                    throw new GroupServiceException(
                            "Unable to connect to the JMX service: "
                            + "serverName not supplied.");
                }

                // Set the service environment.
                serviceEnv = new HashMap();
                serviceEnv.put(
                        "jmx.remote.credentials",
                        new String[]{login, password});

                // Set the service URL.
                serviceUrl = new JMXServiceURL(
                        "service:jmx:rmi:///jndi/rmi://"
                        + serverName + ":" + jmxPort + "/jmxrmi");

                // Connect to the JMX service.
                connector = JMXConnectorFactory.connect(
                        serviceUrl, serviceEnv);
            }
            else {
                throw new GroupServiceException(
                        "Unable to connect to the JMX service: "
                        + "environment not supplied.");
            }
        }
        catch(Exception e) {
            throw new GroupServiceException(
                    "Unable to connect to the JMX service "
                    + serviceUrl + ".", e);
        }

        return connector;
    }

    /**
     * Export the preference group.
     *
     * @param  id  the ID.
     *
     * @return  the preference group.
     *
     * @throws  GroupServiceException  if unable to export the preference group.
     */
    @Override
    public Object exportGroup(Object id)
            throws GroupServiceException {

        // Declare.
        JMXConnector connector;
        Object preferenceGroup;

        // Initialize.
        connector = null;
        preferenceGroup = null;

        try {

            // Declare.
            AgentGroupServiceMBean groupService;

            // Connect to the JMX service.
            connector = this.connect();

            // Get the agent group service MBean.
            groupService = this.getAgentGroupServiceMBean(connector);

            // Export the preference group.
            preferenceGroup = groupService.exportGroup(id);
        }
        catch(Exception e) {
            throw new GroupServiceException(
                    "Unable to export the preference group "
                    + (String)id + ".", e);
        }
        finally {

            // Close the JMX service.
            this.close(connector);
        }

        return preferenceGroup;
    }

    /**
     * Export the preference groups.
     *
     * @return  all the preferences.
     *
     * @throws  GroupServiceException  if unable to export the preference groups.
     */
    @Override
    public Object exportGroups()
            throws GroupServiceException {

        // Declare.
        JMXConnector connector;
        Object preferenceGroups;

        // Initialize.
        connector = null;
        preferenceGroups = null;

        try {

            // Declare.
            AgentGroupServiceMBean groupService;

            // Connect to the JMX service.
            connector = this.connect();

            // Get the agent group service MBean.
            groupService = this.getAgentGroupServiceMBean(connector);

            // Export the preference groups.
            preferenceGroups = groupService.exportGroups();
        }
        catch(Exception e) {
            throw new GroupServiceException(
                    "Unable to export the preference groups.", e);
        }
        finally {

            // Close the JMX service.
            this.close(connector);
        }

        return preferenceGroups;
    }

    /**
     * Get the agent group service MBean.
     *
     * @return  the agent group service MBean.
     *
     * @throws  GroupServiceException  if unable to get the agent
     *                                 group service MBean.
     */
    private AgentGroupServiceMBean getAgentGroupServiceMBean(
            JMXConnector connector)
            throws GroupServiceException {

        // Declare.
        AgentGroupServiceMBean groupService;

        // Initialize.
        groupService = null;

        try {

            // Declare.
            MBeanServerConnection connection;
            ObjectName name;

            // Establish a connection to the MBean server.
            connection = connector.getMBeanServerConnection();

            // Get the MBean object name.
            name = new ObjectName(AgentGroupServiceMBean.OBJECT_NAME);

            // Get the agent group service MBean.
            groupService = JMX.newMXBeanProxy(
                    connection, name, AgentGroupServiceMBean.class);
        }
        catch(Exception e) {
            throw new GroupServiceException(
                    "Unable to get the agent group service MBean.", e);
        }

        return groupService;
    }

    /**
     * Get the environment.
     *
     * @return  the environment.
     */
    @Override
    public Hashtable<String,String> getEnvironment() {
        return this.environment;
    }

    /**
     * Import the preference group.
     *
     * @param  id               the ID.
     * @param  preferenceGroup  the preference group.
     *
     * @throws  GroupServiceException  if unable to import the preference group.
     */
    @Override
    public void importGroup(Object id, Object preferenceGroup)
            throws GroupServiceException {

        // Declare.
        JMXConnector connector;

        // Initialize.
        connector = null;

        try {

            // Declare.
            AgentGroupServiceMBean groupService;

            // Connect to the JMX service.
            connector = this.connect();

            // Get the agent group service MBean.
            groupService = this.getAgentGroupServiceMBean(connector);

            // Import the preference group.
            groupService.importGroup(id, preferenceGroup);
        }
        catch(Exception e) {
            throw new GroupServiceException(
                    "Unable to import the preference group "
                    + (String)id + ".", e);
        }
        finally {

            // Close the JMX service.
            this.close(connector);
        }
    }

    /**
     * Import the preference groups.
     *
     * @param  id                the ID.
     * @param  preferenceGroups  the preference groups.
     *
     * @throws  GroupServiceException  if unable to import the preference groups.
     */
    @Override
    public void importGroups(Object preferenceGroups)
            throws GroupServiceException {

        // Declare.
        JMXConnector connector;

        // Initialize.
        connector = null;

        try {

            // Declare.
            AgentGroupServiceMBean groupService;

            // Connect to the JMX service.
            connector = this.connect();

            // Get the agent group service MBean.
            groupService = this.getAgentGroupServiceMBean(connector);

            // Import the preference groups.
            groupService.importGroups(preferenceGroups);
        }
        catch(Exception e) {
            throw new GroupServiceException(
                    "Unable to import the preference groups.", e);
        }
        finally {

            // Close the JMX service.
            this.close(connector);
        }
    }

    /**
     * Remove the preference group.
     *
     * @param  id  the ID.
     *
     * @throws  GroupServiceException  if unable to remove the preference group.
     */
    @Override
    public void removeGroup(Object id)
            throws GroupServiceException {

        // Declare.
        JMXConnector connector;

        // Initialize.
        connector = null;

        try {

            // Declare.
            AgentGroupServiceMBean groupService;

            // Connect to the JMX service.
            connector = this.connect();

            // Get the agent group service MBean.
            groupService = this.getAgentGroupServiceMBean(connector);

            // Remove the preference group.
            groupService.removeGroup(id);
        }
        catch(Exception e) {
            throw new GroupServiceException(
                    "Unable to remove the preference group "
                    + (String)id + ".", e);
        }
        finally {

            // Close the JMX service.
            this.close(connector);
        }
    }

    /**
     * Remove the preference groups.
     *
     * @throws  GroupServiceException  if unable to remove the preference groups.
     */
    @Override
    public void removeGroups()
            throws GroupServiceException {

        // Declare.
        JMXConnector connector;

        // Initialize.
        connector = null;

        try {

            // Declare.
            AgentGroupServiceMBean groupService;

            // Connect to the JMX service.
            connector = this.connect();

            // Get the agent group service MBean.
            groupService = this.getAgentGroupServiceMBean(connector);

            // Remove the preference groups.
            groupService.removeGroups();
        }
        catch(Exception e) {
            throw new GroupServiceException(
                    "Unable to remove the preference groups.", e);
        }
        finally {

            // Close the JMX service.
            this.close(connector);
        }
    }

    /**
     * Set the environment.
     *
     * @param  environment  the environment.
     */
    @Override
    public void setEnvironment(Hashtable<String,String> environment) {
        this.environment = environment;
    }
}
