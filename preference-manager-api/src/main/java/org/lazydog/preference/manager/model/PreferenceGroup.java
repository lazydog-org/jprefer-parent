package org.lazydog.preference.manager.model;

import java.util.Map;


/**
 * Preference group.
 *
 * @author  Ron Rickard
 */
public class PreferenceGroup {

    private String id;
    private Map<String,String> preferences;
    
    /**
     * Get the ID.
     * 
     * @return  the ID.
     */
    public String getId() {
        return this.id;
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
     * Set the ID.
     *
     * @param  id  the ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Set the preferences.
     *
     * @param  preferences  the preferences.
     */
    public void setPreferences(Map<String,String> preferences) {
        this.preferences = preferences;
    }
}
