package org.lazydog.preference.manager.synchronize.service.internal;

import org.lazydog.preference.manager.preference.service.PreferenceService;
import org.lazydog.preference.manager.preference.service.PreferenceServiceFactory;
import org.lazydog.preference.manager.synchronize.service.LocalSynchronizeService;
import org.lazydog.preference.manager.synchronize.service.SynchronizeServiceException;


/**
 * Local synchronize service implementation.
 * 
 * @author  Ron Rickard
 */
public class LocalSynchronizeServiceImpl implements LocalSynchronizeService {

    private static PreferenceService preferenceService
            = PreferenceServiceFactory.create();

    /**
     * Export the preference groups to a document.
     *
     * @return  the document.
     *
     * @throws  GroupServiceException  if unable to export the preference groups.
     */
    @Override
    public Object exportDocument()
            throws SynchronizeServiceException {

        // Declare.
        Object document;

        // Initialize.
        document = null;

        try {

            // Export the preference groups to a document.
            document = preferenceService.exportDocument();
        }
        catch(Exception e) {
            throw new SynchronizeServiceException(
                    "Unable to export the preference groups.", e);
        }

        return document;
    }

    /**
     * Export the preference group to a document.
     *
     * @param  absolutePath  the absolute path.
     *
     * @return  the document.
     *
     * @throws  GroupServiceException  if unable to export the preference group.
     */
    @Override
    public Object exportDocument(String absolutePath)
            throws SynchronizeServiceException {

        // Declare.
        Object document;

        // Initialize.
        document = null;

        try {

            // Export the preference group to a document.
            document = preferenceService.exportDocument(absolutePath);
        }
        catch(Exception e) {
            throw new SynchronizeServiceException(
                    "Unable to export the preference group "
                    + absolutePath + ".", e);
        }

        return document;
    }

    /**
     * Import the preference groups from a document.
     *
     * @param  document  the document.
     *
     * @throws  GroupServiceException  if unable to import the preference groups.
     */
    @Override
    public void importDocument(Object document)
            throws SynchronizeServiceException {

        try {

            // Import the preference groups from a document.
            preferenceService.importDocument(document);
        }
        catch(Exception e) {
            throw new SynchronizeServiceException(
                    "Unable to import the preference groups.", e);
        }
    }

    /**
     * Import the preference group from a document.
     *
     * @param  absolutePath  the absolute path.
     * @param  document      the preference group.
     *
     * @throws  GroupServiceException  if unable to import the preference group.
     */
    @Override
    public void importDocument(String absolutePath, Object document)
            throws SynchronizeServiceException {

        try {

            // Import the preference group from a document.
            preferenceService.importDocument(absolutePath, document);
        }
        catch(Exception e) {
            throw new SynchronizeServiceException(
                    "Unable to import the preference group "
                    + absolutePath + ".", e);
        }
    }
}