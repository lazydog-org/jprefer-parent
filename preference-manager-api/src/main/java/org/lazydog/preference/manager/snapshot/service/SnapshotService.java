package org.lazydog.preference.manager.snapshot.service;

import java.util.Date;
import java.util.Map;


/**
 * Snapshot service.
 *
 * @author  Ron Rickard
 */
public interface SnapshotService {
 
    public void createSnapshot(String name)
            throws SnapshotServiceException;
    public Map<String,Date> findSnapshots()
            throws SnapshotServiceException;
    public void removeSnapshot(String name)
            throws SnapshotServiceException;
    public void renameSnapshot(String name, String newName)
            throws SnapshotServiceException;
    public void restoreSnapshot(String name)
            throws SnapshotServiceException;
}
