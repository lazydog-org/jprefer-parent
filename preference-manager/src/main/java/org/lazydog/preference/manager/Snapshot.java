package org.lazydog.preference.manager;

import java.util.Date;
import java.util.Map;
import org.lazydog.preference.manager.snapshot.service.SnapshotService;
import org.lazydog.preference.manager.snapshot.service.SnapshotServiceFactory;


/**
 * Snapshot.
 *
 * @author  Ron Rickard
 */
public class Snapshot {

    private static SnapshotService snapshotService
            = SnapshotServiceFactory.create();

    public static void createSnapshot(String name) {

        try {

            // Create the snapshot.
            snapshotService.createSnapshot(name);
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }
    }

    public static Map<String,Date> getSnapshots() {

        // Declare.
        Map<String,Date> snapshots;

        // Initialize.
        snapshots = null;

        try {

            // Find the snapshots.
            snapshots = snapshotService.findSnapshots();
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }

        return snapshots;
    }

    public static void removeSnapshot(String name) {

        try {

            // Remove the snapshot.
            snapshotService.removeSnapshot(name);
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }
    }

    public static void renameSnapshot(String name, String newName) {

        try {

            // Rename the snapshot.
            snapshotService.renameSnapshot(name, newName);
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }
    }

    public static void restoreSnapshot(String name) {

        try {

            // Restore the snapshot.
            snapshotService.restoreSnapshot(name);
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }
    }
}
