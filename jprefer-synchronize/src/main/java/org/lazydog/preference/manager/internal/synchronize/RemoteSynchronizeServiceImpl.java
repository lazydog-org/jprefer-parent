/**
 * Copyright 2009, 2010 lazydog.org.
 *
 * This file is part of JPrefer.
 *
 * JPrefer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JPrefer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Preference Manager.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.lazydog.preference.manager.internal.synchronize;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import javax.management.JMX;
import javax.management.MalformedObjectNameException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import org.lazydog.jprefer.ServiceException;
import org.lazydog.preference.manager.spi.synchronize.AgentSynchronizeService;
import org.lazydog.preference.manager.spi.synchronize.RemoteSynchronizeService;


/**
 * Remote synchronize service.
 *
 * @author  Ron Rickard
 */
public final class RemoteSynchronizeServiceImpl implements RemoteSynchronizeService {

    private Hashtable<String,String> environment;

    /**
     * Close the JMX service.
     *
     * @param  connector  the JMX connector.
     */
    private static void close(JMXConnector connector) {

        try {

            // Check to see if the JMX connector exists.
            if (connector != null) {

                // Close the connection to the JMX service.
                connector.close();
            }
        }
        catch(IOException e) {
            // Ignore.
        }
    }

    /**
     * Connect to the JMX service.
     *
     * @param  environment  the environment.
     *
     * @return  the JMX connector.
     *
     * @throws  ServiceException          if unable to connect to the JMX
     *                                    service.
     * @throws  NullPointerException      if the environment is null.
     * @throws  IllegalArgumentException  if the environment does not contain
     *                                    the jmxPort, login, password, and
     *                                    serverName.
     */
    private static JMXConnector connect(Hashtable<String,String> environment)
            throws ServiceException {

        // Declare.
        JMXConnector connector;
        String jmxPort;
        String serverName;

        // Initialize.
        jmxPort = null;
        serverName = null;

        try {

            // Check if the environment exists.
            if (environment != null) {

                // Declare.
                HashMap serviceEnv;
                JMXServiceURL serviceUrl;
                String login;
                String password;

                // Get the environment properties.
                jmxPort = environment.get(JMX_PORT);
                login = environment.get(LOGIN);
                password = environment.get(PASSWORD);
                serverName = environment.get(SERVER_NAME);

                // Check if the JMX port exists.
                if (jmxPort == null) {
                    throw new IllegalArgumentException(
                            "The jmxPort is not supplied.");
                }

                // Check if the login exists.
                if (login == null) {
                    throw new IllegalArgumentException(
                            "The login is not supplied.");
                }

                // Check if the password exists.
                if (password == null) {
                    throw new IllegalArgumentException(
                            "The password is not supplied.");
                }

                // Check if the server name exists.
                if (serverName == null) {
                    throw new IllegalArgumentException(
                            "The serverName is not supplied.");
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
                throw new NullPointerException(
                        "The environment is null");
            }
        }
        catch(IOException e) {
            throw new ServiceException(
                    "Unable to connect to the JMX service on "
                    + serverName + "/"  + jmxPort + ".", e);
        }

        return connector;
    }

    /**
     * Export the preferences to a document.
     *
     * @return  the document.
     *
     * @throws  ServiceException  if unable to export the preferences.
     */
    @Override
    public String exportDocument()
            throws ServiceException {

        // Declare.
        JMXConnector connector;
        String document;

        // Initialize.
        connector = null;

        try {

            // Declare.
            AgentSynchronizeService synchronizeService;

            // Connect to the JMX service.
            connector = connect(this.environment);

            // Get the agent group service.
            synchronizeService = getAgentSynchronizeService(connector);

            // Export the preferences.
            document = synchronizeService.exportDocument();
        }
        finally {

            // Close the JMX service.
            close(connector);
        }

        return document;
    }

    /**
     * Export the preferences to a document.
     *
     * @param  path  the path.
     *
     * @return  the document.
     *
     * @throws  ServiceException  if unable to export the preferences.
     */
    @Override
    public String exportDocument(String path)
            throws ServiceException {

        // Declare.
        JMXConnector connector;
        String document;

        // Initialize.
        connector = null;

        try {

            // Declare.
            AgentSynchronizeService synchronizeService;

            // Connect to the JMX service.
            connector = connect(this.environment);

            // Get the agent group service.
            synchronizeService = getAgentSynchronizeService(connector);

            // Export the preferences.
            document = synchronizeService.exportDocument(path);
        }
        finally {

            // Close the JMX service.
            close(connector);
        }

        return document;
    }

    /**
     * Get the agent synchronize service.
     *
     * @return  the agent synchronize service.
     *
     * @throws  ServiceException  if unable to get the agent synchronize
     *                            service.
     */
    private static AgentSynchronizeService getAgentSynchronizeService(
            JMXConnector connector)
            throws ServiceException {

        // Declare.
        AgentSynchronizeService synchronizeService;

        try {

            // Declare.
            MBeanServerConnection connection;
            ObjectName name;

            // Establish a connection to the MBean server.
            connection = connector.getMBeanServerConnection();

            // Get the MBean object name.
            name = new ObjectName(AgentSynchronizeService.OBJECT_NAME);

            // Get the agent synchronize service MBean.
            synchronizeService = JMX.newMXBeanProxy(
                    connection, name, AgentSynchronizeService.class);
        }
        catch(IOException e) {
            throw new ServiceException(
                    "Unable to get the agent synchronize service MBean.", e);
        }
        catch(MalformedObjectNameException e) {
            throw new ServiceException(
                    "Unable to get the agent synchronize service MBean.", e);
        }

        return synchronizeService;
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
     * Import the preferences from a document.
     *
     * @param  document  the document.
     *
     * @throws  ServiceException  if unable to import the preferences.
     */
    @Override
    public void importDocument(String document)
            throws ServiceException {

        // Declare.
        JMXConnector connector;

        // Initialize.
        connector = null;

        try {

            // Declare.
            AgentSynchronizeService synchronizeService;

            // Connect to the JMX service.
            connector = connect(this.environment);

            // Get the agent synchronize service.
            synchronizeService = getAgentSynchronizeService(connector);

            // Import the preferences.
            synchronizeService.importDocument(document);
        }
        finally {

            // Close the JMX service.
            close(connector);
        }
    }

    /**
     * Import the preferences from a document.
     *
     * @param  path      the path.
     * @param  document  the document.
     *
     * @throws  ServiceException  if unable to import the preferences.
     */
    @Override
    public void importDocument(String path, String document)
            throws ServiceException {

        // Declare.
        JMXConnector connector;

        // Initialize.
        connector = null;

        try {

            // Declare.
            AgentSynchronizeService synchronizeService;

            // Connect to the JMX service.
            connector = connect(this.environment);

            // Get the agent synchronize service.
            synchronizeService = getAgentSynchronizeService(connector);

            // Import the preferences.
            synchronizeService.importDocument(path, document);
        }
        finally {

            // Close the JMX service.
            close(connector);
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
