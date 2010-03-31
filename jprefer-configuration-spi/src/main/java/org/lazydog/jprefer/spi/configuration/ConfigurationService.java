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
package org.lazydog.jprefer.spi.configuration;

import java.util.List;
import org.lazydog.jprefer.model.Agent;
import org.lazydog.jprefer.model.SetupType;
import org.lazydog.jprefer.ServiceException;


/**
 * Configuration service.
 *
 * @author Ron Rickard
 */
public interface ConfigurationService {

    public Agent findAgent(int id)
            throws ServiceException;
    public List<Agent> findAgents()
            throws ServiceException;
    public SetupType findSetupType()
            throws ServiceException;
    public Agent persistAgent(Agent agent)
            throws ServiceException;
    public SetupType persistSetupType(SetupType setupType)
            throws ServiceException;
    public void removeAgent(int id)
            throws ServiceException;
    public void removeSetupType()
            throws ServiceException;
}
