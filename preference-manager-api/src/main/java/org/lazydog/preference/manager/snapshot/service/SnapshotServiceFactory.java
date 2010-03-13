package org.lazydog.preference.manager.snapshot.service;

import java.util.ServiceLoader;


/**
 * Snapshot service factory.
 * 
 * @author  Ron Rickard
 */
public class SnapshotServiceFactory {

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
     * Create the snapshot service.
     *
     * @return  the snapshot service.
     */
    public static SnapshotService create() {
        return SnapshotServiceFactory.create(SnapshotService.class);
    }
}
