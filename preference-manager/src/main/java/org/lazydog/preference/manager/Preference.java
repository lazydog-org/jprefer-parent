package org.lazydog.preference.manager;

import org.lazydog.preference.manager.model.PreferenceGroup;
import org.lazydog.preference.manager.model.PreferenceGroupTree;
import org.lazydog.preference.manager.preference.service.PreferenceService;
import org.lazydog.preference.manager.preference.service.PreferenceServiceFactory;


/**
 * Preference.
 *
 * @author  Ron Rickard
 */
public class Preference {

    /**
     * Get the preference group tree.
     *
     * @return  the preference group tree.
     */
    public static PreferenceGroupTree getPreferenceGroupTree() {

        // Declare.
        PreferenceGroupTree prefrenceGroupTree;

        // Initialize.
        prefrenceGroupTree = null;

        try {

            // Get the preference group tree.
            prefrenceGroupTree = PreferenceServiceFactory.create();
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }

        return prefrenceGroupTree;
    }

    /**
     * Get the preferences.
     *
     * @return  the preferences.
     */
    public static PreferenceGroup getPreferenceGroup(String id) {

        // Declare.
        PreferenceGroup preferenceGroup;

        // Initialize.
        preferenceGroup = null;

        try {

            // Declare.
            PreferenceService preferenceService;

            // Get the preference service.
            preferenceService = PreferenceServiceFactory.create(id);

            // Get the preference group.
            preferenceGroup = new PreferenceGroup();
            preferenceGroup.setId(id);
            preferenceGroup.setPreferences(preferenceService.getPreferences());
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }

        return preferenceGroup;
    }

    /**
     * Remove the preference group.
     *
     * @param  id  the ID.
     */
    public static void removePreferenceGroup(String id) {

        try {

            // Declare.
            PreferenceService preferenceService;

            // Get the preference service.
            preferenceService = PreferenceServiceFactory.create(id);

            // Remove the preference group.
            preferenceService.remove();
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }
    }
}
