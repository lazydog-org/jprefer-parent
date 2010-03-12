package org.lazydog.preference.service.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.prefs.Preferences;
import org.lazydog.preference.service.LocalPreferenceService;
import org.lazydog.preference.service.PreferenceServiceException;


/**
 * Local preference service implementation.
 * 
 * @author  Ron Rickard
 */
public class LocalPreferenceServiceImpl implements LocalPreferenceService {

    private static final String STRING_ENCODING = "UTF-8";

    /**
     * Check if the preferences are equal to the specified preferences.
     *
     * @param  preferences  the preferences.
     *
     * @return  true if the preferences are equal, otherwise false.
     *
     * @throws  PreferenceServiceException  if unable to check if the
     *                                      preferences are equal.
     */
    @Override
    public boolean areEqual(String preferences)
           throws PreferenceServiceException {

        // Declare.
        boolean areEqual;

        // Initialize.
        areEqual = false;

        try {

            // Declare.
            String thesePreferences;

            // Get all the preferences.
            thesePreferences = this.getAll();

            // Check if the preferences are equal.
            if (thesePreferences.equals(preferences)) {

                areEqual = true;
            }
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to check if the preferences are equal.", e);
        }

        return areEqual;
    }

    /**
     * Create or replace the preferences at the path name with the
     * specified preferences.
     *
     * @param  pathName     the path name.
     * @param  preferences  the preferences.
     *
     * @throws  PreferenceServiceException  if unable to create or replace the
     *                                      preferences.
     */
    @Override
    public void createOrReplace(String pathName, String preferences)
            throws PreferenceServiceException {

        try {

            // Declare.
            ByteArrayInputStream inputStream;

            // Check if the path name exists.
            if (Preferences.systemRoot().nodeExists(pathName)) {

                // Remove the path name.
                Preferences.systemRoot().node(pathName).removeNode();
            }

            // Convert the preferences to an input stream.
            inputStream = new ByteArrayInputStream(
                    preferences.getBytes((STRING_ENCODING)));

            // Import the preferences.
            Preferences.importPreferences(inputStream);
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to create or replace the preferences at the path name "
                    + pathName + ".", e);
        }
    }

    /**
     * Get the preferences at the path name.
     *
     * @param  pathName  the path name.
     *
     * @return  the preferences.
     *
     * @throws  PreferenceServiceException  if unable to get the preferences.
     */
    @Override
    public String get(String pathName)
            throws PreferenceServiceException {

        // Declare.
        String preferences;

        // Initialize.
        preferences = null;

        try {

            // Declare.
            ByteArrayOutputStream outputStream;

            // Get the output stream.
            outputStream = new ByteArrayOutputStream();

            // Export the preferences for the path name.
            Preferences.systemRoot().node(pathName).exportNode(outputStream);

            // Convert the output stream to the preferences.
            preferences = outputStream.toString(STRING_ENCODING);
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to get the preferences at the path name "
                    + pathName + ".", e);
        }

        return preferences;
    }

    /**
     * Get all the preferences.
     *
     * @return  all the preferences.
     *
     * @throws  PreferenceServiceException  if unable to get all the
     *                                      preferences.
     */
    @Override
    public String getAll() 
            throws PreferenceServiceException {

        // Declare.
        String preferences;

        // Initialize.
        preferences = null;

        try {
            
            // Declare.
            ByteArrayOutputStream outputStream;

            // Get the output stream.
            outputStream = new ByteArrayOutputStream();

            // Export the preferences.
            Preferences.systemRoot().exportSubtree(outputStream);

            // Convert the output stream to the preferences.
            preferences = outputStream.toString(STRING_ENCODING);
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to get all the preferences.", e);
        }

        return preferences;
    }

    /**
     * Remove the preferences at the path name.
     *
     * @param  pathName  the path name.
     *
     * @throws  PreferenceServiceException  if unable to remove the preferences.
     */
    @Override
    public void remove(String pathName)
            throws PreferenceServiceException {

        try {

            // Remove the preferences.
            Preferences.systemRoot().node(pathName).removeNode();
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to remove the preferences at the path name "
                    + pathName + ".", e);
        }
    }

    /**
     * Replace all the preferences with the specified preferences.
     *
     * @param  preferences  the preferences.
     *
     * @throws  PreferenceServiceException  if unable to replace all the
     *                                      preferences.
     */
    @Override
    public void replaceAll(String preferences)
            throws PreferenceServiceException {

        try {

            // Declare.
            ByteArrayInputStream inputStream;

            // Remove all the preferences.
            Preferences.systemRoot().removeNode();

            // Convert the preferences to an input stream.
            inputStream = new ByteArrayInputStream(
                    preferences.getBytes((STRING_ENCODING)));

            // Import the preferences.
            Preferences.importPreferences(inputStream);
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to replace all the preferences.", e);
        }
    }
}