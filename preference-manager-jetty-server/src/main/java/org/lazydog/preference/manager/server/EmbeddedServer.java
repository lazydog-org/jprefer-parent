package org.lazydog.preference.manager.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.management.remote.JMXServiceURL;
import org.eclipse.jetty.jmx.ConnectorServer;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;


/**
 * Embedded server.
 *
 * @author  Ron Rickard
 */
public class EmbeddedServer {

    private static final String AGENT_CONTEXT_PATH_PROPERTY = "agent.context.path";
    private static final String AGENT_WAR_PATH_PROPERTY = "agent.war.path";
    private static final String CONFIG_PROPERTY = "config";
    private static final String GUI_CONTEXT_PATH_PROPERTY = "gui.context.path";
    private static final String GUI_WAR_PATH_PROPERTY = "gui.war.path";
    private static final String INSTALL_ROOT_PROPERTY = "install.root";
    private static final String JMX_PASSWORD_FILE_PROPERTY = "jmx.password.file";
    private static final String JMX_PORT_PROPERTY = "jmx.port";
    private static final String MONITOR_PORT_PROPERTY = "monitor.port";
    private static final String SERVER_PORT_PROPERTY = "server.port";
    private static final String USER_REALM_FILE_PROPERTY = "user.realm.file";

    private ConnectorServer connectorServer;
    private HandlerCollection handlerCollection = new HandlerCollection();
    private Server server;

    /**
     * Add the login service.
     *
     * @param  server     the server.
     * @param  realmFile  the realm file.
     */
    private void addLoginService(Server server, String realmFile) {

        // Declare.
        HashLoginService loginService;

        // Create the login service.
        loginService = new HashLoginService("userrealm");
        loginService.setConfig(realmFile);
        loginService.setRefreshInterval(0);
        server.addBean(loginService);
    }

    /**
     * Deploy the web application on the server.
     *
     * @param  server       the server.
     * @param  contextPath  the context path.
     * @param  warPath      the WAR path.
     */
    private void deployWebApp(Server server, String contextPath, String warPath) {

        // Declare.
        WebAppContext webAppContext;

        // Create the web application context.
        webAppContext = new WebAppContext();
        webAppContext.setContextPath(contextPath);
        webAppContext.setWar(warPath);

        // Deploy the web application.
        this.handlerCollection.addHandler(webAppContext);
        server.setHandler(this.handlerCollection);
    }

    /**
     * Get the environment.
     *
     * @param  configFilePath  the configuration file path.
     *
     * @return  the environment.
     */
    private static Properties getEnv(String configFilePath) {

        // Declare.
        Properties env;

        // Initialize.
        env = null;

        try {

            // Check if the configuration file path is not null.
            if (configFilePath != null) {

                // Declare.
                File configFile;
                String configFileCanonicalPath;
                String installRoot;

                // Initialize.
                env = new Properties();

                // Get the configuration file.
                configFile = new File(configFilePath);

                // Get the configuration file canonical path.
                configFileCanonicalPath = configFile.getCanonicalPath();

                // Parse the installation root.
                installRoot = configFileCanonicalPath.replaceAll("config/.+$", "");
                installRoot = configFileCanonicalPath.replaceAll("config\\\\.+$", "");

                // Load the environment properties.
                env.load(new FileInputStream(configFile));
                env.put(INSTALL_ROOT_PROPERTY, installRoot);
            }
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }

        return env;
    }

    /**
     * Start the server.
     *
     * @param  env  the environment.
     */
    public void start(Properties env) {

        try {

            // Declare.
            String agentContextPath;
            String agentWarPath;
            String guiContextPath;
            String guiWarPath;
            String installRoot;
            String jmxPasswordFile;
            int jmxPort;
            int serverPort;
            String userRealmFile;

            // Get the environment properties.
            agentContextPath = env.getProperty(AGENT_CONTEXT_PATH_PROPERTY);
            agentWarPath = env.getProperty(AGENT_WAR_PATH_PROPERTY);
            guiContextPath = env.getProperty(GUI_CONTEXT_PATH_PROPERTY);
            guiWarPath = env.getProperty(GUI_WAR_PATH_PROPERTY);
            installRoot = env.getProperty(INSTALL_ROOT_PROPERTY);
            jmxPasswordFile = env.getProperty(JMX_PASSWORD_FILE_PROPERTY);
            jmxPort = Integer.parseInt(env.getProperty(JMX_PORT_PROPERTY));
            serverPort = Integer.parseInt(env.getProperty(SERVER_PORT_PROPERTY));
            userRealmFile = env.getProperty(USER_REALM_FILE_PROPERTY);

            // Initialize the server.
            this.server = new Server(serverPort);

            // Deploy the web applications.
            deployWebApp(this.server, agentContextPath, installRoot + agentWarPath);
            deployWebApp(this.server, guiContextPath, installRoot + guiWarPath);

            // Start the JMX service.
            startJMXService(this.server, jmxPort, installRoot + jmxPasswordFile);

            // Add the login service.
            addLoginService(this.server, installRoot + userRealmFile);

            // Start the server.
            this.server.start();
            this.server.join();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Start the JMX service.
     *
     * @param  server        the server.
     * @param  port          the port.
     * @param  passwordFile  the password file.
     */
    private void startJMXService(Server server, int port, String passwordFile) {

        try {

            // Declare.
            Map<String,String> env;
            String hostName;
            MBeanContainer mBeanContainer;
            JMXServiceURL jmxServiceUrl;

            // Set the JMX connector server environment.
            env = new HashMap<String,String>();
            env.put("jmx.remote.x.password.file", passwordFile);

            // Get the host name.
            hostName = InetAddress.getLocalHost().getHostName();

            // Create the JMX service URL.
            jmxServiceUrl = new JMXServiceURL(
                            "service:jmx:rmi:///jndi/rmi://" + hostName + ":" +
                            port + "/jmxrmi");

            // Create the RMI registry.
            LocateRegistry.createRegistry(port);

            // Initialize the Jetty MBean container.
            mBeanContainer = new MBeanContainer(
                    ManagementFactory.getPlatformMBeanServer());
            server.getContainer().addEventListener(mBeanContainer);
            server.addBean(mBeanContainer);

            // Start the JMX connector server.
            this.connectorServer = new ConnectorServer(jmxServiceUrl, env,
                    "org.eclipse.jetty:name=rmiconnectorserver");
            this.connectorServer.doStart();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Stop the server.
     */
    public void stop() {

        try {

            // Stop the JMX service.
            this.connectorServer.doStop();

            // Stop the server.
            this.server.stop();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Run the embedded server.
     *
     * @param  args  command line arguments.
     *
     * @throws  Exception  if unable to run the embedded server.
     */
    public static void main(String[] args) throws Exception {

        // Declare.
        Properties env;
        int monitorPort;

        // Get the environment.
        env = getEnv(System.getProperty(CONFIG_PROPERTY));
        monitorPort = Integer.parseInt(env.getProperty(MONITOR_PORT_PROPERTY));

        // Check if the 'start' argument was specified.
        if (args[0].equals("start")) {

            // Declare.
            EmbeddedServer server;
            Thread monitor;

            // Initialize the embedded server.
            server = new EmbeddedServer();

            // Star the monitor.
            monitor = new MonitorThread(monitorPort, server);
            monitor.start();

            // Start the server.
            server.start(env);
        }

        // Check if the 'stop' argument was specified.
        else if (args[0].equals("stop")) {

            // Declare.
            OutputStream out;
            Socket socket;

            // Connect to the monitor port.
            socket = new Socket(InetAddress.getByName("127.0.0.1"), monitorPort);

            // Write data to the monitor port.
            out = socket.getOutputStream();
            out.write(("\r\n").getBytes());
            out.flush();

            // Close the connection.
            socket.close();
        }
    }

    /**
     * Monitor thread.
     */
    private static class MonitorThread extends Thread {

        private EmbeddedServer server;
        private ServerSocket listener;

        /**
         * Constructor.
         *
         * @param  port    the monitor port.
         * @param  server  the server.
         */
        public MonitorThread(int port, EmbeddedServer server) {

            // Create a daemon.
            this.setDaemon(true);

            try {

                // Create a listener on the localhost port.
                this.listener = new ServerSocket(
                        port, 1, InetAddress.getByName("127.0.0.1"));
                
                // Set the server.
                this.server = server;
            }
            catch(Exception e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Run the thread.
         */
        @Override
        public void run() {

            try {

                // Declare.
                Socket incoming;
                BufferedReader reader;

                // Accept a incoming connection on the listener.
                incoming = this.listener.accept();
                reader = new BufferedReader(
                        new InputStreamReader(incoming.getInputStream()));
                reader.readLine();

                // Stop the server.
                this.server.stop();

                // Close the incoming connection.
                incoming.close();

                // Close the listener.
                this.listener.close();
            }
            catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
