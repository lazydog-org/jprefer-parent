package org.lazydog.preference.manager.preference.service;

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
        return PreferenceServiceFactory.create(PreferenceService.class);
    }

    /**
     * Create the preference service.
     *
     * @param  id  the ID.
     *
     * @return  the preference service.
     */
    public static PreferenceService create(String id) {

        // Declare.
        PreferenceService preferenceService;

        // Create the preference service.
        preferenceService = PreferenceServiceFactory.create(PreferenceService.class);

        try {

            // Set the ID.
            preferenceService.setId(id);
        }
        catch(PreferenceServiceException e) {
            throw new IllegalArgumentException(
                    "Unable to create preference service " + id + ".", e);
        }

        return preferenceService;
    }
}
