package org.lazydog.preference.manager.snapshot.service;

import java.util.Date;
import java.util.Map;
import org.lazydog.preference.manager.service.ServiceException;


/**
 * Snapshot service.
 *
 * @author  Ron Rickard
 */
public interface SnapshotService {
 
    public void createSnapshot(String name)
            throws ServiceException;
    public Map<String,Date> findSnapshots()
            throws ServiceException;
    public void removeSnapshot(String name)
            throws ServiceException;
    public void renameSnapshot(String name, String newName)
            throws ServiceException;
    public void restoreSnapshot(String name)
            throws ServiceException;
}
