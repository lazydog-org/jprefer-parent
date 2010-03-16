package org.lazydog.preference.manager.snapshot.service;

import java.util.List;


/**
 * Snapshot service.
 *
 * @author  Ron Rickard
 */
public interface SnapshotService {

    public List<String> findSnapshotNames()
            throws SnapshotServiceException;
    public void createSnapshot(String name)
            throws SnapshotServiceException;
    public void restoreSnapshot(String name)
            throws SnapshotServiceException;
}
