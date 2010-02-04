package org.lazydog.preference.manager.model;

import java.util.prefs.Preferences;


/**
 * Preferences node.
 *
 * @author  Ron Rickard
 */
public class PreferencesNode {

    public static final String ROOT_NODE_NAME = "/";
    private static final String SLASH = "/";
    private static PreferencesNode[] CHILDREN_ABSENT = new PreferencesNode[0];
    private PreferencesNode[] children;
    private Preferences preferences;


    public PreferencesNode(String path) {

        this.preferences = Preferences.userRoot().node(path);
System.out.println("preference node = " + this.preferences.toString());
    }

    private boolean isRootNode() {
        return this.preferences.absolutePath().equals(ROOT_NODE_NAME);
    }

    public synchronized PreferencesNode[] getNodes() {

        if (children == null) {

            String[] childrenNames;

            childrenNames = new String[0];

            try {

                childrenNames = this.preferences.childrenNames();
            }
            catch(Exception e) {
                // Already handled.
            }

            if (childrenNames.length != 0) {

                this.children = new PreferencesNode[childrenNames.length];

                for (int x = 0; x < childrenNames.length; x++) {

                    if (this.isRootNode()) {
System.out.println("child name = " + childrenNames[x]);
                        this.children[x] = new PreferencesNode(SLASH + childrenNames[x]);
                    }
                    else {
System.out.println("child name = " + this.preferences.absolutePath() + SLASH + childrenNames[x]);
                        this.children[x] = new PreferencesNode(this.preferences.absolutePath() + SLASH + childrenNames[x]);
                    }
                }
            }
            else {
                
                this.children = CHILDREN_ABSENT;
            }
        }

        return children;
    }

    @Override
    public String toString() {
        return (this.isRootNode() ? ROOT_NODE_NAME : this.preferences.name());
    }
}
