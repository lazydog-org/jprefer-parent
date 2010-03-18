package org.lazydog.preference.manager.preference.service.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;
import org.lazydog.preference.manager.model.PreferencesTree;
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
                            "The target path, " + targetPath
                            + ", already exists.");
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

            // Declare.
            String newSourcePath;
            String newTargetPath;

            // Generate the source and target paths.
            newSourcePath = generatePath(sourcePath, childName);
            newTargetPath = generatePath(targetPath, childName);

            // Copy the child preference tree.
            copyTree(sourceSystem, newSourcePath, targetSystem, newTargetPath);
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
    public String exportDocument()
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
    public String exportDocument(String path)
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

            // Check if the path exists.
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
     * Find the preferences.
     * 
     * @param  path  the path.
     * 
     * @return  the preferences.
     * 
     * @throws  ServiceException          if unable to find the preferences.
     * @throws  NullPointerException      if the path is null.
     * @throws  IllegalArgumentException  if the path does not exist.
     */
    public Map<String,String> findPreferences(String path)
            throws ServiceException {

        // Declare.
        Map<String,String> preferences;

        // Initialize.
        preferences = new LinkedHashMap<String,String>();

        try {

            // Check if the path is null.
            if (path == null) {
                throw new NullPointerException("The path is null.");
            }

            // Check if the path exists.
            if (system.nodeExists(path)) {

                // Loop through the keys.
                for (String key : system.node(path).keys()) {

                    // Add the preference to the preferences.
                    preferences.put(key, system.node(path).get(key, null));
                }
            }
            else {
                throw new IllegalArgumentException(
                        "The path, " + path + ", does not exist.");
            }
        }
        catch(BackingStoreException e) {
            throw new ServiceException(
                    "Unable to find the preferences, " + path + ".", e);
        }

        return preferences;
    }

    /**
     * Find the preferences tree.
     *
     * @return  the preferences tree.
     *
     * @throws  ServiceException  if unable to find the preferences tree.
     */
    @Override
    public PreferencesTree findPreferencesTree() 
            throws ServiceException {
        return new PreferencesTreeImpl(ROOT_PATH);
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
    public void importDocument(String document)
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
    public void importDocument(String path, String document)
            throws ServiceException {

        try {

            // Declare.
            ByteArrayInputStream inputStream;

            // Remove the preferences.
            this.removePreferences(path);

            // Convert the document to a input stream.
            inputStream = new ByteArrayInputStream(
                    document.getBytes((STRING_ENCODING)));

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
     * @throws  ServiceException          if unable to move the preferences.
     * @throws  NullPointerException      if the source or target path are null.
     * @throws  IllegalArgumentException  if the source path does not exist or
     *                                    the target path already exists.
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
     * Persist the preference.
     *
     * @param  path   the path.
     * @param  key    the key.
     * @param  value  the value.
     *
     * @return  the preference value.
     *
     * @throws  ServiceException          if unable to persist the preference.
     * @throws  NullPointerException      if the path, key, or value is null.
     * @throws  IllegalArgumentException  if the path does not exist.
     */
    @Override
    public String persistPreference(String path, String key, String value)
            throws ServiceException {

        try {

            // Check if the path is null.
            if (path == null) {
                throw new NullPointerException("The path is null.");
            }

            // Check if the key is null.
            if (key == null) {
                throw new NullPointerException("The key is null.");
            }

            // Check if the value is null.
            if (key == null) {
                throw new NullPointerException("The value is null.");
            }

            // Check if the path exists.
            if (system.nodeExists(path)) {

                // Persist the preference.
                system.node(path).put(key, value);

                // Flush the system.
                system.flush();
            }
            else {
                throw new IllegalArgumentException(
                        "The path, " + path + ", does not exist.");
            }

            // Get the value.
            system.node(path).get(key, null);
        }
        catch(BackingStoreException e) {
            throw new ServiceException(
                    "Unable to persist the preference, "
                    + key + "=" + value + ", at " + path + ".", e);
        }

        return value;
    }

    /**
     * Persist the preferences.
     *
     * @param  path   the path.
     *
     * @return  the preferences.
     *
     * @throws  ServiceException          if unable to persist the preferences.
     * @throws  NullPointerException      if the path is null.
     * @throws  IllegalArgumentException  if the path already exists.
     */
    @Override
    public String persistPreferences(String path)
            throws ServiceException {

        try {

            // Check if the path is null.
            if (path == null) {
                throw new NullPointerException("The path is null.");
            }

            // Check if the path does not exists.
            if (!system.nodeExists(path)) {

                // Persist the preferences.
                system.node(path);

                // Flush the system.
                system.flush();
            }
            else {
                throw new IllegalArgumentException(
                        "The path, " + path + ", already exist.");
            }

            // Get the path.
            path = system.node(path).absolutePath();
        }
        catch(BackingStoreException e) {
            throw new ServiceException(
                    "Unable to persist the preference path, " + path + ".", e);
        }

        return path;
    }

    /**
     * Remove the preference.
     *
     * @param  path  the path.
     * @param  key   the key.
     *
     * @throws  ServiceException          if unable to remove the preference.
     * @throws  NullPointerException      if the path or key is null.
     * @throws  IllegalArgumentException  if the path or key does not exist.
     */
    @Override
    public void removePreference(String path, String key)
            throws ServiceException {

        try {

            // Check if the path is null.
            if (path == null) {
                throw new NullPointerException("The path is null.");
            }

            // Check if the key is null.
            if (key == null) {
                throw new NullPointerException("The key is null.");
            }

            // Check if the path exists.
            if (system.nodeExists(path)) {

                // Check if the key exists.
                if (system.node(path).get(key, null) != null) {

                    // Remove the preference.
                    system.node(path).remove(key);

                    // Flush the system.
                    system.flush();
                }
                else {
                    throw new IllegalArgumentException(
                            "The key, " + key + ", does not exist.");
                }
            }
            else {
                throw new IllegalArgumentException(
                        "The path, " + path + ", does not exist.");
            }
        }
        catch(BackingStoreException e) {
            throw new ServiceException(
                    "Unable to remove the preference, "
                    + key + ", at " + path + ".", e);
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

            // Check if the path exists.
            if (system.nodeExists(path)) {

                // Remove the preferences.
                removeTree(system, path);
            }
            else {
                throw new IllegalArgumentException(
                        "The path, " + path + ", does not exist.");
            }
        }
        catch(BackingStoreException e) {
            throw new ServiceException(
                    "Unable to remove the preferences, " + path + ".", e);
        }
    }

    /**
     * Remove the preference tree.
     *
     * @param  system  the system.
     * @param  path    the path.
     *
     * @throws  BackingStoreException  if unable to remove the preference tree.
     */
    private static void removeTree(Preferences system, String path)
            throws BackingStoreException {

        // Check if the path is the root path.
        if (system.node(path).absolutePath().equals(ROOT_PATH)) {

            // Loop through the root children.
            for (String childName : system.childrenNames()) {

                // Remove the child tree.
                system.node(childName).removeNode();
            }

            // Clear the root preferences.
            system.node(path).clear();
        }
        else {

            // Remove the preference tree.
            system.node(path).removeNode();
        }

        // Flush the system.
        system.flush();
    }
}
