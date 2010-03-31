/**
 * Copyright 2009, 2010 lazydog.org.
 *
 * This file is part of Preference Manager.
 *
 * Preference Manager is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Preference Manager is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Preference Manager.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.lazydog.preference.manager.internal.synchronize;

import org.lazydog.preference.manager.ServiceException;
import org.lazydog.preference.manager.spi.synchronize.AgentSynchronizeService;
import org.lazydog.preference.manager.spi.synchronize.SynchronizeService;
import org.lazydog.preference.manager.spi.synchronize.SynchronizeServiceFactory;


/**
 * Agent synchronize service.
 * 
 * @author  Ron Rickard
 */
public final class AgentSynchronizeServiceImpl implements AgentSynchronizeService {

    private static final SynchronizeService synchronizeService
            = SynchronizeServiceFactory.create();

    /**
     * Export the preferences to a document.
     *
     * @return  the document.
     *
     * @throws  ServiceException  if unable to export the preferences.
     */
    @Override
    public String exportDocument()
            throws ServiceException {
        return synchronizeService.exportDocument();
    }

    /**
     * Export the preferences to a document.
     *
     * @param  path  the path.
     *
     * @return  the document.
     *
     * @throws  ServiceException  if unable to export the preferences.
     */
    @Override
    public String exportDocument(String path)
            throws ServiceException {
        return synchronizeService.exportDocument(path);
    }

    /**
     * Import the preferences from a document.
     *
     * @param  document  the document.
     *
     * @throws  ServiceException  if unable to import the preferences.
     */
    @Override
    public void importDocument(String document)
            throws ServiceException {
        synchronizeService.importDocument(document);
    }

    /**
     * Import the preferences from a document.
     *
     * @param  path      the path.
     * @param  document  the document.
     *
     * @throws  GroupServiceException  if unable to import the preferences.
     */
    @Override
    public void importDocument(String path, String document)
            throws ServiceException {
        synchronizeService.importDocument(path, document);
    }
}