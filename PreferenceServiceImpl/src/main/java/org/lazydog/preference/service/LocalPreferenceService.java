package org.lazydog.preference.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.prefs.Preferences;
import org.lazydog.preference.api.PreferenceService;
import org.lazydog.preference.exception.PreferenceServiceException;


/**
 * Local preference service.
 * 
 * @author  Ron Rickard
 */
public class LocalPreferenceService
        implements PreferenceService {

    /**
     * Create or replace the node specified by the path name with the
     * configuration in the XML string.
     *
     * @param  pathName   the path name.
     * @param  xmlString  the configuration as a XML string.
     *
     * @throws  PreferenceServiceException  if unable to create or replace the
     *                                      node.
     */
    @Override
    public void createOrReplaceNode(String pathName, String xmlString)
            throws PreferenceServiceException {

        try {

            // Declare.
            ByteArrayInputStream inputStream;

            // Check if the node exists.
            if (Preferences.systemRoot().nodeExists(pathName)) {

                // Remove the node.
                Preferences.systemRoot().node(pathName).removeNode();
            }

            // Convert the XML string to an input stream.
            inputStream = new ByteArrayInputStream(
                    xmlString.getBytes((XMLSTRING_ENCODING)));

            // Import the preferences.
            Preferences.importPreferences(inputStream);
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to create or replace the node for path name "
                    + pathName + ".", e);
        }
    }

    /**
     * Get all the nodes.
     *
     * @return  the configuration for all the nodes as a XML string.
     *
     * @throws  PreferenceServiceException  if unable to get all the nodes.
     */
    @Override
    public String getAllNodes() 
            throws PreferenceServiceException {

        // Declare.
        String xmlString;

        // Initialize.
        xmlString = null;

        try {
            
            // Declare.
            ByteArrayOutputStream outputStream;

            // Get the output stream.
            outputStream = new ByteArrayOutputStream();

            // Export the preferences for all the nodes.
            Preferences.systemRoot().exportSubtree(outputStream);

            // Convert the output stream to a XML string.
            xmlString = outputStream.toString(XMLSTRING_ENCODING);
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to get all the nodes.", e);
        }

        return xmlString;
    }

    /**
     * Get the node specified by the path name.
     *
     * @param  pathName  the path name.
     *
     * @return  the configuration for the node as a XML string.
     *
     * @throws  PreferenceServiceException  if unable to get the node.
     */
    @Override
    public String getNode(String pathName) 
            throws PreferenceServiceException {

        // Declare.
        String xmlString;

        // Initialize.
        xmlString = null;

        try {

            // Declare.
            ByteArrayOutputStream outputStream;

            // Get the output stream.
            outputStream = new ByteArrayOutputStream();

            // Export the preferences for the node.
            Preferences.systemRoot().node(pathName).exportNode(outputStream);

            // Convert the output stream to a XML string.
            xmlString = outputStream.toString(XMLSTRING_ENCODING);
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to get the node for path name "
                    + pathName + ".", e);
        }

        return xmlString;
    }

    /**
     * Remove the node specified by the path name.
     *
     * @param  pathName  the path name.
     *
     * @throws  PreferenceServiceException  if unable to remove the node.
     */
    @Override
    public void removeNode(String pathName)
            throws PreferenceServiceException {

        try {

            // Remove the node.
            Preferences.systemRoot().node(pathName).removeNode();
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to remove the node for path name "
                    + pathName + ".", e);
        }
    }

    /**
     * Replace all the nodes with the configuration in the XML string.
     *
     * @param  xmlString  the configuration as a XML string.
     *
     * @throws  PreferenceServiceException  if unable to replace all the nodes.
     */
    @Override
    public void replaceAllNodes(String xmlString)
            throws PreferenceServiceException {

        try {

            // Declare.
            ByteArrayInputStream inputStream;

            // Remove all the nodes.
            Preferences.systemRoot().removeNode();

            // Convert the XML string to an input stream.
            inputStream = new ByteArrayInputStream(
                    xmlString.getBytes((XMLSTRING_ENCODING)));

            // Import the preferences.
            Preferences.importPreferences(inputStream);
        }
        catch(Exception e) {
            throw new PreferenceServiceException(
                    "Unable to replace all the nodes.", e);
        }
    }
}