package org.lazydog.preference.service;

import java.util.HashMap;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import org.lazydog.preference.api.AgentPreferenceServiceMBean;
import org.lazydog.preference.api.PreferenceService;
import org.lazydog.preference.exception.PreferenceServiceException;
import org.lazydog.preference.model.Agent;


/**
 * Remote preference service.
 *
 * @author  Ron Rickard
 */
public class RemotePreferenceService
        implements PreferenceService {

    private HashMap serviceEnv;
    private JMXServiceURL serviceUrl;

    /**
     * Constructor.
     */
    private RemotePreferenceService() {
    }

    /**
     * Constructor.
     *
     * @param  agent  the agent.
     *
     * @throws  PreferenceServiceException  if unable to instantiate the service.
     */
    public RemotePreferenceService(Agent agent)
            throws PreferenceServiceException {

        try {

            // Set the service environment.
            this.serviceEnv = new HashMap();
            this.serviceEnv.put(
                    "jmx.remote.credentials",
                    new String[]{agent.getLogin(), agent.getPassword()});

            // Set the service URL.
            this.serviceUrl = new JMXServiceURL(
                    "service:jmx:rmi:///jndi/rmi://"
                    + agent.getServerName()
                    + ":"
                    + agent.getJmxPort()
                    + "/jmxrmi");
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to instantiate the service.", e);
        }
    }

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
     * @throws  PreferenceServiceException  if unable to connect to the
     *                                      JMX service.
     */
    private JMXConnector connect()
            throws PreferenceServiceException {

        // Declare.
        JMXConnector connector;

        // Initialize.
        connector = null;

        try {

            // Connect to the JMX service.
            connector = JMXConnectorFactory.connect(
                    this.serviceUrl, this.serviceEnv);
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to connect to the JMX service "
                    + this.serviceUrl + ".", e);
        }

        return connector;
    }

    /**
     * Create or replace the node specified by the path name with the
     * configuration in the XML string.
     *
     * @param  pathName   the path name.
     * @param  xmlString  the configuration as a XML string.
     *
     * @throws  PreferenceServiceException  if unable to create or replace the
     *                                      node.
     */
    @Override
    public void createOrReplaceNode(String pathName, String xmlString)
            throws PreferenceServiceException {

        // Declare.
        JMXConnector connector;

        // Initialize.
        connector = null;

        try {

            // Declare.
            AgentPreferenceServiceMBean preferenceService;

            // Connect to the JMX service.
            connector = this.connect();

            // Get the agent preference service MBean.
            preferenceService = this.getAgentPreferenceServiceMBean(connector);

            // Create or replace the node.
            preferenceService.createOrReplaceNode(pathName, xmlString);
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to create or replace the node for path name "
                    + pathName + ".", e);
        }
        finally {

            // Close the JMX service.
            this.close(connector);
        }
    }

    /**
     * Get the agent preference service MBean.
     *
     * @return  the agent preference service MBean.
     *
     * @throws  PreferenceServiceException  if unable to get the agent
     *                                      preference service MBean.
     */
    private AgentPreferenceServiceMBean getAgentPreferenceServiceMBean(JMXConnector connector)
            throws PreferenceServiceException {

        // Declare.
        AgentPreferenceServiceMBean preferenceService;

        // Initialize.
        preferenceService = null;

        try {

            // Declare.
            MBeanServerConnection connection;
            ObjectName name;

            // Establish a connection to the MBean server
            // and get the name of the MBean.
            connection = connector.getMBeanServerConnection();
            name = new ObjectName(AgentPreferenceServiceMBean.OBJECT_NAME);

            // Get the agent preference service MBean.
            preferenceService = JMX.newMXBeanProxy(
                    connection, name, AgentPreferenceServiceMBean.class);
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to get the agent preference service MBean.", e);
        }

        return preferenceService;
    }

    /**
     * Get all the nodes.
     *
     * @return  the configuration for all the nodes as a XML string.
     *
     * @throws  PreferenceServiceException  if unable to get all the nodes.
     */
    @Override
    public String getAllNodes()
            throws PreferenceServiceException {

        // Declare.
        JMXConnector connector;
        String xmlString;

        // Initialize.
        connector = null;
        xmlString = null;

        try {

            // Declare.
            AgentPreferenceServiceMBean preferenceService;

            // Connect to the JMX service.
            connector = this.connect();

            // Get the agent preference service MBean.
            preferenceService = this.getAgentPreferenceServiceMBean(connector);

            // Get all the nodes.
            xmlString = preferenceService.getAllNodes();
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to get all the nodes.", e);
        }
        finally {

            // Close the JMX service.
            this.close(connector);
        }

        return xmlString;
    }

    /**
     * Get the node specified by the path name.
     *
     * @param  pathName  the path name.
     *
     * @return  the configuration for the node as a XML string.
     *
     * @throws  PreferenceServiceException  if unable to get the node.
     */
    @Override
    public String getNode(String pathName)
            throws PreferenceServiceException {

        // Declare.
        JMXConnector connector;
        String xmlString;

        // Initialize.
        connector = null;
        xmlString = null;

        try {

            // Declare.
            AgentPreferenceServiceMBean preferenceService;

            // Connect to the JMX service.
            connector = this.connect();

            // Get the agent preference service MBean.
            preferenceService = this.getAgentPreferenceServiceMBean(connector);

            // Get the node.
            xmlString = preferenceService.getNode(pathName);
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to get the node for path name "
                    + pathName + ".", e);
        }
        finally {

            // Close the JMX service.
            this.close(connector);
        }

        return xmlString;
    }

    /**
     * Remove the node specified by the path name.
     *
     * @param  pathName  the path name.
     *
     * @throws  PreferenceServiceException  if unable to remove the node.
     */
    @Override
    public void removeNode(String pathName)
            throws PreferenceServiceException {

        // Declare.
        JMXConnector connector;

        // Initialize.
        connector = null;

        try {

            // Declare.
            AgentPreferenceServiceMBean preferenceService;

            // Connect to the JMX service.
            connector = this.connect();

            // Get the agent preference service MBean.
            preferenceService = this.getAgentPreferenceServiceMBean(connector);

            // Remove the node.
            preferenceService.removeNode(pathName);
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to remove the node for path name "
                    + pathName + ".", e);
        }
        finally {

            // Close the JMX service.
            this.close(connector);
        }
    }

    /**
     * Replace all the nodes with the configuration in the XML string.
     *
     * @param  xmlString  the configuration as a XML string.
     *
     * @throws  PreferenceServiceException  if unable to replace all the nodes.
     */
    @Override
    public void replaceAllNodes(String xmlString)
            throws PreferenceServiceException {

        // Declare.
        JMXConnector connector;

        // Initialize.
        connector = null;

        try {

            // Declare.
            AgentPreferenceServiceMBean preferenceService;

            // Connect to the JMX service.
            connector = this.connect();

            // Get the agent preference service MBean.
            preferenceService = this.getAgentPreferenceServiceMBean(connector);

            // Replace all the nodes.
            preferenceService.replaceAllNodes(xmlString);
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to replace all the nodes.", e);
        }
        finally {

            // Close the JMX service.
            this.close(connector);
        }
    }
}
