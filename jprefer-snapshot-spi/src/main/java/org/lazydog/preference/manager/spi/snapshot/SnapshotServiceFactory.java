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
package org.lazydog.preference.manager.spi.snapshot;

import java.util.ServiceLoader;


/**
 * Snapshot service factory.
 * 
 * @author  Ron Rickard
 */
public final class SnapshotServiceFactory {

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
