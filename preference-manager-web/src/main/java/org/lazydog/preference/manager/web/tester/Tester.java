package org.lazydog.preference.manager.web.tester;

import java.util.HashMap;
import java.util.prefs.Preferences;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import org.lazydog.preference.manager.synchronize.service.AgentSynchronizeServiceMBean;
import org.lazydog.preference.manager.synchronize.service.SynchronizeService;
import org.lazydog.preference.manager.synchronize.service.SynchronizeServiceFactory;


/**
 * Preference agent tester.
 *
 * @author  Ron Rickard
 */
public class Tester {

    public void run() throws Exception {

        SynchronizeService groupService = SynchronizeServiceFactory.create();
        String id = "/org/lazydog/test";
        Preferences prefs;
        prefs = Preferences.systemRoot().node(id);
        prefs.put("first", "Ronald");
        prefs.put("last", "Rickard");
        prefs.put("middle", "John");
        prefs.put("zipcode", "85254");

        Object document = groupService.exportDocument(id);

        System.out.println((String)document);

        //groupService.removeGroup("");

        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://SIC36565.sic.nwie.net:8686/jmxrmi");
        HashMap environment = new HashMap();
        String[] credentials = new String[]{"admin", "adminadmin"};
        environment.put("jmx.remote.credentials", credentials);
        JMXConnector connector = JMXConnectorFactory.connect(url, environment);

        MBeanServerConnection connection = connector.getMBeanServerConnection();

        ObjectName name = new ObjectName(AgentSynchronizeServiceMBean.OBJECT_NAME);
        AgentSynchronizeServiceMBean agentGroupService = JMX.newMXBeanProxy(connection, name, AgentSynchronizeServiceMBean.class);

        //agentGroupService.removeGroup(id);
        //agentGroupService.importDocument(id, document);

        connector.close();
    }

    public static void main(String[] args)
           throws Exception {
        Tester tester = new Tester();
        tester.run();
    }
}
