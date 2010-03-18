package org.lazydog.preference.manager;

import java.util.Date;
import java.util.Map;
import org.lazydog.preference.manager.service.ServiceException;
import org.lazydog.preference.manager.snapshot.service.SnapshotService;
import org.lazydog.preference.manager.snapshot.service.SnapshotServiceFactory;
import org.lazydog.preference.manager.synchronize.service.SynchronizeService;
import org.lazydog.preference.manager.synchronize.service.SynchronizeServiceFactory;


/**
 * Snapshot.
 *
 * @author  Ron Rickard
 */
public class Snapshot {

    private static SnapshotService snapshotService
            = SnapshotServiceFactory.create();

    public static void createSnapshot(String name) 
            throws ServiceException {
            snapshotService.createSnapshot(name);
    }

    public static Map<String,Date> getSnapshots() 
            throws ServiceException {
        return snapshotService.findSnapshots();
    }

    public static void removeSnapshot(String name) 
            throws ServiceException {
        snapshotService.removeSnapshot(name);
    }

    public static void renameSnapshot(String name, String newName) 
            throws ServiceException {
        snapshotService.renameSnapshot(name, newName);
    }

    public static void restoreSnapshot(String name) 
            throws ServiceException {

        // Restore the snapshot.
        snapshotService.restoreSnapshot(name);

        // Synchronize the agents.
        Configuration.synchronizeAgents();
    }
}
