package org.lazydog.preference.manager.preference.service.internal;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.lazydog.preference.manager.model.PreferencesTree;


/**
 * Preferences tree implementation.
 *
 * @author  Ron Rickard
 */
public class PreferencesTreeImpl implements PreferencesTree {

    private static final String ROOT_PATH = "/";
    private Preferences preferences;

    /**
     * Private constructor.
     *
     * @param  path  the path.
     *
     */
    public PreferencesTreeImpl(String path) {
        this.preferences = Preferences.systemRoot().node(path);
    }

    /**
     * Get the children.
     *
     * @return  the children.
     */
    @Override
    public List<PreferencesTree> getChildren() {

        // Declare.
        List<PreferencesTree> children;

        // Initialize.
        children = new ArrayList<PreferencesTree>();

        try {

            // Loop through the children.
            for (String childName : this.preferences.childrenNames()) {

                // Declare.
                PreferencesTreeImpl child;

                // Get the child.
                child = new PreferencesTreeImpl(this.preferences.node(childName).absolutePath());

                // Add the child to the children.
                children.add(child);
            }
        }
        catch(BackingStoreException e) {
            // Already handled.
        }

        return children;
    }

    /**
     * Get the path.
     *
     * @return  the absolute path.
     */
    @Override
    public String getPath() {
        return this.preferences.absolutePath();
    }

    /**
     * Get the preference keys.
     *
     * @return  the preference keys.
     */
    @Override
    public List<String> getPreferenceKeys() {

        // Declare.
        List<String> preferenceKeys;

        // Initialize.
        preferenceKeys = new ArrayList<String>();

        // Get the preference keys.
        preferenceKeys.addAll(this.getPreferences().keySet());

        return preferenceKeys;
    }

    /**
     * Get the preferences.
     *
     * @return  the preferences.
     */
    @Override
    public Map<String,String> getPreferences() {

        // Declare.
        Map<String,String> preferences;

        // Initialize.
        preferences = new LinkedHashMap<String,String>();

        try {

            // Loop through the preference keys.
            for (String key : this.preferences.keys()) {

                // Add the preference to the preferences.
                preferences.put(key, this.preferences.get(key, ""));
            }
        }
        catch(BackingStoreException e) {
            // Already handled.
        }

        return preferences;
    }

    /**
     * Is this the root group?
     *
     * @return  true if this is the root group, otherwise false.
     */
    private boolean isRootGroup() {
        return this.getPath().equals(ROOT_PATH);
    }

    /**
     * Get this object as a String.
     *
     * @return  this object as a String.
     */
    @Override
    public String toString() {
        return this.isRootGroup() ? ROOT_PATH : this.preferences.name();
    }
}
