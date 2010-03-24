package org.lazydog.preference.manager.utility;

import java.lang.management.ManagementFactory;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MalformedObjectNameException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;


/**
 * MBean utility.
 *
 * @author  Ron Rickard
 */
public final class MBeanUtility {

    /**
     * Unregister the MBean.
     *
     * @param  name  the MBean name.
     *
     * @throws  MBeanUtilityException  if unable to unregister the MBean.
     */
    public static void unregister(String name)
            throws MBeanUtilityException {

        try {

            // Declare.
            MBeanServer mBeanServer;
            ObjectName objectName;

            // Get the MBean object name.
            objectName = new ObjectName(name);

            // Get the MBean server.
            mBeanServer = ManagementFactory.getPlatformMBeanServer();

            // Check if the MBean is registered with the MBean server.
            if (mBeanServer.isRegistered(objectName)) {

                // Unregister the MBean with the MBean server.
                mBeanServer.unregisterMBean(objectName);
            }
        }
        catch(InstanceNotFoundException e) {
            throw new MBeanUtilityException(
                    "Unable to unregister the MBean, " + name + ".", e);
        }
        catch(MalformedObjectNameException e) {
            throw new MBeanUtilityException(
                    "Unable to unregister the MBean, " + name + ".", e);
        }
        catch(MBeanRegistrationException e) {
            throw new MBeanUtilityException(
                    "Unable to unregister the MBean, " + name + ".", e);
        }
    }

    /**
     * Register the MBean.
     *
     * @param  name    the MBean name.
     * @param  object  the MBean object.
     *
     * @throws  MBeanUtilityException  if unable to register the MBean.
     */
    public static void register(String name, Object object)
            throws MBeanUtilityException {

        try {

            // Declare.
            MBeanServer mBeanServer;
            ObjectName objectName;

            // Get the MBean object name.
            objectName = new ObjectName(name);

            // Get the MBean server.
            mBeanServer = ManagementFactory.getPlatformMBeanServer();

            // Check if the MBean is not registered with the MBean server.
            if (!mBeanServer.isRegistered(objectName)) {

                // Register the MBean with the MBean server.
                mBeanServer.registerMBean(object, objectName);
            }
        }
        catch(InstanceAlreadyExistsException e) {
            throw new MBeanUtilityException(
                    "Unable to register the MBean, " + name + ".", e);
        }
        catch(MalformedObjectNameException e) {
            throw new MBeanUtilityException(
                    "Unable to register the MBean, " + name + ".", e);
        }
        catch(MBeanRegistrationException e) {
            throw new MBeanUtilityException(
                    "Unable to register the MBean, " + name + ".", e);
        }
        catch(NotCompliantMBeanException e) {
            throw new MBeanUtilityException(
                    "Unable to register the MBean, " + name + ".", e);
        }
    }
}