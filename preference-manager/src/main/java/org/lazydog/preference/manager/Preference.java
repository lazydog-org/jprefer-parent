package org.lazydog.preference.manager;

import org.lazydog.preference.manager.model.Preferences;
import org.lazydog.preference.manager.preference.group.PreferenceGroup;
import org.lazydog.preference.manager.preference.group.PreferenceGroupFactory;


/**
 * Preference.
 *
 * @author  Ron Rickard
 */
public class Preference {

    /**
     * Get the preference group.
     *
     * @return  the preference group.
     */
    public static PreferenceGroup getPreferenceGroup() {

        // Declare.
        PreferenceGroup preferenceGroup;

        // Initialize.
        preferenceGroup = null;

        try {

            // Get the preference group.
            preferenceGroup = PreferenceGroupFactory.create();
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }

        return preferenceGroup;
    }

    /**
     * Get the preferences.
     *
     * @return  the preferences.
     */
    public static Preferences getPreferences(String id) {

        // Declare.
        Preferences preferences;

        // Initialize.
        preferences = null;

        try {

            // Declare.
            PreferenceGroup preferenceGroup;

            // Get the preference group.
            preferenceGroup = PreferenceGroupFactory.create(id);

            // Get the preferences.
            preferences = new Preferences();
            preferences.setId(preferenceGroup.getId());
            preferences.setPreferences(preferenceGroup.getPreferences());
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }

        return preferences;
    }
}
