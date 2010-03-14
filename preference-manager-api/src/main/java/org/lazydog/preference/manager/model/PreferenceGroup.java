package org.lazydog.preference.manager.model;

import java.util.List;


/**
 * Preference group.
 *
 * @author  Ron Rickard
 */
public class PreferenceGroup {

    private String id;
    private List<Preference> preferences;
    
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
    public List<Preference> getPreferences() {
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
    public void setPreferences(List<Preference> preferences) {
        this.preferences = preferences;
    }
}
