package org.lazydog.preference.manager.server;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;
import javax.management.remote.JMXServiceURL;
import org.eclipse.jetty.jmx.ConnectorServer;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;


/**
 * Embedded server.
 *
 * @author  Ron Rickard
 */
public class EmbeddedServer {

    /**
     * Deploy the web application on the server.
     *
     * @param  contextPath  the context path.
     * @param  warPath      the WAR path.
     * @param  server       the server.
     */
    private void deployWebApp(String contextPath, String warPath, Server server) {

        // Declare.
        WebAppContext webAppContext;

        // Create the web application context.
        webAppContext = new WebAppContext();
        webAppContext.setContextPath(contextPath);
        webAppContext.setWar(warPath);

        // Deploy the web application.
        server.setHandler(webAppContext);
    }

    /**
     * Add the JMX service.
     *
     * @param  server        the server.
     * @param  port          the port.
     * @param  passwordFile  the password file.
     *
     * @throws  Exception  if unable to add the JMX service.
     */
    private void addJMXService(Server server, int port, String passwordFile)
            throws Exception {

        // Declare.
        ConnectorServer connectorServer;
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
                        "service:jmx:rmi:///jndi/rmi://" + hostName + ":" + port + "/jmxrmi");

        // Create the RMI registry.
        LocateRegistry.createRegistry(port);

        // Initialize the Jetty MBean container.
        mBeanContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
        server.getContainer().addEventListener(mBeanContainer);
        server.addBean(mBeanContainer);

        // Start the JMX connector server.
        connectorServer = new ConnectorServer(jmxServiceUrl, env, "org.eclipse.jetty:name=rmiconnectorserver");
        connectorServer.doStart();
    }

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
     * Start the server.
     *
     * @param  environment  the environment.
     *
     * @throws  Exception  if unable to start the server.
     */
    public void start(Map<String,String> env) throws Exception {

        // Declare.
        Server server;

        // Initialize the server.
        server = new Server(Integer.parseInt(env.get("server.port")));

        // Deploy the web applications.
        deployWebApp(env.get("agent.context.path"), env.get("install.root") + env.get("agent.war.path"), server);
        deployWebApp(env.get("gui.context.path"), env.get("install.root") + env.get("gui.war.path"), server);

        // Add the JMX service.
        addJMXService(server, Integer.parseInt(env.get("jmx.port")), env.get("install.root") + env.get("jmx.password.file"));

        // Add login service.
        addLoginService(server, env.get("install.root") + env.get("user.realm.file"));

        // Star the server.
        server.start();
        server.join();
    }

    public static void main(String[] args) throws Exception {

        Map<String,String> environment;
        EmbeddedServer server;

        environment = new HashMap<String,String>();
        environment.put("install.root", "/var/tmp/preferencemanager/embeddedserver");
        environment.put("server.port", "8085");
        environment.put("jmx.port", "8686");
        environment.put("jmx.password.file", "/jmxpassword");
        environment.put("user.realm.file", "/userrealm");
        environment.put("agent.context.path", "/agent");
        environment.put("agent.war.path", "/webapps/preference-manager-agent-1.0-SNAPSHOT.war");
        environment.put("gui.context.path", "/preferencemanager");
        environment.put("gui.war.path", "/webapps/preference-manager-web-1.0-SNAPSHOT.war");

        server = new EmbeddedServer();
        server.start(environment);

        System.out.println("server started");
    }
}
