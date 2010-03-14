package org.lazydog.preference.manager.preference.group;

import org.lazydog.preference.manager.preference.group.PreferenceGroup;
import java.util.ServiceLoader;


/**
 * Preference group factory.
 *
 * @author  Ron Rickard
 */
public class PreferenceGroupFactory {

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
     * Create the preference group.
     *
     * @return  the preference group.
     */
    public static PreferenceGroup create() {
        return PreferenceGroupFactory.create(PreferenceGroup.class);
    }

    /**
     * Create the preference group.
     *
     * @param  id  the ID.
     *
     * @return  the preference group.
     */
    public static PreferenceGroup create(String id) {

        // Declare.
        PreferenceGroup preferenceGroup;

        // Create the preference group.
        preferenceGroup = PreferenceGroupFactory.create(PreferenceGroup.class);

        try {

            // Set the ID.
            preferenceGroup.setId(id);
        }
        catch(PreferenceGroupException e) {
            throw new IllegalArgumentException(
                    "Unable to create preference group " + id + ".", e);
        }

        return preferenceGroup;
    }
}
