package org.lazydog.preference.manager.synchronize.service;

import org.lazydog.preference.manager.service.ServiceException;


/**
 * Agent synchronize service.
 * 
 * @author  Ron Rickard
 */
public class AgentSynchronizeService
        implements AgentSynchronizeServiceMBean {

    private static SynchronizeService synchronizeService
            = SynchronizeServiceFactory.create();

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
        return synchronizeService.exportDocument();
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
        return synchronizeService.exportDocument(path);
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
        synchronizeService.importDocument(document);
    }

    /**
     * Import the preferences from a document.
     *
     * @param  path      the path.
     * @param  document  the document.
     *
     * @throws  GroupServiceException  if unable to import the preferences.
     */
    @Override
    public void importDocument(String path, String document)
            throws ServiceException {
        synchronizeService.importDocument(path, document);
    }
}