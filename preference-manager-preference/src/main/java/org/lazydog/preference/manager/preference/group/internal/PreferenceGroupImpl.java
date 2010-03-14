package org.lazydog.preference.manager.preference.group.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import org.lazydog.preference.manager.model.Preference;
import org.lazydog.preference.manager.preference.group.PreferenceGroup;
import org.lazydog.preference.manager.preference.group.PreferenceGroupException;


/**
 * Preference group implementation.
 *
 * @author  Ron Rickard
 */
public class PreferenceGroupImpl implements PreferenceGroup {

    public static final String ROOT_GROUP_ID = "/";
    private static final String SLASH = "/";
    private static final String STRING_ENCODING = "UTF-8";
    private Preferences preferences;

    /**
     * Constructor.
     *
     * @throws  PreferenceGroupException  if unable to create.
     */
    public PreferenceGroupImpl()
            throws PreferenceGroupException {
        this.setId(ROOT_GROUP_ID);
    }

    /**
     * Private constructor.
     *
     * @param  id  the ID.
     *
     * @throws  PreferenceGroupException  if unable to create.
     */
    private PreferenceGroupImpl(String id)
            throws PreferenceGroupException {
        this.setId(id);
    }

    /**
     * Export the preference group as a document.
     *
     * @return  the document.
     *
     * @throws  PreferenceGroupException  if unable to export the
     *                                    preference group.
     */
    @Override
    public Object exportDocument()
            throws PreferenceGroupException {

        // Declare.
        String preferenceGroup;

        // Initialize.
        preferenceGroup = null;

        try {

            // Declare.
            ByteArrayOutputStream outputStream;

            // Get the output stream.
            outputStream = new ByteArrayOutputStream();

            // Export the preference group as a output stream.
            this.preferences.exportNode(outputStream);

            // Convert the output stream to a string.
            preferenceGroup = outputStream.toString(STRING_ENCODING);
        }
        catch(Exception e) {
            throw new PreferenceGroupException(
                    "Unable to export the preference group " 
                    + this.getId() + ".", e);
        }

        return preferenceGroup;
    }

    /**
     * Get the children.
     *
     * @return  the children.
     */
    @Override
    public List<PreferenceGroup> getChildren() {

        // Declare.
        List<PreferenceGroup> children;

        // Initialize.
        children = new ArrayList<PreferenceGroup>();

        try {

            // Loop through the children names.
            for (String childName : this.preferences.childrenNames()) {
                
                // Declare.
                PreferenceGroupImpl child;

                // Check if this is the root group.
                if (this.isRootGroup()) {

                    // Get the child for the root group.
                    child = new PreferenceGroupImpl(SLASH + childName);
                }
                else {

                    // Get the child.
                    child = new PreferenceGroupImpl(this.preferences.absolutePath() + SLASH + childName);
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
     * Get the ID.
     *
     * @return  the ID.
     */
    @Override
    public String getId() {
        return this.preferences.absolutePath();
    }

    /**
     * Get the preferences.
     *
     * @return  the preferences.
     */
    @Override
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
     * Import the preference group as a document.
     *
     * @param  document  the document.
     *
     * @throws  PreferenceGroupException  if unable to import the
     *                                    preference group.
     */
    @Override
    public void importDocument(Object document)
            throws PreferenceGroupException {

        try {

            // Declare.
            String id;
            ByteArrayInputStream inputStream;

            // Get the id.
            id = this.getId();

            // Remove the preference group.
            this.remove();

            // Convert the document to an input stream.
            inputStream = new ByteArrayInputStream(
                    ((String)document).getBytes((STRING_ENCODING)));

            // Import the preference group.
            Preferences.importPreferences(inputStream);

            // Set the id.
            this.setId(id);
        }
        catch(Exception e) {
            throw new PreferenceGroupException(
                    "Unable to import the preference group " 
                    + this.getId() + ".", e);
        }
    }

    /**
     * Is this the root group?
     *
     * @return  true if this is the root group, otherwise false.
     */
    private boolean isRootGroup() {
        return this.getId().equals(ROOT_GROUP_ID);
    }

    /**
     * Remove the preference group.
     *
     * @throws  PreferenceGroupException  if unable to remove the
     *                                    preference group.
     */
    @Override
    public void remove()
            throws PreferenceGroupException {

        try {

            // Remove the preference group.
            this.preferences.removeNode();

            // Flush the preference group.
            this.preferences.flush();
        }
        catch(Exception e) {
            throw new PreferenceGroupException(
                    "Unable to remove the preference group "
                    + this.getId() + ".", e);
        }
    }

    /**
     * Set the ID.
     *
     * @return  the ID.
     *
     * @throws  PreferenceGroupException  if unable to set the ID.
     */
    @Override
    public void setId(String id)
            throws PreferenceGroupException {

        try {

            // Create the preference group.
            this.preferences = Preferences.userRoot().node(id);

            // Flush the preference group.
            this.preferences.flush();
        }
        catch(Exception e) {
            throw new PreferenceGroupException(
                    "Unable to create preference group " + id + ".", e);
        }
    }

    /**
     * Set the preferences.
     *
     * @return  the preferences.
     *
     * @throws  PreferenceGroupException  if unable to set the preferences.
     */
    @Override
    public void setPreferences(List<Preference> preferences)
            throws PreferenceGroupException {

        try {

            // Clear the preferences for the preference group.
            this.preferences.clear();

            // Check if the preferences exist.
            if (preferences != null && preferences.size() > 0) {

                // Loop through the preferences.
                for (Preference preference : preferences) {

                    // Add the preference to the preference group.
                    this.preferences.put(preference.getKey(), preference.getValue());
                }
            }

            // Flush the preference group.
            this.preferences.flush();
        }
        catch(Exception e) {
            throw new PreferenceGroupException(
                    "Unable to set the preferences for preference group "
                    + this.getId() + ".", e);
        }
    }

    /**
     * Get this object as a String.
     *
     * @return  this object as a String.
     */
    @Override
    public String toString() {
        return (this.isRootGroup() ? ROOT_GROUP_ID : this.preferences.name());
    }
}