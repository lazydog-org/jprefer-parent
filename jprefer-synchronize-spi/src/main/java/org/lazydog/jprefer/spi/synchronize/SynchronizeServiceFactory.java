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
 * along with JPrefer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.lazydog.jprefer.spi.synchronize;

import java.util.Hashtable;
import java.util.ServiceLoader;


/**
 * Synchronize service factory.
 * 
 * @author  Ron Rickard
 */
public final class SynchronizeServiceFactory {

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
