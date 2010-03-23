package org.lazydog.preference.manager;

import java.util.ServiceLoader;


/**
 * Agent synchronize service factory.
 *
 * @author  Ron Rickard
 */
public final class AgentSynchronizeServiceFactory {

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
     * Create the agent synchronize service.
     *
     * @return  the agent synchronize service.
     */
    public static AgentSynchronizeService create() {
        return AgentSynchronizeServiceFactory.create(AgentSynchronizeService.class);
    }
}
