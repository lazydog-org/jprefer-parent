package org.lazydog.preference.manager.model;

import java.util.List;
import java.util.Map;


/**
 * Preference group tree.
 *
 * @author  Ron Rickard
 */
public interface PreferenceGroupTree {

    public List<PreferenceGroupTree> getChildren();
    public String getPath();
    public Map<String,String> getPreferences();
    @Override
    public String toString();
}
