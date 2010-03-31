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

import org.lazydog.jprefer.ServiceException;


/**
 * Synchronize service.
 *
 * @author  Ron Rickard
 */
public interface SynchronizeService {

    public String exportDocument()
            throws ServiceException;
    public String exportDocument(String path)
            throws ServiceException;
    public void importDocument(String document)
            throws ServiceException;
    public void importDocument(String path, String document)
            throws ServiceException;
}
