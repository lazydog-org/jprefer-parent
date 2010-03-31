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

import java.util.Date;
import java.util.Map;
import org.lazydog.preference.manager.ServiceException;


/**
 * Snapshot service.
 *
 * @author  Ron Rickard
 */
public interface SnapshotService {
 
    public void createSnapshot(String name)
            throws ServiceException;
    public Map<String,Date> findSnapshots()
            throws ServiceException;
    public void removeSnapshot(String name)
            throws ServiceException;
    public void renameSnapshot(String sourceName, String targetName)
            throws ServiceException;
    public void restoreSnapshot(String name)
            throws ServiceException;
}
