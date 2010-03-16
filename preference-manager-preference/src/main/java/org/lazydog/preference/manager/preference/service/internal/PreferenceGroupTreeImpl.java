package org.lazydog.preference.manager.preference.service.internal;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.lazydog.preference.manager.model.PreferenceGroupTree;


/**
 * Preference service implementation.
 *
 * @author  Ron Rickard
 */
public class PreferenceGroupTreeImpl implements PreferenceGroupTree {

    private static final String ROOT_PATH = "/";
    private Preferences preferences;

    /**
     * Private constructor.
     *
     * @param  path  the path.
     *
     */
    public PreferenceGroupTreeImpl(String path) {
        this.preferences = Preferences.systemRoot().node(path);
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
     * Get the children.
     *
     * @return  the children.
     */
    @Override
    public List<PreferenceGroupTree> getChildren() {

        // Declare.
        List<PreferenceGroupTree> children;

        // Initialize.
        children = new ArrayList<PreferenceGroupTree>();

        try {

            // Loop through the children.
            for (String childName : this.preferences.childrenNames()) {

                // Declare.
                PreferenceGroupTreeImpl child;

                // Get the child.
                child = new PreferenceGroupTreeImpl(this.preferences.node(childName).absolutePath());

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
