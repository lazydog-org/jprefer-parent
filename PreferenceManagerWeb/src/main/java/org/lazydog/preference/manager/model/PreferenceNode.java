package org.lazydog.preference.manager.model;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;


/**
 * Preferences node.
 *
 * @author  Ron Rickard
 */
public class PreferenceNode {

    public static final String ROOT_NODE_NAME = "/";
    private static final String SLASH = "/";
    private Preferences preferences;

    /**
     * Constructor.
     *
     * @param  path  the path.
     */
    public PreferenceNode(String path) {

        // Set the preferences according to the path.
        this.preferences = Preferences.userRoot().node(path);
    }

    /**
     * Is this the root node?
     *
     * @return  true if this is the root node, otherwise false.
     */
    private boolean isRootNode() {
        return this.preferences.absolutePath().equals(ROOT_NODE_NAME);
    }

    /**
     * Get the children.
     *
     * @return  the children.
     */
    public List<PreferenceNode> getChildren() {

        // Declare.
        List<PreferenceNode> children;

        // Initialize.
        children = new ArrayList<PreferenceNode>();

        try {

            // Loop through the children names.
            for (String childName : this.preferences.childrenNames()) {

                // Declare.
                PreferenceNode child;

                // Check if this is the root node.
                if (this.isRootNode()) {

                    // Get the child for the child name.
                    child = new PreferenceNode(SLASH + childName);
                }
                else {

                    // Get the child for the child name.
                    child = new PreferenceNode(this.preferences.absolutePath() + SLASH + childName);
                }

                // Add the child to the children.
                children.add(child);
            }
        }
        catch(Exception e) {
            // Already handled.
        }

        return children;
    }

    /**
     * Get the preferences.
     *
     * @return  the preferences.
     */
    public List<Preference> getPreferences() {

        // Declare.
        List<Preference> preferences;

        // Initialize.
        preferences = new ArrayList<Preference>();

        try {

            // Loop through the preference keys.
            for (String key : this.preferences.keys()) {

                // Declare.
                String value;
                Preference preference;

                // Get the value for the key.
                value = this.preferences.get(key, "");

                // Get the preference.
                preference = new Preference();
                preference.setKey(key);
                preference.setValue(value);

                // Add the preference to the preferences.
                preferences.add(preference);
            }
        }
        catch(Exception e) {
            // Already handled.
        }

        return preferences;
    }

    /**
     * Get this object as a String.
     *
     * @return  this object as a String.
     */
    @Override
    public String toString() {
        return (this.isRootNode() ? ROOT_NODE_NAME : this.preferences.name());
    }
}
