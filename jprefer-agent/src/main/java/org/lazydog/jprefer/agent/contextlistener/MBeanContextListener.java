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
package org.lazydog.jprefer.agent.contextlistener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.lazydog.jprefer.model.SetupType;
import org.lazydog.jprefer.manager.JPreferManager;
import org.lazydog.jprefer.manager.JPreferManagerFactory;
import org.lazydog.jprefer.utility.MBeanUtility;
import org.lazydog.jprefer.spi.synchronize.AgentSynchronizeService;
import org.lazydog.jprefer.spi.synchronize.AgentSynchronizeServiceFactory;


/**
 * MBean context listener.
 *
 * @author  Ron Rickard
 */
public class MBeanContextListener implements ServletContextListener {

    private static final JPreferManager preferenceManager
            = JPreferManagerFactory.create();

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
