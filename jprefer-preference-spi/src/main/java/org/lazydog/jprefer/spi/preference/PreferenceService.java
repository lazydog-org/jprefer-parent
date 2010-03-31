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
package org.lazydog.jprefer.spi.preference;

import java.util.Map;
import org.lazydog.jprefer.model.Preference;
import org.lazydog.jprefer.model.PreferencesTree;
import org.lazydog.jprefer.ServiceException;


/**
 * Preference service.
 *
 * @author  Ron Rickard
 */
public interface PreferenceService {

    public void copyPreferencePath(String sourcePath, String targetPath)
            throws ServiceException;
    public Map<String,String> findPreferences(String path)
            throws ServiceException;
    public PreferencesTree findPreferencesTree()
            throws ServiceException;
    public void movePreferencePath(String sourcePath, String targetPath)
            throws ServiceException;
    public Preference persistPreference(Preference preference)
            throws ServiceException;
    public String persistPreferencePath(String path)
            throws ServiceException;
    public void removePreference(String path, String key)
            throws ServiceException;
    public void removePreferencePath(String path)
            throws ServiceException;
}
