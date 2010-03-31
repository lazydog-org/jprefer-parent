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
 * along with JPrefer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.lazydog.preference.manager.server;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.glassfish.api.ActionReport;
import org.glassfish.api.admin.CommandRunner;
import org.glassfish.api.admin.ParameterMap;
import org.glassfish.api.embedded.ContainerBuilder;
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
        File configurationFile;
        ContainerBuilder containerBuilder;
        EmbeddedDeployer deployer;
        EmbeddedFileSystem fileSystem;
        EmbeddedFileSystem.Builder fileSystemBuilder;
        File installRoot;
        File instanceRoot;
        File agentWAR;
        File guiWAR;
        Server server;
        Server.Builder serverBuilder;

        installRoot = new File(environment.get("install.root"));
        instanceRoot = new File(installRoot, "domains/domain1");
        configurationFile = new File(instanceRoot, "config/domain.xml");

        agentWAR = new File(environment.get("agent.war"));
        guiWAR = new File(environment.get("gui.war"));

        serverBuilder = new Server.Builder("test");
        fileSystemBuilder = new EmbeddedFileSystem.Builder();
        fileSystemBuilder.autoDelete(false);
        fileSystemBuilder.installRoot(installRoot, true);
        fileSystemBuilder.instanceRoot(instanceRoot);
        fileSystemBuilder.configurationFile(configurationFile, true);
        fileSystem = fileSystemBuilder.build();

        serverBuilder.embeddedFileSystem(fileSystem);
        server = serverBuilder.build();
        containerBuilder = server.createConfig(ContainerBuilder.Type.web);
        server.addContainer(containerBuilder);
        server.createPort(Integer.parseInt(environment.get("port")));
        server.start();
        this.createUser(server, "admin", "admin", "admin");
        this.createUser(server, "operator", "operator", "operator");
        this.createUser(server, "user", "user", "user");

        deployer = server.getDeployer();
        deployer.deploy(agentWAR, null);
        deployer.deploy(guiWAR, null);
    }

    public static void main(String[] args) throws Exception {

        Map<String,String> environment;
        EmbeddedServer server;

        environment = new HashMap<String,String>();
        environment.put("install.root", "C:\\Temp\\preferencemanager\\embeddedserver");
        environment.put("port", "8085");
        environment.put("agent.war", "C:\\Temp\\preference-manager-agent-1.0-SNAPSHOT.war");
        environment.put("gui.war", "C:\\Temp\\preference-manager-web-1.0-SNAPSHOT.war");

        System.setProperty("glassfish.embedded.tmpdir", "C:\\Temp");
        server = new EmbeddedServer();
        server.start(environment);
    }
}
