package org.lazydog.preference.manager.snapshot.service.internal;

import java.util.ArrayList;
import java.util.List;
import org.lazydog.preference.manager.snapshot.service.SnapshotService;
import org.lazydog.preference.manager.snapshot.service.SnapshotServiceException;


/**
 * Snapshot service implementation.
 *
 * @author  Ron Rickard
 */
public class SnapshotServiceImpl implements SnapshotService {

    public List<String> findSnapshotNames()
            throws SnapshotServiceException {

        // Declare.
        List<String> snapshotNames;

        // Initialize.
        snapshotNames = new ArrayList<String>();

        return snapshotNames;
    }

    public void createSnapshot(String name)
            throws SnapshotServiceException {

    }

    public void restoreSnapshot(String name)
            throws SnapshotServiceException {

    }
}
