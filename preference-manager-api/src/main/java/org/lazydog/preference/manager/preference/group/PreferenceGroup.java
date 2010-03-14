package org.lazydog.preference.manager.preference.group;

import java.util.List;
import org.lazydog.preference.manager.model.Preference;


/**
 * Preference group.
 *
 * @author  Ron Rickard
 */
public interface PreferenceGroup {

    public Object exportDocument()
            throws PreferenceGroupException;
    public List<PreferenceGroup> getChildren();
    public String getId();
    public List<Preference> getPreferences();
    public void importDocument(Object document)
            throws PreferenceGroupException;
    public void remove()
            throws PreferenceGroupException;
    public void setId(String id)
            throws PreferenceGroupException;
    public void setPreferences(List<Preference> preferences)
            throws PreferenceGroupException;
    @Override
    public String toString();
}
