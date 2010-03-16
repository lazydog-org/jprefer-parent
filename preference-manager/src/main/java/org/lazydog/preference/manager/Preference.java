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

    private static PreferenceService preferenceService
            = PreferenceServiceFactory.create();

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

            // Find the preference group tree.
            prefrenceGroupTree = preferenceService.findPreferenceGroupTree();
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }

        return prefrenceGroupTree;
    }

    /**
     * Get the preference group.
     *
     * @param  absolutePath  the absolute path.
     *
     * @return  the preference group.
     */
    public static PreferenceGroup getPreferenceGroup(String absolutePath) {

        // Declare.
        PreferenceGroup preferenceGroup;

        // Initialize.
        preferenceGroup = null;

        try {

            // Find the preference group.
            preferenceGroup = preferenceService.findPreferenceGroup(absolutePath);
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }

        return preferenceGroup;
    }

    /**
     * Remove the preference group.
     *
     * @param  absolutePath  the absolute path.
     */
    public static void removePreferenceGroup(String absolutePath) {

        try {

            // Remove the preference group.
            preferenceService.removePreferenceGroup(absolutePath);
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }
    }

    /**
     * Save the preference group.
     *
     * @param  preferenceGroup  the preference group.
     */
    public static void savePreferenceGroup(PreferenceGroup preferenceGroup) {

        try {

            // Save the preference group.
            preferenceService.persistPreferenceGroup(preferenceGroup);
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }
    }
}
