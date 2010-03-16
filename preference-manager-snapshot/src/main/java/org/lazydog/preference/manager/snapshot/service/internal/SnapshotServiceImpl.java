package org.lazydog.preference.manager.snapshot.service.internal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.prefs.Preferences;
import org.lazydog.preference.manager.snapshot.service.SnapshotService;
import org.lazydog.preference.manager.snapshot.service.SnapshotServiceException;


/**
 * Snapshot service implementation.
 *
 * @author  Ron Rickard
 */
public class SnapshotServiceImpl implements SnapshotService {

    private static final String CREATE_DATE_KEY = "create.date";
    private static final String DATE_PATTERN = "MM/dd/yyyy HH:mm:ss.SSS";
    private static final String RESTORE_DATE_KEY = "restore.date";
    private static final String ROOT_PATH = "/";
    private static final String SNAPSHOT_NAME_PREFIX = "snapshot-";
    private static DateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
    private static Preferences snapshotSystem = Preferences.userRoot();
    private static Preferences sourceSystem = Preferences.systemRoot();

    /**
     * Copy the preferences tree.
     *
     * @param  sourceSystem  the source system.
     * @param  sourcePath    the source path.
     * @param  targetSystem  the target system.
     * @param  targetPath    the target path.
     *
     * @throws  SnapshotServiceException  if unable to copy the
     *                                    preferences tree.
     */
    private static void copyTree(Preferences sourceSystem, String sourcePath,
            Preferences targetSystem, String targetPath)
            throws SnapshotServiceException {

        try {

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
                copyTree(sourceSystem, sourcePath,
                        targetSystem, targetPath);
            }
        }
        catch(Exception e) {
            throw new SnapshotServiceException(
                    "Unable to copy the preference tree " + sourcePath + " to "
                    + targetPath + ".", e);
        }
    }

    /**
     * Create the snapshot.
     *
     * @param  name  the name.
     *
     * @throws  SnapshotServiceException  if unable to create the snapshot.
     */
    @Override
    public void createSnapshot(String name)
            throws SnapshotServiceException {

        try {

            // Check if the snapshot does not exist.
            if (!snapshotSystem.nodeExists(getSnapshotPath(name))) {

                // Create the snapshot.
                copyTree(sourceSystem, ROOT_PATH, snapshotSystem, getSnapshotPath(name));

                // Add timestamp to snapshot.
                snapshotSystem.node(getSnapshotPath(name))
                        .put(CREATE_DATE_KEY, dateFormat.format(new Date()));
            }
            else {
                throw new SnapshotServiceException(
                        "Unable to create existing snapshot " + name + ".");
            }
        }
        catch(Exception e) {
            throw new SnapshotServiceException(
                    "Unable to create a snapshot " + name + ".", e);
        }
    }

    /**
     * Find the snapshots.
     *
     * @return  the snapshots.
     *
     * @throws  SnapshotServiceException  if unable to find the snapshots.
     */
    @Override
    public Map<String, Date> findSnapshots()
            throws SnapshotServiceException {

        // Declare.
        Map<String,Date> snapshots;

        // Initialize.
        snapshots = new LinkedHashMap<String,Date>();

        try {

            // Loop through the snapshot system children.
            for (String childName : snapshotSystem.childrenNames()) {

                // Check if the child is a snapshot.
                if (childName.startsWith(SNAPSHOT_NAME_PREFIX)) {

                    // Declare.
                    String name;
                    Date date;

                    // Get the name and date.
                    name = childName.replaceFirst(SNAPSHOT_NAME_PREFIX, "");
                    date = dateFormat.parse(
                            snapshotSystem.node(childName).get(CREATE_DATE_KEY, null));

                    // Put the name and date on the list.
                    snapshots.put(name, date);
                }
            }
        }
        catch(Exception e) {
            throw new SnapshotServiceException(
                    "Unable to find the snapshot names.", e);
        }

        return snapshots;
    }

    /**
     * Generate a path.
     *
     * @param  path  the path.
     * @param  name  the name.
     *
     * @return  the new absolute path.
     */
    private static String generatePath(String path, String name) {
        return (path.equals(ROOT_PATH)) ?
                path + name :
                path + "/" + name;
    }

    /**
     * Get the snapshot path.
     *
     * @param  name  the name.
     *
     * @return  the snapshot path.
     */
    private static String getSnapshotPath(String name) {
        return ROOT_PATH + SNAPSHOT_NAME_PREFIX + name;
    }

    /**
     * Remove the snapshot.
     *
     * @param  name  the name.
     *
     * @throws  SnapshotServiceException  if unable to remove the snapshot.
     */
    @Override
    public void removeSnapshot(String name)
            throws SnapshotServiceException {

        try {

            // Check if the snapshot exist.
            if (snapshotSystem.nodeExists(getSnapshotPath(name))) {

                // Remove the snapshot.
                snapshotSystem.node(getSnapshotPath(name)).removeNode();

                // Flush the snapshot system.
                snapshotSystem.flush();
            }
            else {
                throw new SnapshotServiceException(
                        "Unable to remove missing snapshot " + name + ".");
            }
        }
        catch(Exception e) {
            throw new SnapshotServiceException(
                    "Unable to restore a snapshot " + name + ".", e);
        }
    }

    /**
     * Rename the snapshot.
     *
     * @param  name     the name.
     * @param  newName  the new name.
     *
     * @throws  SnapshotServiceException  if unable to rename the snapshot.
     */
    @Override
    public void renameSnapshot(String name, String newName)
            throws SnapshotServiceException {

        try {

            // Check if the snapshot exist.
            if (snapshotSystem.nodeExists(getSnapshotPath(name))) {

                // Check if the new snapshot does not exist.
                if (!snapshotSystem.nodeExists(getSnapshotPath(newName))) {

                    // Copy the snapshot to the new name.
                    copyTree(snapshotSystem, getSnapshotPath(name), snapshotSystem, getSnapshotPath(newName));

                    // Remove the snapshot.
                    this.removeSnapshot(name);
                }
                else {
                    throw new SnapshotServiceException(
                            "Unable to rename snapshot " + name
                            + " to existing snapshot " + newName + ".");
                }
            }
            else {
                throw new SnapshotServiceException(
                        "Unable to rename missing snapshot " + name
                        + " to " + newName + ".");
            }
        }
        catch(Exception e) {
            throw new SnapshotServiceException(
                    "Unable to restore a snapshot " + name + ".", e);
        }
    }

    /**
     * Restore the snapshot.
     *
     * @param  name  the name.
     *
     * @throws  SnapshotServiceException  if unable to restore the snapshot.
     */
    @Override
    public void restoreSnapshot(String name)
            throws SnapshotServiceException {

        try {

            // Check if the snapshot exist.
            if (snapshotSystem.nodeExists(getSnapshotPath(name))) {

                // Loop through the root children.
                for (String childName : sourceSystem.childrenNames()) {

                    // Remove the child.
                    sourceSystem.node(childName).removeNode();
                }
                
                // Flush the source system.
                sourceSystem.flush();

                // Restore the snapshot.
                copyTree(snapshotSystem, getSnapshotPath(name), sourceSystem, ROOT_PATH);

                // Add timestamp to snapshot.
                snapshotSystem.node(getSnapshotPath(name))
                        .put(RESTORE_DATE_KEY, dateFormat.format(new Date()));
            }
            else {
                throw new SnapshotServiceException(
                        "Unable to restore missing snapshot " + name + ".");
            }
        }
        catch(Exception e) {
            throw new SnapshotServiceException(
                    "Unable to restore a snapshot " + name + ".", e);
        }
    }
}
