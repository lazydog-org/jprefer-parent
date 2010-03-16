package org.lazydog.preference.manager.synchronize.service.internal;

import java.util.HashMap;
import java.util.Hashtable;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import org.lazydog.preference.manager.synchronize.service.AgentSynchronizeServiceMBean;
import org.lazydog.preference.manager.synchronize.service.RemoteSynchronizeService;
import org.lazydog.preference.manager.synchronize.service.SynchronizeServiceException;


/**
 * Remote synchronize service.
 *
 * @author  Ron Rickard
 */
public class RemoteSynchronizeServiceImpl implements RemoteSynchronizeService {

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
            throws SynchronizeServiceException {

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
                    throw new SynchronizeServiceException(
                            "Unable to connect to the JMX service: "
                            + "jmxPort not supplied.");
                }

                // Check if the login exists.
                if (login == null) {
                    throw new SynchronizeServiceException(
                            "Unable to connect to the JMX service: "
                            + "login not supplied.");
                }

                // Check if the password exists.
                if (password == null) {
                    throw new SynchronizeServiceException(
                            "Unable to connect to the JMX service: "
                            + "password not supplied.");
                }

                // Check if the server name exists.
                if (serverName == null) {
                    throw new SynchronizeServiceException(
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
                throw new SynchronizeServiceException(
                        "Unable to connect to the JMX service: "
                        + "environment not supplied.");
            }
        }
        catch(Exception e) {
            throw new SynchronizeServiceException(
                    "Unable to connect to the JMX service "
                    + serviceUrl + ".", e);
        }

        return connector;
    }

    /**
     * Export the preference groups to a document.
     *
     * @return  the document.
     *
     * @throws  GroupServiceException  if unable to export the preference groups.
     */
    @Override
    public Object exportDocument()
            throws SynchronizeServiceException {

        // Declare.
        JMXConnector connector;
        Object document;

        // Initialize.
        connector = null;
        document = null;

        try {

            // Declare.
            AgentSynchronizeServiceMBean groupService;

            // Connect to the JMX service.
            connector = this.connect();

            // Get the agent group service MBean.
            groupService = this.getAgentGroupServiceMBean(connector);

            // Export the preference groups.
            document = groupService.exportDocument();
        }
        catch(Exception e) {
            throw new SynchronizeServiceException(
                    "Unable to export the preference groups.", e);
        }
        finally {

            // Close the JMX service.
            this.close(connector);
        }

        return document;
    }

    /**
     * Export the preference group to a document.
     *
     * @param  absolutePath  the absolute path.
     *
     * @return  the document.
     *
     * @throws  GroupServiceException  if unable to export the preference group.
     */
    @Override
    public Object exportDocument(String absolutePath)
            throws SynchronizeServiceException {

        // Declare.
        JMXConnector connector;
        Object document;

        // Initialize.
        connector = null;
        document = null;

        try {

            // Declare.
            AgentSynchronizeServiceMBean groupService;

            // Connect to the JMX service.
            connector = this.connect();

            // Get the agent group service MBean.
            groupService = this.getAgentGroupServiceMBean(connector);

            // Export the preference group.
            document = groupService.exportDocument(absolutePath);
        }
        catch(Exception e) {
            throw new SynchronizeServiceException(
                    "Unable to export the preference group " 
                    + absolutePath + ".", e);
        }
        finally {

            // Close the JMX service.
            this.close(connector);
        }

        return document;
    }

    /**
     * Get the agent group service MBean.
     *
     * @return  the agent group service MBean.
     *
     * @throws  GroupServiceException  if unable to get the agent
     *                                 group service MBean.
     */
    private AgentSynchronizeServiceMBean getAgentGroupServiceMBean(
            JMXConnector connector)
            throws SynchronizeServiceException {

        // Declare.
        AgentSynchronizeServiceMBean groupService;

        // Initialize.
        groupService = null;

        try {

            // Declare.
            MBeanServerConnection connection;
            ObjectName name;

            // Establish a connection to the MBean server.
            connection = connector.getMBeanServerConnection();

            // Get the MBean object name.
            name = new ObjectName(AgentSynchronizeServiceMBean.OBJECT_NAME);

            // Get the agent group service MBean.
            groupService = JMX.newMXBeanProxy(
                    connection, name, AgentSynchronizeServiceMBean.class);
        }
        catch(Exception e) {
            throw new SynchronizeServiceException(
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
     * Import the preference groups from a document.
     *
     * @param  document  the document.
     *
     * @throws  GroupServiceException  if unable to import the preference groups.
     */
    @Override
    public void importDocument(Object document)
            throws SynchronizeServiceException {

        // Declare.
        JMXConnector connector;

        // Initialize.
        connector = null;

        try {

            // Declare.
            AgentSynchronizeServiceMBean groupService;

            // Connect to the JMX service.
            connector = this.connect();

            // Get the agent group service MBean.
            groupService = this.getAgentGroupServiceMBean(connector);

            // Import the preference groups.
            groupService.importDocument(document);
        }
        catch(Exception e) {
            throw new SynchronizeServiceException(
                    "Unable to import the preference groups.", e);
        }
        finally {

            // Close the JMX service.
            this.close(connector);
        }
    }

    /**
     * Import the preference group from a document.
     *
     * @param  absolutePath  the absolutePath.
     * @param  document      the document.
     *
     * @throws  GroupServiceException  if unable to import the preference group.
     */
    @Override
    public void importDocument(String absolutePath, Object document)
            throws SynchronizeServiceException {

        // Declare.
        JMXConnector connector;

        // Initialize.
        connector = null;

        try {

            // Declare.
            AgentSynchronizeServiceMBean groupService;

            // Connect to the JMX service.
            connector = this.connect();

            // Get the agent group service MBean.
            groupService = this.getAgentGroupServiceMBean(connector);

            // Import the preference group.
            groupService.importDocument(absolutePath, document);
        }
        catch(Exception e) {
            throw new SynchronizeServiceException(
                    "Unable to import the preference group " 
                    + absolutePath + ".", e);
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
