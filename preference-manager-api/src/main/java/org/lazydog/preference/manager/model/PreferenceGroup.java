package org.lazydog.preference.manager.model;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Preference group.
 *
 * @author  Ron Rickard
 */
public class PreferenceGroup {

    private String path;
    private Map<String,String> preferences = new LinkedHashMap<String,String>();
    
    /**
     * Get the path.
     * 
     * @return  the path.
     */
    public String getPath() {
        return this.path;
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

        // Check if the preferences exist.
        if (preferences == null) {

            // Create an empty map for preferences.
            preferences = new LinkedHashMap<String, String>();
        }

        // Set the preferences.
        this.preferences = preferences;
    }

    /**
     * Get this object as a String.
     *
     * @return  this object as a String.
     */
    @Override
    public String toString() {

        // Declare.
        StringBuffer toString;

        // Initialize.
        toString = new StringBuffer();

        toString.append("PreferenceGroup [");
        toString.append("path = ").append(this.getPath());
        toString.append(", preferences = ").append(this.getPreferences());
        toString.append("]");

        return toString.toString();
    }
}
