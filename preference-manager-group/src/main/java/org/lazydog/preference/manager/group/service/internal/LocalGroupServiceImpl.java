package org.lazydog.preference.manager.group.service.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.prefs.Preferences;
import org.lazydog.preference.manager.group.service.LocalGroupService;
import org.lazydog.preference.manager.group.service.GroupServiceException;


/**
 * Local group service implementation.
 * 
 * @author  Ron Rickard
 */
public class LocalGroupServiceImpl implements LocalGroupService {

    private static final String STRING_ENCODING = "UTF-8";

    /**
     * Export the preference group.
     *
     * @param  id  the ID.
     *
     * @return  the preference group.
     *
     * @throws  GroupServiceException  if unable to export the preference group.
     */
    @Override
    public Object exportGroup(Object id)
            throws GroupServiceException {

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
            Preferences.systemRoot().node((String)id).exportNode(outputStream);

            // Convert the output stream to a string.
            preferenceGroup = outputStream.toString(STRING_ENCODING);
        }
        catch(Exception e) {
            throw new GroupServiceException(
                    "Unable to export the preference group "
                    + (String)id + ".", e);
        }

        return preferenceGroup;
    }

    /**
     * Export the preference groups.
     *
     * @return  all the preferences.
     *
     * @throws  GroupServiceException  if unable to export the preference groups.
     */
    @Override
    public Object exportGroups()
            throws GroupServiceException {

        // Declare.
        String preferenceGroups;

        // Initialize.
        preferenceGroups = null;

        try {
            
            // Declare.
            ByteArrayOutputStream outputStream;

            // Get the output stream.
            outputStream = new ByteArrayOutputStream();

            // Export the preference group as a output stream.
            Preferences.systemRoot().exportSubtree(outputStream);

            // Convert the output stream to a string.
            preferenceGroups = outputStream.toString(STRING_ENCODING);
        }
        catch(Exception e) {
            throw new GroupServiceException(
                    "Unable to export the preference groups.", e);
        }

        return preferenceGroups;
    }

    /**
     * Import the preference group.
     *
     * @param  id               the ID.
     * @param  preferenceGroup  the preference group.
     *
     * @throws  GroupServiceException  if unable to import the preference group.
     */
    @Override
    public void importGroup(Object id, Object preferenceGroup)
            throws GroupServiceException {

        try {

            // Declare.
            ByteArrayInputStream inputStream;

            // Check if the preference group exists.
            if (Preferences.systemRoot().nodeExists((String)id)) {

                // Remove the preference group.
                Preferences.systemRoot().node((String)id).removeNode();
            }

            // Convert the preference group to an input stream.
            inputStream = new ByteArrayInputStream(
                    ((String)preferenceGroup).getBytes((STRING_ENCODING)));

            // Import the preference group.
            Preferences.importPreferences(inputStream);
        }
        catch(Exception e) {
            throw new GroupServiceException(
                    "Unable to import the preference group "
                    + (String)id + ".", e);
        }
    }

    /**
     * Import the preference groups.
     *
     * @param  id                the ID.
     * @param  preferenceGroups  the preference groups.
     *
     * @throws  GroupServiceException  if unable to import the preference groups.
     */
    @Override
    public void importGroups(Object preferences)
            throws GroupServiceException {

        try {

            // Declare.
            ByteArrayInputStream inputStream;

            // Remove all the preferences.
            Preferences.systemRoot().removeNode();

            // Convert the preference groups to an input stream.
            inputStream = new ByteArrayInputStream(
                    ((String)preferences).getBytes((STRING_ENCODING)));

            // Import the preference groups.
            Preferences.importPreferences(inputStream);
        }
        catch(Exception e) {
            throw new GroupServiceException(
                    "Unable to import the preference groups.", e);
        }
    }
    
    /**
     * Remove the preference group.
     *
     * @param  id  the ID.
     *
     * @throws  GroupServiceException  if unable to remove the preference group.
     */
    @Override
    public void removeGroup(Object id)
            throws GroupServiceException {

        try {

            // Remove the preference group.
            Preferences.systemRoot().node((String)id).removeNode();
        }
        catch(Exception e) {
            throw new GroupServiceException(
                    "Unable to remove the preference group "
                    + (String)id + ".", e);
        }
    }

    /**
     * Remove the preference groups.
     *
     * @throws  GroupServiceException  if unable to remove the preference groups.
     */
    @Override
    public void removeGroups()
            throws GroupServiceException {

        try {

            // Remove the preference groups.
            Preferences.systemRoot().removeNode();
        }
        catch(Exception e) {
            throw new GroupServiceException(
                    "Unable to remove the preference groups.", e);
        }
    }
}