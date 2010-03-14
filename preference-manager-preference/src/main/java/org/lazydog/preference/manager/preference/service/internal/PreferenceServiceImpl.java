package org.lazydog.preference.manager.preference.service.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;
import org.lazydog.preference.manager.model.PreferenceGroupTree;
import org.lazydog.preference.manager.preference.service.PreferenceService;
import org.lazydog.preference.manager.preference.service.PreferenceServiceException;


/**
 * Preference service implementation.
 *
 * @author  Ron Rickard
 */
public class PreferenceServiceImpl implements PreferenceService {

    public static final String ROOT_GROUP_ID = "/";
    private static final String STRING_ENCODING = "UTF-8";
    private Preferences preferences;

    /**
     * Constructor.
     *
     * @throws  PreferenceServiceException  if unable to create.
     */
    public PreferenceServiceImpl()
            throws PreferenceServiceException {
        this.setId(ROOT_GROUP_ID);
    }

    /**
     * Private constructor.
     *
     * @param  id  the ID.
     *
     * @throws  PreferenceServiceException  if unable to create.
     */
    private PreferenceServiceImpl(String id)
            throws PreferenceServiceException {
        this.setId(id);
    }

    /**
     * Export the preference group as a document.
     *
     * @return  the document.
     *
     * @throws  PreferenceServiceException  if unable to export the
     *                                      preference group.
     */
    @Override
    public Object exportDocument()
            throws PreferenceServiceException {

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
            throw new PreferenceServiceException(
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
    public List<PreferenceGroupTree> getChildren() {

        // Declare.
        List<PreferenceGroupTree> children;

        // Initialize.
        children = new ArrayList<PreferenceGroupTree>();

        try {

            // Loop through the children names.
            for (String childName : this.preferences.childrenNames()) {

                // Declare.
                PreferenceServiceImpl child;

                // Get the child.
                child = new PreferenceServiceImpl(this.preferences.node(childName).absolutePath());

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
    public Map<String,String> getPreferences() {

        // Declare.
        Map<String,String> preferences;

        // Initialize.
        preferences = new LinkedHashMap<String,String>();

        try {

            // Loop through the preference keys.
            for (String key : this.preferences.keys()) {

                // Declare.
                String value;

                // Get the value for the key.
                value = this.preferences.get(key, "");

                // Add the preference to the preferences.
                preferences.put(key, value);
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
     * @throws  PreferenceServiceException  if unable to import the
     *                                      preference group.
     */
    @Override
    public void importDocument(Object document)
            throws PreferenceServiceException {

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
            throw new PreferenceServiceException(
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
     * @throws  PreferenceServiceException  if unable to remove the
     *                                      preference group.
     */
    @Override
    public void remove()
            throws PreferenceServiceException {

        try {

            // Remove the preference group.
            this.preferences.removeNode();

            // Flush the preference group.
            this.preferences.flush();
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to remove the preference group "
                    + this.getId() + ".", e);
        }
    }

    /**
     * Set the ID.
     *
     * @return  the ID.
     *
     * @throws  PreferenceServiceException  if unable to set the ID.
     */
    @Override
    public void setId(String id)
            throws PreferenceServiceException {

        try {

            // Create the preference group.
            this.preferences = Preferences.userRoot().node(id);

            // Flush the preference group.
            this.preferences.flush();
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to create preference group " + id + ".", e);
        }
    }

    /**
     * Set the preferences.
     *
     * @param  preferences  the preferences.
     *
     * @throws  PreferenceServiceException  if unable to set the preferences.
     */
    @Override
    public void setPreferences(Map<String,String> preferences)
            throws PreferenceServiceException {

        try {

            // Clear the preferences for the preference group.
            this.preferences.clear();

            // Check if the preferences exist.
            if (preferences != null && preferences.size() > 0) {

                // Loop through the preferences.
                for (String key : preferences.keySet()) {

                    // Add the preference to the preference group.
                    this.preferences.put(key, preferences.get(key));
                }
            }

            // Flush the preference group.
            this.preferences.flush();
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
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
