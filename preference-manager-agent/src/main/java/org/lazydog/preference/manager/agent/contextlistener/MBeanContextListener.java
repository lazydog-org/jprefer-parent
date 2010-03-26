package org.lazydog.preference.manager.agent.contextlistener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.lazydog.preference.manager.model.SetupType;
import org.lazydog.preference.manager.PreferenceManager;
import org.lazydog.preference.manager.PreferenceManagerFactory;
import org.lazydog.preference.manager.utility.MBeanUtility;
import org.lazydog.preference.manager.spi.synchronize.AgentSynchronizeService;
import org.lazydog.preference.manager.spi.synchronize.AgentSynchronizeServiceFactory;


/**
 * MBean context listener.
 *
 * @author  Ron Rickard
 */
public class MBeanContextListener implements ServletContextListener {

    private static final PreferenceManager preferenceManager
            = PreferenceManagerFactory.create();

    /**
     * Destroy the servlet context.
     *
     * @param  event  the servlet context event.
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {

        try {

            // Check if this is an agent setup.
            if (preferenceManager.getSetupType() == SetupType.AGENT) {

                // Unregister the MBean.
                MBeanUtility.unregister(AgentSynchronizeService.OBJECT_NAME);
            }
        }
        catch(Exception e) {
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
            if (preferenceManager.getSetupType() == SetupType.AGENT) {

                // Register the MBean.
                MBeanUtility.register(AgentSynchronizeService.OBJECT_NAME,
                        AgentSynchronizeServiceFactory.create());
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
