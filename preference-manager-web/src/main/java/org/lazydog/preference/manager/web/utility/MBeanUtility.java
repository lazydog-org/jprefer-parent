package org.lazydog.preference.manager.web.utility;

import java.lang.management.ManagementFactory;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MalformedObjectNameException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import org.lazydog.preference.manager.AgentSynchronizeService;
import org.lazydog.preference.manager.AgentSynchronizeServiceFactory;


/**
 * MBean utility.
 *
 * @author  Ron Rickard
 */
public final class MBeanUtility {

    /**
     * Unregister the MBean.
     * 
     * @throws  InstanceNotFoundException     if the MBean instance is not 
     *                                        found.
     * @throws  MalformedObjectNameException  if the MBean object name is 
     *                                        malformed.
     * @throws  MBeanRegistrationException    if the MBean cannot be 
     *                                        unregistered.
     */
    public static void unregister()
            throws InstanceNotFoundException, MalformedObjectNameException,
            MBeanRegistrationException {

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

    /**
     * Register the MBean.
     *
     * @throws  InstanceAlreadyExistsException  if the MBean instance already
     *                                          exists.
     * @throws  MalformedObjectNameException    if the MBean object name is
     *                                          malformed.
     * @throws  MBeanRegistrationException      if the MBean cannot be
     *                                          unregistered.
     * @throws  NotCompliantMBeanException      if the MBean is not compliant.
     */
    public static void register()
            throws InstanceAlreadyExistsException, MalformedObjectNameException,
            MBeanRegistrationException, NotCompliantMBeanException {

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
