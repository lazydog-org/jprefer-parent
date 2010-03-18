package org.lazydog.preference.manager.synchronize.service;

import java.util.Hashtable;
import java.util.ServiceLoader;


/**
 * Synchronize service factory.
 * 
 * @author  Ron Rickard
 */
public class SynchronizeServiceFactory {

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
     * Create the synchronize service.
     *
     * @return  the synchronize service.
     */
    public static SynchronizeService create() {
        return SynchronizeServiceFactory.create(SynchronizeService.class);
    }

    /**
     * Create the synchronize service.
     *
     * @param  environment  the environment.
     *
     * @return  the synchronize service.
     */
    public static SynchronizeService create(Hashtable environment) {

        // Declare.
        RemoteSynchronizeService remoteSynchronizeService;
        
        // Create the remote synchronize service.
        remoteSynchronizeService = SynchronizeServiceFactory.create(RemoteSynchronizeService.class);
        remoteSynchronizeService.setEnvironment(environment);

        return remoteSynchronizeService;
    }
}
