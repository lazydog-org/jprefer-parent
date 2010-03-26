package org.lazydog.preference.manager.server;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.glassfish.api.ActionReport;
import org.glassfish.api.admin.CommandRunner;
import org.glassfish.api.admin.ParameterMap;
import org.glassfish.api.embedded.ContainerBuilder;
import org.glassfish.api.embedded.admin.AdminInfo;
import org.glassfish.api.embedded.admin.EmbeddedAdminContainer;
import org.glassfish.api.embedded.EmbeddedFileSystem;
import org.glassfish.api.embedded.EmbeddedDeployer;
import org.glassfish.api.embedded.Server;


/**
 * Embedded server.
 *
 * @author  Ron Rickard
 */
public class EmbeddedServer {

    public void createUser(Server server, String user, String password, String group) {

        // Declare.
        String command;
        ParameterMap parameters;
        ActionReport report;
        CommandRunner runner;

        command = "create-file-user";
        parameters = new ParameterMap();
        parameters.add("username", user);
        parameters.add("userpassword", password);
        parameters.add("groups", group);
        runner = server.getHabitat().getComponent(CommandRunner.class);
        report = server.getHabitat().getComponent(ActionReport.class);
        runner.getCommandInvocation(command, report).parameters(parameters).execute();
    }

    public void start(Map<String,String> environment) throws Exception {

        // Declare.
        AdminInfo adminInfo;
        ContainerBuilder containerBuilder;
        EmbeddedAdminContainer adminContainer;
        EmbeddedDeployer deployer;
        //EmbeddedFileSystem fileSystem;
        //EmbeddedFileSystem.Builder fileSystemBuilder;
        //File installRoot;
        File ejbEAR;
        File guiEAR;
        Server server;
        Server.Builder serverBuilder;

        //installRoot = new File(environment.get("install.root"));

        ejbEAR = new File(environment.get("ejb.ear"));
        guiEAR = new File(environment.get("gui.ear"));

        //fileSystemBuilder = new EmbeddedFileSystem.Builder();
        //fileSystemBuilder.autoDelete(false);
        //fileSystemBuilder.installRoot(installRoot);
        //fileSystem = fileSystemBuilder.build();

        serverBuilder = new Server.Builder("server");
        //serverBuilder.embeddedFileSystem(fileSystem);
        server = serverBuilder.build();
        containerBuilder = server.createConfig(ContainerBuilder.Type.ejb);
        server.addContainer(containerBuilder);
        containerBuilder = server.createConfig(ContainerBuilder.Type.web);
        server.addContainer(containerBuilder);
        server.createPort(Integer.parseInt(environment.get("port")));
        server.start();
        this.createUser(server, "admin", "admin", "admin");
        this.createUser(server, "operator", "operator", "operator");
        this.createUser(server, "user", "user", "user");

        deployer = server.getDeployer();
        deployer.deploy(ejbEAR, null);
        deployer.deploy(guiEAR, null);
    }

    public static void main(String[] args) throws Exception {

        Map<String,String> environment;
        EmbeddedServer server;

        environment = new HashMap<String,String>();
        environment.put("install.root", "/var/tmp/preferencemanager/embeddedserver");
        environment.put("port", "8082");
        environment.put("ejb.ear", "/var/tmp/preference-manager-ear-1.0-SNAPSHOT.ear");
        environment.put("gui.ear", "/var/tmp/preference-manager-web-ear-1.0-SNAPSHOT.ear");

        System.setProperty("glassfish.embedded.tmpdir", "/var/tmp");
        server = new EmbeddedServer();
        server.start(environment);
    }
}
