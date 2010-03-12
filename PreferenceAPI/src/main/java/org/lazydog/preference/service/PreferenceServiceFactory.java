package org.lazydog.preference.service;

import java.util.Hashtable;
import java.util.ServiceLoader;


/**
 * Preference service factory.
 * 
 * @author  Ron Rickard
 */
public class PreferenceServiceFactory {

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
     * Create the preference service.
     *
     * @return  the preference service.
     */
    public static PreferenceService create() {
        return PreferenceServiceFactory.create(LocalPreferenceService.class);
    }

    /**
     * Create the preference service.
     *
     * @param  environment  the environment.
     *
     * @return  the preference service.
     */
    public static PreferenceService create(Hashtable environment) {

        // Declare.
        RemotePreferenceService remotePreferenceService;
        
        // Create the remote preference service.
        remotePreferenceService = PreferenceServiceFactory.create(RemotePreferenceService.class);
        remotePreferenceService.setEnvironment(environment);

        return remotePreferenceService;
    }
}
