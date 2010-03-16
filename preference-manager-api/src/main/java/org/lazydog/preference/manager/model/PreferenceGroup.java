package org.lazydog.preference.manager.model;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Preference group.
 *
 * @author  Ron Rickard
 */
public class PreferenceGroup {

    private String absolutePath;
    private Map<String,String> preferences = new LinkedHashMap<String,String>();
    
    /**
     * Get the absolute path.
     * 
     * @return  the absolute path.
     */
    public String getAbsolutePath() {
        return this.absolutePath;
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
     * Set the absolute path.
     *
     * @param  absolutePath  the absolute path.
     */
    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
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
        toString.append("absolutePath = ").append(this.getAbsolutePath());
        toString.append(", preferences = ").append(this.getPreferences());
        toString.append("]");

        return toString.toString();
    }
}
