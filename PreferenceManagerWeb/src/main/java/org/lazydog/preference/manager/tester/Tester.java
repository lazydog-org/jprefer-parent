package org.lazydog.preference.manager.tester;

import java.util.HashMap;
import java.util.prefs.Preferences;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import org.lazydog.preference.service.AgentPreferenceServiceMBean;
import org.lazydog.preference.service.PreferenceService;
import org.lazydog.preference.service.PreferenceServiceFactory;


/**
 * Preference agent tester.
 *
 * @author  Ron Rickard
 */
public class Tester {

    public void run() throws Exception {

        PreferenceService preferenceService = PreferenceServiceFactory.create();
        String pathName = "/org/lazydog/test";
        Preferences prefs;
        prefs = Preferences.systemRoot().node(pathName);
        prefs.put("first", "Ronald");
        prefs.put("last", "Rickard");
        prefs.put("middle", "John");
        prefs.put("zipcode", "85254");

        String xmlString = preferenceService.get(pathName);

        System.out.println(xmlString);

        preferenceService.remove("");

        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://SIC36565.sic.nwie.net:8686/jmxrmi");
        HashMap environment = new HashMap();
        String[] credentials = new String[]{"admin", "adminadmin"};
        environment.put("jmx.remote.credentials", credentials);
        JMXConnector connector = JMXConnectorFactory.connect(url, environment);

        MBeanServerConnection connection = connector.getMBeanServerConnection();

        ObjectName name = new ObjectName(AgentPreferenceServiceMBean.OBJECT_NAME);
        AgentPreferenceServiceMBean preferenceAgent = JMX.newMXBeanProxy(connection, name, AgentPreferenceServiceMBean.class);

        //preferenceAgent.remove(pathName);
        preferenceAgent.createOrReplace(pathName, xmlString);

        connector.close();
    }

    public static void main(String[] args)
           throws Exception {
        Tester tester = new Tester();
        tester.run();
    }
}
