package org.lazydog.preference.manager.synchronize.service.internal;

import org.lazydog.preference.manager.preference.service.PreferenceService;
import org.lazydog.preference.manager.preference.service.PreferenceServiceFactory;
import org.lazydog.preference.manager.service.ServiceException;
import org.lazydog.preference.manager.synchronize.service.LocalSynchronizeService;



/**
 * Local synchronize service implementation.
 * 
 * @author  Ron Rickard
 */
public class LocalSynchronizeServiceImpl implements LocalSynchronizeService {

    private static PreferenceService preferenceService
            = PreferenceServiceFactory.create();

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
        return preferenceService.exportDocument();
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
        return preferenceService.exportDocument(path);
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
        preferenceService.importDocument(document);
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
        preferenceService.importDocument(path, document);
    }
}