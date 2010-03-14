package org.lazydog.preference.manager.synchronize.service.internal;

import org.lazydog.preference.manager.preference.group.PreferenceGroup;
import org.lazydog.preference.manager.preference.group.PreferenceGroupFactory;
import org.lazydog.preference.manager.synchronize.service.LocalSynchronizeService;
import org.lazydog.preference.manager.synchronize.service.SynchronizeServiceException;


/**
 * Local synchronize service implementation.
 * 
 * @author  Ron Rickard
 */
public class LocalSynchronizeServiceImpl implements LocalSynchronizeService {

    /**
     * Export the preference group as a document.
     *
     * @param  id  the ID.
     *
     * @return  the document.
     *
     * @throws  GroupServiceException  if unable to export the preference group.
     */
    @Override
    public Object exportDocument(String id)
            throws SynchronizeServiceException {

        // Declare.
        Object document;

        // Initialize.
        document = null;

        try {

            // Declare.
            PreferenceGroup preferenceGroup;

            // Get the preference group.
            preferenceGroup = PreferenceGroupFactory.create(id);

            // Export the document.
            document = preferenceGroup.exportDocument();
        }
        catch(Exception e) {
            throw new SynchronizeServiceException(
                    "Unable to export the preference group "
                    + id + ".", e);
        }

        return document;
    }

    /**
     * Export the preference groups as a document.
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
            
            // Declare.
            PreferenceGroup preferenceGroups;

            // Get the preference group.
            preferenceGroups = PreferenceGroupFactory.create();

            // Export the document.
            document = preferenceGroups.exportDocument();
        }
        catch(Exception e) {
            throw new SynchronizeServiceException(
                    "Unable to export the preference groups.", e);
        }

        return document;
    }

    /**
     * Import the preference group as a document.
     *
     * @param  id        the ID.
     * @param  document  the preference group.
     *
     * @throws  GroupServiceException  if unable to import the preference group.
     */
    @Override
    public void importDocument(String id, Object document)
            throws SynchronizeServiceException {

        try {

            // Declare.
            PreferenceGroup preferenceGroup;

            // Get the preference group.
            preferenceGroup = PreferenceGroupFactory.create(id);

            // Import the document.
            preferenceGroup.importDocument(document);
        }
        catch(Exception e) {
            throw new SynchronizeServiceException(
                    "Unable to import the preference group " + id + ".", e);
        }
    }

    /**
     * Import the preference groups as a document.
     *
     * @param  document  the document.
     *
     * @throws  GroupServiceException  if unable to import the preference groups.
     */
    @Override
    public void importDocument(Object document)
            throws SynchronizeServiceException {

        try {

            // Declare.
            PreferenceGroup preferenceGroups;

            // Get the preference groups.
            preferenceGroups = PreferenceGroupFactory.create();

            // Import the document.
            preferenceGroups.importDocument(document);
        }
        catch(Exception e) {
            throw new SynchronizeServiceException(
                    "Unable to import the preference groups.", e);
        }
    }
}