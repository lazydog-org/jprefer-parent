package org.lazydog.preference.manager.model;

import java.util.List;


/**
 * Preference group tree.
 *
 * @author  Ron Rickard
 */
public interface PreferenceGroupTree {

    public List<PreferenceGroupTree> getChildren();
    public String getId();
    public List<Preference> getPreferences();
    @Override
    public String toString();
}
