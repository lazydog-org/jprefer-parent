package org.lazydog.preference.manager.spi.synchronize;

import org.lazydog.preference.manager.ServiceException;

/**
 * Synchronize service.
 *
 * @author  Ron Rickard
 */
public interface SynchronizeService {

    public String exportDocument()
            throws ServiceException;
    public String exportDocument(String path)
            throws ServiceException;
    public void importDocument(String document)
            throws ServiceException;
    public void importDocument(String path, String document)
            throws ServiceException;
}
