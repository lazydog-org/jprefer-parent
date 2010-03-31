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
package org.lazydog.preference.manager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Preferences tree.
 *
 * @author  Ron Rickard
 */
public class PreferencesTree implements Serializable {

    private List<PreferencesTree> children = new ArrayList<PreferencesTree>();
    private String path;
    private Map<String,String> preferences = new LinkedHashMap<String,String>();

    /**
     * Get the children.
     *
     * @return  the children.
     */
    public List<PreferencesTree> getChildren() {
        return this.children;
    }

    /**
     * Get the path.
     *
     * @return  the path.
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Get the preference keys.
     *
     * @return  the preference keys.
     */
    public List<String> getPreferenceKeys() {
        return new ArrayList(this.preferences.keySet());
    }

    /**
     * Get the preferences.
     *
     * @return  the preferences.
     */
    public Map<String,String> getPreferences() {
        return this.preferences;
    }

    /**
     * Set the children.
     *
     * @param  children  the children.
     */
    public void setChildren(List<PreferencesTree> children) {

        // Check if there are no children.
        if (children == null) {

            // Create an empty children list.
            this.children = new ArrayList<PreferencesTree>();
        }
        else {

            // Set the children.
            this.children = children;
        }
    }

    /**
     * Set the path.
     *
     * @param  path  the path.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Set the preferences.
     *
     * @param  preferences  the preferences.
     */
    public void setPreferences(Map<String,String> preferences) {

        // Check if there are no preferences.
        if (preferences == null) {

            // Create an empty preferences map.
            this.preferences = new LinkedHashMap<String,String>();
        }
        else {

            // Set the preferences.
            this.preferences = preferences;
        }
    }

    /**
     * Get this object as a String.
     *
     * @return this object as a String.
     */
    @Override
    public String toString() {
        return this.path.substring(this.path.lastIndexOf("/") + 1);
    }
}
