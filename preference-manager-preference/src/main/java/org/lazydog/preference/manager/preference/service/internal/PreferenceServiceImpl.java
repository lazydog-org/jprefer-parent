package org.lazydog.preference.manager.preference.service.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;
import org.lazydog.preference.manager.model.PreferenceGroup;
import org.lazydog.preference.manager.model.PreferenceGroupTree;
import org.lazydog.preference.manager.preference.service.PreferenceService;
import org.lazydog.preference.manager.service.ServiceException;


/**
 * Preference service implementation.
 *
 * @author  Ron Rickard
 */
public class PreferenceServiceImpl implements PreferenceService {

    private static final String ROOT_PATH = "/";
    private static final String STRING_ENCODING = "UTF-8";
    private static Preferences system = Preferences.systemRoot();


    /**
     * Copy the preferences.
     * 
     * @param  sourcePath  the source path.
     * @param  targetPath  the target path.
     * 
     * @throws  ServiceException          if unable to copy the preferences.
     * @throws  NullPointerException      if the source or target path are null.
     * @throws  IllegalArgumentException  if the source path does not exist or
     *                                    the target path already exists.
     */
    @Override
    public void copyPreferences(String sourcePath, String targetPath)
            throws ServiceException {
        
        try {

            // Check if the source path is null.
            if (sourcePath == null) {
                throw new NullPointerException("The source path is null.");
            }

            // Check if the target path is null.
            if (targetPath == null) {
                throw new NullPointerException("The target path is null.");
            }

            // Check if the source path exist.
            if (system.nodeExists(sourcePath)) {

                // Check if the target path does not exist.
                if (!system.nodeExists(targetPath)) {

                    // Copy the source path to the target path.
                    copyTree(system, sourcePath, system, targetPath);
                }
                else {
                    throw new IllegalArgumentException(
                            "The target path, " + targetPath + ", already exists.");
                }
            }
            else {
                throw new IllegalArgumentException(
                        "The source path, " + sourcePath + ", does not exist.");
            }
        }
        catch(BackingStoreException e) {
            throw new ServiceException(
                    "Unable to copy the preferences from "
                    + sourcePath + " to " + targetPath + ".", e);
        }
    }

    /**
     * Copy the preferences tree.
     *
     * @param  sourceSystem  the source system.
     * @param  sourcePath    the source path.
     * @param  targetSystem  the target system.
     * @param  targetPath    the target path.
     *
     * @throws  BackingStoreException  if unable to copy the preferences tree.
     */
    private static void copyTree(Preferences sourceSystem, String sourcePath,
            Preferences targetSystem, String targetPath)
            throws BackingStoreException {

        // Declare.
        Preferences sourcePreferences;
        Preferences targetPreferences;

        // Get the source preferences.
        sourcePreferences = sourceSystem.node(sourcePath);

        // Create the target preferences.
        targetPreferences = targetSystem.node(targetPath);

        // Loop through the keys.
        for (String key : sourcePreferences.keys()) {

            // Add the source preference to the target preferences.
            targetPreferences.put(key, sourcePreferences.get(key, null));
        }

        // Flush the target preferences.
        targetSystem.flush();

        // Loop through the source children.
        for (String childName : sourcePreferences.childrenNames()) {

            // Generate the source and target paths.
            sourcePath = generatePath(sourcePath, childName);
            targetPath = generatePath(targetPath, childName);

            // Copy the child preference tree.
            copyTree(sourceSystem, sourcePath, targetSystem, targetPath);
        }
    }

    /**
     * Export the preferences to a document.
     *
     * @return  the document.
     *
     * @throws  ServiceException  if unable to export the preferences.
     */
    @Override
    public Object exportDocument()
            throws ServiceException {
        return this.exportDocument(system.absolutePath());
    }

    /**
     * Export the preferences to a document.
     *
     * @param  path  the path.
     *
     * @return  the document.
     *
     * @throws  ServiceException  if unable to export the preferences.
     */
    @Override
    public Object exportDocument(String path)
            throws ServiceException {

        // Declare.
        String document;

        // Initialize.
        document = null;

        try {

            // Check if the path is null.
            if (path == null) {
                throw new NullPointerException("The path is null.");
            }

            // Check if the preferences exists.
            if (system.nodeExists(path)) {

                // Declare.
                ByteArrayOutputStream outputStream;

                // Get the output stream.
                outputStream = new ByteArrayOutputStream();

                // Export the preferences to a output stream.
                system.node(path).exportSubtree(outputStream);

                // Convert the output stream to a document.
                document = outputStream.toString(STRING_ENCODING);
            }
        }
        catch(BackingStoreException e) {
            throw new ServiceException(
                    "Unable to export the preferences, " + path + ".", e);
        }
        catch(IOException e) {
            throw new ServiceException(
                    "Unable to export the preferences, " + path + ".", e);
        }

        return document;
    }

    /**
     * Find the preference group.
     * 
     * @param  path  the path.
     * 
     * @return  the preference group.
     * 
     * @throws  ServiceException          if unable to find the
     *                                    preference group.
     * @throws  NullPointerException      if the path are null.
     * @throws  IllegalArgumentException  if the path does not exist.
     */
    @Override
    public PreferenceGroup findPreferenceGroup(String path)
            throws ServiceException {

        // Declare.
        PreferenceGroup preferenceGroup;

        // Initialize.
        preferenceGroup = null;

        try {

            // Check if the path is null.
            if (path == null) {
                throw new NullPointerException("The path is null.");
            }

            // Check if the preferences exists.
            if (system.nodeExists(path)) {

                // Set the preference group.
                preferenceGroup = new PreferenceGroup();
                preferenceGroup.setPath(path);

                // Loop through the keys.
                for (String key : Preferences.systemRoot()
                        .node(path).keys()) {

                    // Add the preference to the preference group.
                    preferenceGroup.getPreferences().put(key, 
                            system.node(path)
                            .get(key, null));
                }
            }
            else {
                throw new IllegalArgumentException(
                        "The path, " + path + ", does not exist.");
            }
        }
        catch(BackingStoreException e) {
            throw new ServiceException(
                    "Unable to find the preferences group, " + path + ".", e);
        }

        return preferenceGroup;
    }

    /**
     * Find the preference group tree.
     *
     * @return  the preference group tree.
     */
    @Override
    public PreferenceGroupTree findPreferenceGroupTree() {
        return new PreferenceGroupTreeImpl(ROOT_PATH);
    }

    /**
     * Generate a path.
     *
     * @param  path  the path.
     * @param  name  the name.
     *
     * @return  the new absolute path.
     *
     * @throws  NullPointerException  if either the path or name is null.
     */
    private static String generatePath(String path, String name) {

        // Check if the path is null.
        if (path == null) {
            throw new NullPointerException("The path is null.");
        }

        // Check if the name is null.
        if (name == null) {
            throw new NullPointerException("The name is null.");
        }

        return (path.equals(ROOT_PATH)) ?
                path + name :
                path + "/" + name;
    }

    /**
     * Import the preferences from a document.
     *
     * @param  document  the document.
     *
     * @throws  ServiceException  if unable to import the preferences.
     */
    @Override
    public void importDocument(Object document)
            throws ServiceException {
        this.importDocument(system.absolutePath(), document);
    }

    /**
     * Import the preferences as a document.
     *
     * @param  path      the path.
     * @param  document  the document.
     *
     * @throws  ServiceException  if unable to import the preferences.
     */
    @Override
    public void importDocument(String path, Object document)
            throws ServiceException {

        try {

            // Declare.
            ByteArrayInputStream inputStream;

            // Remove the preferences.
            this.removePreferences(path);

            // Convert the document to a input stream.
            inputStream = new ByteArrayInputStream(
                    ((String)document).getBytes((STRING_ENCODING)));

            // Import the input stream.
            Preferences.importPreferences(inputStream);
        }
        catch(InvalidPreferencesFormatException e) {
            throw new ServiceException(
                    "Unable to import the preferences, " + path + ".", e);
        }
        catch(IOException e) {
            throw new ServiceException(
                    "Unable to import the preferences, " + path + ".", e);
        }
        catch(ServiceException e) {
            throw new ServiceException(
                    "Unable to import the preferences, " + path + ".", e);
        }
    }

    /**
     * Move the preferences.
     * 
     * @param  sourcePath  the source path.
     * @param  targetPath  the target path.
     * 
     * @throws  ServiceException  if unable to move the preferences.
     */
    @Override
    public void movePreferences(String sourcePath, String targetPath)
            throws ServiceException {

        try {

            // Copy the preferences.
            this.copyPreferences(sourcePath, targetPath);

            // Remove the preferences.
            this.removePreferences(sourcePath);
        }
        catch(ServiceException e) {
            throw new ServiceException(
                    "Unable to move the preferences from "
                    + sourcePath + " to " + targetPath + ".", e);
        }
    }

    /**
     * Persist the preference group.
     *
     * @param  preferenceGroup  the preference group.
     *
     * @throws  ServiceException          if unable to persist the
     *                                    preference group.
     * @throws  NullPointerException      if the preference group is null.
     * @throws  IllegalArgumentException  if the preference group is invalid.
     */
    @Override
    public void persistPreferenceGroup(PreferenceGroup preferenceGroup)
            throws ServiceException {

        try {

            // Check if the preference group is null.
            if (preferenceGroup == null) {
                throw new NullPointerException("The preference group is null.");
            }

            // Check if the preference group path is valid.
            if (preferenceGroup.getPath() != null) {

                // Declare.
                Preferences preferences;

                // Create/get the preferences.
                preferences = system.node(preferenceGroup.getPath());

                // Clear the preferences.
                preferences.clear();

                // Loop through the keys.
                for (String key : preferenceGroup.getPreferences().keySet()) {

                    // Add the preference to the preferences.
                    preferences.put(key,
                            preferenceGroup.getPreferences().get(key));
                }

                // Flush the preferences.
                preferences.flush();
            }
            else {
                throw new IllegalArgumentException(
                        "The preference group is invalid.");
            }
        }
        catch(BackingStoreException e) {
            throw new ServiceException(
                    "Unable to persist the preference group, "
                    + preferenceGroup + ".", e);
        }
    }

    /**
     * Remove the preferences.
     *
     * @param  path  the path.
     *
     * @throws  ServiceException          if unable to remove the preferences.
     * @throws  NullPointerException      if the path is null.
     * @throws  IllegalArgumentException  if the path does not exist.
     */
    @Override
    public void removePreferences(String path)
            throws ServiceException {

        try {

            // Check if the path is null.
            if (path == null) {
                throw new NullPointerException("The path is null.");
            }

            // Check if the preferences exists.
            if (system.nodeExists(path)) {

                // Remove the preferences.
                system.node(path).removeNode();

                // Flush the preferences.
                system.flush();
            }
            else {
                throw new IllegalArgumentException(
                        "The path, " + path + ", does not exist.");
            }
        }
        catch(Exception e) {
            throw new ServiceException(
                    "Unable to remove the preferences, " + path + ".", e);
        }
    }
}
