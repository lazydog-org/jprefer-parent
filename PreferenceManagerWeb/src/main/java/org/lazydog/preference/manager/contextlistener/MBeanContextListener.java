package org.lazydog.preference.manager.contextlistener;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.lazydog.preference.service.AgentPreferenceService;
import org.lazydog.preference.manager.configuration.Configuration;


/**
 * MBean context listener.
 *
 * @author  Ron Rickard
 */
public class MBeanContextListener implements ServletContextListener {

    /**
     * Destroy the servlet context.
     *
     * @param  event  the servlet context event.
     */
    public void contextDestroyed(ServletContextEvent event) {

        try {

            // Check if this is an agent setup.
            if (Configuration.isAgentSetup()) {
                
                // Declare.
                MBeanServer mBeanServer;
                ObjectName name;

                // Get the MBean server.
                mBeanServer = ManagementFactory.getPlatformMBeanServer();

                // Get the MBean object name.
                name = new ObjectName(AgentPreferenceService.OBJECT_NAME);

                // Check if the MBean is registered with the MBean server.
                if (mBeanServer.isRegistered(name)) {

                    // Unregister the MBean with the MBean server.
                    mBeanServer.unregisterMBean(name);
                }
            }
        }
        catch(Exception e) {
            // Ignore.
        }
    }

    /**
     * Initialize the servlet context.
     *
     * @param  event  the servlet context event.
     */
    public void contextInitialized(ServletContextEvent event) {

        try {

            // Check if this is an agent setup.
            if (Configuration.isAgentSetup()) {

                // Declare.
                MBeanServer mBeanServer;
                ObjectName name;

                // Get the MBean server.
                mBeanServer = ManagementFactory.getPlatformMBeanServer();

                // Get the MBean object name.
                name = new ObjectName(AgentPreferenceService.OBJECT_NAME);

                // Check if the MBean is not registered with the MBean server.
                if (!mBeanServer.isRegistered(name)) {

                    // Register the MBean with the MBean server.
                    mBeanServer.registerMBean(new AgentPreferenceService(), name);
                }
            }
        }
        catch(Exception e) {
            // Ignore.
        }
    }
}
