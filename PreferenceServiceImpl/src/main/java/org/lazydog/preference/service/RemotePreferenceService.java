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

    private JMXConnector connector;

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

            // Declare.
            HashMap serviceEnv;
            JMXServiceURL serviceUrl;
            String url;

            // Set the service environment and URL.
            serviceEnv = new HashMap();
            serviceEnv.put(
                    "jmx.remote.credentials",
                    new String[]{agent.getLogin(), agent.getPassword()});
            url = "service:jmx:rmi:///jndi/rmi://"
                    + agent.getServerName()
                    + ":"
                    + agent.getJmxPort()
                    + "/jmxrmi";
            serviceUrl = new JMXServiceURL(url);

            // Connect to the JMX service on the agent.
            this.connector = JMXConnectorFactory.connect(
                    serviceUrl, serviceEnv);
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to instantiate the service.", e);
        }
    }

    /**
     * Acquire the agent preference service MBean.
     *
     * @return  the agent preference service MBean.
     *
     * @throws  Exception  if unable to acquire the agent preference service
     *                     MBean.
     */
    private AgentPreferenceServiceMBean acquire()
            throws Exception {


        // Declare.
        MBeanServerConnection connection;
        ObjectName name;
        AgentPreferenceServiceMBean preferenceService;

        // Establish a connection to the MBean server
        // and get the preference service MBean.
        connection = this.connector.getMBeanServerConnection();
        name = new ObjectName(AgentPreferenceServiceMBean.OBJECT_NAME);
        preferenceService = JMX.newMXBeanProxy(
                connection, name, AgentPreferenceServiceMBean.class);

        return preferenceService;
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

        try {

            // Declare.
            AgentPreferenceServiceMBean preferenceService;

            // Acquire the MBean, create or replace the node, and
            // release the MBean.
            preferenceService = this.acquire();
            preferenceService.createOrReplaceNode(pathName, xmlString);
            this.release();
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to create or replace the node for path name "
                    + pathName + ".", e);
        }
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
        String xmlString;

        // Initialize.
        xmlString = null;

        try {

            // Declare.
            AgentPreferenceServiceMBean preferenceService;

            // Acquire the MBean, get all the nodes, and
            // release the MBean.
            preferenceService = this.acquire();
            xmlString = preferenceService.getAllNodes();
            this.release();
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to get all the nodes.", e);
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
        String xmlString;

        // Initialize.
        xmlString = null;

        try {

            // Declare.
            AgentPreferenceServiceMBean preferenceService;

            // Acquire the MBean, get the node, and
            // release the MBean.
            preferenceService = this.acquire();
            xmlString = preferenceService.getNode(pathName);
            this.release();
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to get the node for path name "
                    + pathName + ".", e);
        }

        return xmlString;
    }

    /**
     * Release the remote preference service MBean.
     *
     * @throws  Exception  if unable to release the remote preference service
     *                     MBean.
     */
    private void release()
            throws Exception {

        // Close the connection to the MBean server.
        this.connector.close();
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

        try {

            // Declare.
            AgentPreferenceServiceMBean preferenceService;

            // Acquire the MBean, remove the node, and
            // release the MBean.
            preferenceService = this.acquire();
            preferenceService.removeNode(pathName);
            this.release();
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to remove the node for path name "
                    + pathName + ".", e);
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

        try {

            // Declare.
            AgentPreferenceServiceMBean preferenceService;

            // Acquire the MBean, replace all the nodes, and
            // release the MBean.
            preferenceService = this.acquire();
            preferenceService.replaceAllNodes(xmlString);
            this.release();
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to replace all the nodes.", e);
        }
    }
}
