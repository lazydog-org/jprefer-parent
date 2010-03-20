package org.lazydog.preference.manager.web.contextlistener;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.lazydog.preference.manager.AgentSynchronizeService;
import org.lazydog.preference.manager.AgentSynchronizeServiceFactory;
import org.lazydog.preference.manager.Configuration;


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
    @Override
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
                name = new ObjectName(AgentSynchronizeService.OBJECT_NAME);

                // Check if the MBean is registered with the MBean server.
                if (mBeanServer.isRegistered(name)) {

                    // Unregister the MBean with the MBean server.
                    mBeanServer.unregisterMBean(name);
                }
            }
        }
        catch(Exception e) {
            // TODO: handle exception.
e.printStackTrace();
        }
    }

    /**
     * Initialize the servlet context.
     *
     * @param  event  the servlet context event.
     */
    @Override
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
                name = new ObjectName(AgentSynchronizeService.OBJECT_NAME);

                // Check if the MBean is not registered with the MBean server.
                if (!mBeanServer.isRegistered(name)) {

                    // Register the MBean with the MBean server.
                    mBeanServer.registerMBean(
                            AgentSynchronizeServiceFactory.create(), name);
                }
            }
        }
        catch(Exception e) {
            // TODO: handle exception.
e.printStackTrace();
        }
    }
}
