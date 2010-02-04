package org.lazydog.preference.manager.managedbean;

import java.io.Serializable;
import org.lazydog.preference.manager.model.PreferencesNode;


/**
 * Preferences managed bean.
 * 
 * @author  Ron Rickard
 */
public class PreferencesMB implements Serializable {

    private PreferencesNode[] roots;

    /**
     * Get the name.
     *
     * @return  the name.
     */
    public synchronized PreferencesNode[] getRoots() {

        if (roots == null) {

            roots = new PreferencesNode[1];
            roots[0] = new PreferencesNode(PreferencesNode.ROOT_NODE_NAME);
        }

        return roots;
    }
}
