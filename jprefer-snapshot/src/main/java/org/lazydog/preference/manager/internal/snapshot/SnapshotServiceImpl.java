/**
 * Copyright 2009, 2010 lazydog.org.
 *
 * This file is part of JPrefer.
 *
 * JPrefer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JPrefer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Preference Manager.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.lazydog.preference.manager.internal.snapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.lazydog.jprefer.ServiceException;
import org.lazydog.preference.manager.spi.snapshot.SnapshotService;


/**
 * Snapshot service implementation.
 *
 * @author  Ron Rickard
 */
public final class SnapshotServiceImpl implements SnapshotService {

    private static final String CREATE_DATE_KEY = "create.date";
    private static final String DATE_PATTERN = "MM/dd/yyyy HH:mm:ss.SSS";
    private static final String RESTORE_DATE_KEY = "restore.date";
    private static final String ROOT_PATH = "/";
    private static final String SNAPSHOT_NAME_PREFIX = "snapshot-";
    private static final String SNAPSHOTS_PATH = "org/lazydog/preference/manager/snapshots";
    private static final DateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
    private static final Preferences snapshotSystem = Preferences.userRoot().node(SNAPSHOTS_PATH);
    private static final Preferences sourceSystem = Preferences.systemRoot();

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
     * Create the snapshot.
     *
     * @param  name  the name.
     *
     * @throws  ServiceException          if unable to create the snapshot.
     * @throws  NullPointerException      if the name is null.
     * @throws  IllegalArgumentException  if the name refers to an existing
     *                                    snapshot.
     */
    @Override
    public void createSnapshot(String name)
            throws ServiceException {

        try {

            // Check if the snapshot does not exist.
            if (!snapshotSystem.nodeExists(getSnapshotPath(name))) {

                // Create the snapshot.
                copyTree(sourceSystem, ROOT_PATH,
                        snapshotSystem, getSnapshotPath(name));

                // Add timestamp to snapshot.
                snapshotSystem.node(getSnapshotPath(name))
                        .put(CREATE_DATE_KEY, dateFormat.format(new Date()));
            }
            else {
                throw new IllegalArgumentException(
                        "The name, " + name
                        + ", refers to an existing snapshot.");
            }
        }
        catch(BackingStoreException e) {
            throw new ServiceException(
                    "Unable to create the snapshot, " + name + ".", e);
        }
    }

    /**
     * Find the snapshots.
     *
     * @return  the snapshots.
     *
     * @throws  ServiceException  if unable to find the snapshots.
     */
    @Override
    public Map<String, Date> findSnapshots()
            throws ServiceException {

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
                    Date date;
                    String name;

                    // Get the name and date.
                    name = childName.replaceFirst(SNAPSHOT_NAME_PREFIX, "");
                    date = dateFormat.parse(snapshotSystem.node(childName)
                            .get(CREATE_DATE_KEY, null));

                    // Put the name and date on the list.
                    snapshots.put(name, date);
                }
            }
        }
        catch(BackingStoreException e) {
            throw new ServiceException(
                    "Unable to find the snapshot names.", e);
        }
        catch(ParseException e) {
            throw new ServiceException(
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
     * Get the snapshot path.
     *
     * @param  name  the name.
     *
     * @return  the snapshot path.
     *
     * @throws  NullPointerException  if the name is null.
     */
    private static String getSnapshotPath(String name) {

        // Check if the name is null.
        if (name == null) {
            throw new NullPointerException("The name is null.");
        }
        
        return SNAPSHOT_NAME_PREFIX + name;
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

    /**
     * Remove the snapshot.
     *
     * @param  name  the name.
     *
     * @throws  ServiceException          if unable to remove the snapshot.
     * @throws  NullPointerException      if the name is null.
     * @throws  IllegalArgumentException  if the name does not refers to an
     *                                    existing snapshot.
     */
    @Override
    public void removeSnapshot(String name)
            throws ServiceException {

        try {

            // Check if the snapshot exist.
            if (snapshotSystem.nodeExists(getSnapshotPath(name))) {

                // Remove the snapshot.
                this.removeTree(snapshotSystem, getSnapshotPath(name));
            }
            else {
                throw new IllegalArgumentException(
                        "The name, " + name
                        + ", does not refers to an existing snapshot.");
            }
        }
        catch(BackingStoreException e) {
            throw new ServiceException(
                    "Unable to remove the snapshot, " + name + ".", e);
        }
    }

    /**
     * Rename the snapshot.
     *
     * @param  sourceName  the source name.
     * @param  targetName  the target name.
     *
     * @throws  ServiceException          if unable to rename the snapshot.
     * @throws  NullPointerException      if the source or target name are null.
     * @throws  IllegalArgumentException  if the source name does not refers to
     *                                    an existing snapshot or the target
     *                                    name refers to an existing snapshot.
     */
    @Override
    public void renameSnapshot(String sourceName, String targetName)
            throws ServiceException {

        try {

            // Check if the snapshot exist.
            if (snapshotSystem.nodeExists(getSnapshotPath(sourceName))) {

                // Check if the new snapshot does not exist.
                if (!snapshotSystem.nodeExists(getSnapshotPath(targetName))) {

                    // Copy the snapshot to the new name.
                    copyTree(snapshotSystem, getSnapshotPath(sourceName),
                            snapshotSystem, getSnapshotPath(targetName));

                    // Remove the snapshot.
                    this.removeTree(snapshotSystem, getSnapshotPath(sourceName));
                }
                else {
                    throw new IllegalArgumentException(
                            "The target name, " + targetName
                            + ", refers to an existing snapshot.");
                }
            }
            else {
                throw new IllegalArgumentException(
                        "The source name, " + sourceName
                        + ", does not refers to an existing snapshot.");
            }
        }
        catch(BackingStoreException e) {
            throw new ServiceException(
                    "Unable to rename the snapshot, " + sourceName + ".", e);
        }
    }

    /**
     * Restore the snapshot.
     *
     * @param  name  the name.
     *
     * @throws  ServiceException          if unable to restore the snapshot.
     * @throws  NullPointerException      if the name is null.
     * @throws  IllegalArgumentException  if the name does not refers to an
     *                                    existing snapshot.
     */
    @Override
    public void restoreSnapshot(String name)
            throws ServiceException {

        try {

            // Check if the snapshot exist.
            if (snapshotSystem.nodeExists(getSnapshotPath(name))) {

                // Remove the source tree.
                this.removeTree(sourceSystem, ROOT_PATH);

                // Restore the snapshot.
                copyTree(snapshotSystem, getSnapshotPath(name),
                        sourceSystem, ROOT_PATH);

                // Add timestamp to snapshot.
                snapshotSystem.node(getSnapshotPath(name))
                        .put(RESTORE_DATE_KEY, dateFormat.format(new Date()));
            }
            else {
                throw new IllegalArgumentException(
                        "The name, " + name
                        + ", does not refers to an existing snapshot.");
            }
        }
        catch(BackingStoreException e) {
            throw new ServiceException(
                    "Unable to restore the snapshot, " + name + ".", e);
        }
    }
}
