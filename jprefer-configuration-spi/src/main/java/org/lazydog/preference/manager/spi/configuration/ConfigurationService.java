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
package org.lazydog.preference.manager.spi.configuration;

import java.util.List;
import org.lazydog.preference.manager.model.Agent;
import org.lazydog.preference.manager.model.SetupType;
import org.lazydog.preference.manager.ServiceException;


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
