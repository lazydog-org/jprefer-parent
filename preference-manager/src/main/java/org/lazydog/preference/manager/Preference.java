package org.lazydog.preference.manager;

import org.lazydog.preference.manager.model.PreferenceGroup;
import org.lazydog.preference.manager.model.PreferenceGroupTree;
import org.lazydog.preference.manager.preference.service.PreferenceService;
import org.lazydog.preference.manager.preference.service.PreferenceServiceFactory;
import org.lazydog.preference.manager.service.ServiceException;


/**
 * Preference.
 *
 * @author  Ron Rickard
 */
public class Preference {

    private static PreferenceService preferenceService
            = PreferenceServiceFactory.create();

    public static PreferenceGroupTree getPreferenceGroupTree() {
        return preferenceService.findPreferenceGroupTree();
    }

    public static PreferenceGroup getPreferenceGroup(String path)
            throws ServiceException {
        return preferenceService.findPreferenceGroup(path);
    }

    public static void removePreferenceGroup(String path)
            throws ServiceException {
        preferenceService.removePreferences(path);
    }

    public static void savePreferenceGroup(PreferenceGroup preferenceGroup) 
            throws ServiceException {
        preferenceService.persistPreferenceGroup(preferenceGroup);
    }
}
