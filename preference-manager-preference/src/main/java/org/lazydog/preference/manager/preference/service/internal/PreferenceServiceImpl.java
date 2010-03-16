package org.lazydog.preference.manager.preference.service.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.prefs.Preferences;
import org.lazydog.preference.manager.model.PreferenceGroup;
import org.lazydog.preference.manager.model.PreferenceGroupTree;
import org.lazydog.preference.manager.preference.service.PreferenceService;
import org.lazydog.preference.manager.preference.service.PreferenceServiceException;


/**
 * Preference service implementation.
 *
 * @author  Ron Rickard
 */
public class PreferenceServiceImpl implements PreferenceService {

    private static final String STRING_ENCODING = "UTF-8";

    /**
     * Export the preference groups to a document.
     *
     * @return  the document.
     *
     * @throws  PreferenceServiceException  if unable to export the
     *                                      preference groups.
     */
    @Override
    public Object exportDocument()
            throws PreferenceServiceException {
        return this.exportDocument(Preferences.systemRoot().absolutePath());
    }

    /**
     * Export the preference group to a document.
     *
     * @param  absolutePath  the absolute path.
     *
     * @return  the document.
     *
     * @throws  PreferenceServiceException  if unable to export the
     *                                      preference group.
     */
    @Override
    public Object exportDocument(String absolutePath)
            throws PreferenceServiceException {

        // Declare.
        String document;

        // Initialize.
        document = null;

        try {

            // Check if the preference group exists.
            if (Preferences.systemRoot().nodeExists(absolutePath)) {

                // Declare.
                ByteArrayOutputStream outputStream;

                // Get the output stream.
                outputStream = new ByteArrayOutputStream();

                // Export the preference group to a output stream.
                Preferences.systemRoot().node(absolutePath).exportSubtree(outputStream);

                // Convert the output stream to a document.
                document = outputStream.toString(STRING_ENCODING);
            }
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to export the preference group "
                    + absolutePath + ".", e);
        }

        return document;
    }

    /**
     * Find the preference group.
     * 
     * @param  absolutePath  the absolute path.
     * 
     * @return  the preference group.
     * 
     * @throws  PreferenceServiceException  if unable to find the 
     *                                      preference group.
     */
    @Override
    public PreferenceGroup findPreferenceGroup(String absolutePath)
            throws PreferenceServiceException {

        // Declare.
        PreferenceGroup preferenceGroup;

        // Initialize.
        preferenceGroup = null;

        try {

            // Check if the preference group exists.
            if (Preferences.systemRoot().nodeExists(absolutePath)) {

                // Set the preference group.
                preferenceGroup = new PreferenceGroup();
                preferenceGroup.setAbsolutePath(absolutePath);

                // Loop through the keys.
                for (String key : Preferences.systemRoot().node(absolutePath).keys()) {

                    // Add the preference to the preference group.
                    preferenceGroup.getPreferences().put(key,
                            Preferences.systemRoot().node(absolutePath).get(key, null));
                }
            }
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to find the preference group "
                    + absolutePath + ".", e);
        }

        return preferenceGroup;
    }

    /**
     * Find the preference group tree.
     *
     * @return  the preference group tree.
     *
     * @throws  PreferenceServiceException  if unable to find the
     *                                      preference group tree.
     */
    @Override
    public PreferenceGroupTree findPreferenceGroupTree()
            throws  PreferenceServiceException {
        return new PreferenceGroupTreeImpl(PreferenceGroupTreeImpl.ROOT_ABSOLUTE_PATH);
    }

    /**
     * Import the preference group from a document.
     *
     * @param  document  the document.
     *
     * @throws  PreferenceServiceException  if unable to import the
     *                                      preference group.
     */
    @Override
    public void importDocument(Object document)
            throws PreferenceServiceException {
        this.importDocument(Preferences.systemRoot().absolutePath(), document);
    }

    /**
     * Import the preferences as a document.
     *
     * @param  absolutePath  the absolute path.
     * @param  document      the document.
     *
     * @throws  PreferenceServiceException  if unable to import the
     *                                      preferences.
     */
    @Override
    public void importDocument(String absolutePath, Object document)
            throws PreferenceServiceException {

        try {

            // Declare.
            ByteArrayInputStream inputStream;

            // Remove the preference group.
            this.removePreferenceGroup(absolutePath);

            // Convert the document to a input stream.
            inputStream = new ByteArrayInputStream(
                    ((String)document).getBytes((STRING_ENCODING)));

            // Import the input stream.
            Preferences.importPreferences(inputStream);
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to import the preference group " 
                    + absolutePath + ".", e);
        }
    }

    /**
     * Persist the preference group.
     *
     * @param  preferenceGroup  the preference group.
     *
     * @throws  PreferenceServiceException  if unable to persist the
     *                                      preference group.
     */
    @Override
    public void persistPreferenceGroup(PreferenceGroup preferenceGroup)
            throws PreferenceServiceException {

        try {

            // Declare.
            Preferences preferences;

            // Create/get the preference group.
            preferences = Preferences.systemRoot().node(preferenceGroup.getAbsolutePath());

            // Clear the preferences for the preference group.
            preferences.clear();

            // Loop through the keys.
            for (String key : preferenceGroup.getPreferences().keySet()) {

                // Add the preference to the preference group.
                preferences.put(key, preferenceGroup.getPreferences().get(key));
            }

            // Flush the preference group.
            preferences.flush();
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to persist the preference group "
                    + preferenceGroup + ".", e);
        }
    }

    /**
     * Remove the preference group.
     *
     * @param  absolutePath  the absolute path.
     *
     * @throws  PreferenceServiceException  if unable to remove the
     *                                      preference group.
     */
    @Override
    public void removePreferenceGroup(String absolutePath)
            throws PreferenceServiceException {

        try {

            // Check if the preference group exists.
            if (Preferences.systemRoot().nodeExists(absolutePath)) {

                // Remove the preference group.
                Preferences.systemRoot().node(absolutePath).removeNode();

                // Flush the preference group.
                Preferences.systemRoot().flush();
            }
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to remove the preference group "
                    + absolutePath + ".", e);
        }
    }
}
