package org.lazydog.preference.manager.model;

import java.util.List;
import java.util.Map;


/**
 * Preferences tree.
 *
 * @author  Ron Rickard
 */
public interface PreferencesTree {

    public List<PreferencesTree> getChildren();
    public String getPath();
    public List<String> getPreferenceKeys();
    public Map<String,String> getPreferences();
    @Override
    public String toString();
}
