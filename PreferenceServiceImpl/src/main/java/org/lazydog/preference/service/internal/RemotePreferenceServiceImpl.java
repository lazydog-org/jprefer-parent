package org.lazydog.preference.service.internal;

import java.util.HashMap;
import java.util.Hashtable;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import org.lazydog.preference.service.AgentPreferenceServiceMBean;
import org.lazydog.preference.service.RemotePreferenceService;
import org.lazydog.preference.service.PreferenceServiceException;


/**
 * Remote preference service.
 *
 * @author  Ron Rickard
 */
public class RemotePreferenceServiceImpl implements RemotePreferenceService {

    private Hashtable<String,String> environment;

    /**
     * Check if the preferences are equal to the specified preferences.
     *
     * @param  preferences  the preferences.
     *
     * @return  true if the preferences are equal, otherwise false.
     *
     * @throws  PreferenceServiceException  if unable to check if the
     *                                      preferences are equal.
     */
    @Override
    public boolean areEqual(String preferences)
           throws PreferenceServiceException {

        // Declare.
        boolean areEqual;

        // Initialize.
        areEqual = false;

        try {

            // Declare.
            String thesePreferences;

            // Get all the preferences.
            thesePreferences = this.getAll();

            // Check if the preferences are equal.
            if (thesePreferences.equals(preferences)) {

                areEqual = true;
            }
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to check if the preferences are equal.", e);
        }

        return areEqual;
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
                    throw new PreferenceServiceException(
                            "Unable to connect to the JMX service: "
                            + "jmxPort not supplied.");
                }

                // Check if the login exists.
                if (login == null) {
                    throw new PreferenceServiceException(
                            "Unable to connect to the JMX service: "
                            + "login not supplied.");
                }

                // Check if the password exists.
                if (password == null) {
                    throw new PreferenceServiceException(
                            "Unable to connect to the JMX service: "
                            + "password not supplied.");
                }

                // Check if the server name exists.
                if (serverName == null) {
                    throw new PreferenceServiceException(
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
                throw new PreferenceServiceException(
                        "Unable to connect to the JMX service: "
                        + "environment not supplied.");
            }
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to connect to the JMX service "
                    + serviceUrl + ".", e);
        }

        return connector;
    }

    /**
     * Create or replace the preferences at the path name with the
     * specified preferences.
     *
     * @param  pathName     the path name.
     * @param  preferences  the preferences.
     *
     * @throws  PreferenceServiceException  if unable to create or replace the
     *                                      preferences.
     */
    @Override
    public void createOrReplace(String pathName, String preferences)
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

            // Create or replace the preferences.
            preferenceService.createOrReplace(pathName, preferences);
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to create or replace the preferences at the path name "
                    + pathName + ".", e);
        }
        finally {

            // Close the JMX service.
            this.close(connector);
        }
    }

    /**
     * Get the preferences at the path name.
     *
     * @param  pathName  the path name.
     *
     * @return  the preferences.
     *
     * @throws  PreferenceServiceException  if unable to get the preferences.
     */
    @Override
    public String get(String pathName)
            throws PreferenceServiceException {

        // Declare.
        JMXConnector connector;
        String preferences;

        // Initialize.
        connector = null;
        preferences = null;

        try {

            // Declare.
            AgentPreferenceServiceMBean preferenceService;

            // Connect to the JMX service.
            connector = this.connect();

            // Get the agent preference service MBean.
            preferenceService = this.getAgentPreferenceServiceMBean(connector);

            // Get the preferences.
            preferences = preferenceService.get(pathName);
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to get the preferences at the path name "
                    + pathName + ".", e);
        }
        finally {

            // Close the JMX service.
            this.close(connector);
        }

        return preferences;
    }

    /**
     * Get the agent preference service MBean.
     *
     * @return  the agent preference service MBean.
     *
     * @throws  PreferenceServiceException  if unable to get the agent
     *                                      preference service MBean.
     */
    private AgentPreferenceServiceMBean getAgentPreferenceServiceMBean(
            JMXConnector connector)
            throws PreferenceServiceException {

        // Declare.
        AgentPreferenceServiceMBean preferenceService;

        // Initialize.
        preferenceService = null;

        try {

            // Declare.
            MBeanServerConnection connection;
            ObjectName name;

            // Establish a connection to the MBean server.
            connection = connector.getMBeanServerConnection();

            // Get the MBean object name.
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
     * Get all the preferences.
     *
     * @return  all the preferences.
     *
     * @throws  PreferenceServiceException  if unable to get all the 
     *                                      preferences.
     */
    @Override
    public String getAll()
            throws PreferenceServiceException {

        // Declare.
        JMXConnector connector;
        String preferences;

        // Initialize.
        connector = null;
        preferences = null;

        try {

            // Declare.
            AgentPreferenceServiceMBean preferenceService;

            // Connect to the JMX service.
            connector = this.connect();

            // Get the agent preference service MBean.
            preferenceService = this.getAgentPreferenceServiceMBean(connector);

            // Get all the preferences.
            preferences = preferenceService.getAll();
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to get all the preferences.", e);
        }
        finally {

            // Close the JMX service.
            this.close(connector);
        }

        return preferences;
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
     * Remove the preferences at the path name.
     *
     * @param  pathName  the path name.
     *
     * @throws  PreferenceServiceException  if unable to remove the preferences.
     */
    @Override
    public void remove(String pathName)
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

            // Remove the preferences.
            preferenceService.remove(pathName);
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to remove the preferences at the path name "
                    + pathName + ".", e);
        }
        finally {

            // Close the JMX service.
            this.close(connector);
        }
    }

    /**
     * Replace all the preferences with the specified preferences.
     *
     * @param  preferences  the preferences.
     *
     * @throws  PreferenceServiceException  if unable to replace all the 
     *                                      preferences.
     */
    @Override
    public void replaceAll(String preferences)
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

            // Replace all the preferences.
            preferenceService.replaceAll(preferences);
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to replace all the preferences.", e);
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
