package org.lazydog.preference.group.service;

import java.util.Hashtable;
import java.util.ServiceLoader;


/**
 * Group service factory.
 * 
 * @author  Ron Rickard
 */
public class GroupServiceFactory {

    /**
     * Create the service.
     *
     * @return  the service.
     */
    private static <T> T create(Class<T> serviceClass) {

        // Declare.
        T service;
        ServiceLoader<T> serviceLoader;

        // Initialize.
        service = null;
        serviceLoader = ServiceLoader.load(serviceClass);

        // Loop through the services.
        for (T loadedService : serviceLoader) {

            // Check if a service has not been found.
            if (service == null) {

                // Set the service.
                service = loadedService;
            }
            else {
                throw new IllegalArgumentException(
                    "More than one " + serviceClass.getSimpleName() + " service found.");
            }
        }

        // Check if a service has not been found.
        if (service == null) {
            throw new IllegalArgumentException(
                "No " + serviceClass.getSimpleName() + " service found.");
        }

        return service;
    }

    /**
     * Create the group service.
     *
     * @return  the group service.
     */
    public static GroupService create() {
        return GroupServiceFactory.create(LocalGroupService.class);
    }

    /**
     * Create the group service.
     *
     * @param  environment  the environment.
     *
     * @return  the group service.
     */
    public static GroupService create(Hashtable environment) {

        // Declare.
        RemoteGroupService remoteGroupService;
        
        // Create the remote group service.
        remoteGroupService = GroupServiceFactory.create(RemoteGroupService.class);
        remoteGroupService.setEnvironment(environment);

        return remoteGroupService;
    }
}
