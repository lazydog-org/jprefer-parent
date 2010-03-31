/**
 * Copyright 2009, 2010 lazydog.org.
 *
 * This file is part of JPrefer.
 *
 * JPrefer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JPrefer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JPrefer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.lazydog.jprefer.web.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;


/**
 * Snapshot managed bean.
 * 
 * @author  Ron Rickard
 */
public class SnapshotMB extends AbstractMB implements Serializable {

    private String name;
    private String oldName;

    /**
     * Get the name.
     *
     * @return  the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the old name.
     *
     * @return  the old name.
     */
    public String getOldName() {
        return this.oldName;
    }

    /**
     * Get the snapshots.
     *
     * @return  the snapshots.
     */
    public Map<String,Date> getSnapshots() {

        // Declare.
        Map<String,Date> snapshots;

        // Initialize.
        snapshots = new LinkedHashMap<String,Date>();

        try {

            // Get the snapshots.
            snapshots = getJPreferManager().getSnapshots();
        }
        catch(Exception e) {
            this.createMessage("Unable to get the snapshots.");
        }

        return snapshots;
    }

    /**
     * Get the snapshot keys.
     *
     * @return  the snapshot keys.
     */
    public List<String> getSnapshotKeys() {

        // Declare.
        List<String> snapshotKeys;

        // Initialize.
        snapshotKeys = new ArrayList<String>();

        // Get the snapshot keys.
        snapshotKeys.addAll(this.getSnapshots().keySet());

        return snapshotKeys;
    }

    /**
     * Initialize.
     */
    @PostConstruct
    public void initialize() {

        // Set message available to false.
        this.setMessageAvailable(Boolean.FALSE);
    }

    /**
     * Process the delete button.
     *
     * @param  actionEvent  the action event.
     */
    public void processDeleteButton(ActionEvent actionEvent) {

        try {

            // Remove the snapshot.
            getJPreferManager().removeSnapshot(this.name);
        }
        catch(Exception e) {
            this.createMessage("Unable to delete the snapshot.");
        }
    }

    /**
     * Process the OK button.
     *
     * @param  actionEvent  the action event.
     */
    public void processOkButton(ActionEvent actionEvent) {

        try {

            // Validate the snapshot.
            if (this.name != null && this.name.length() > 0) {

                // Check if the old name exists.
                if (this.oldName != null && !this.oldName.equals("")) {

                    // Rename the snapshot.
                    getJPreferManager().renameSnapshot(oldName, name);
                }
                else {

                    // Create the snapshot.
                    getJPreferManager().createSnapshot(this.name);
                }
            }
            else {
                this.createMessage("Name is required.");
            }
        }
        catch(Exception e) {

            // Check if this is a new snapshot.
            if (this.oldName == null || this.oldName.equals("")) {
                this.createMessage("Unable to create the snapshot.");
            }
            else {
                this.createMessage("Unable to rename the snapshot.");
            }
        }
    }

    /**
     * Process the rename button.
     *
     * @param  actionEvent  the action event.
     */
    public void processRenameButton(ActionEvent actionEvent) {

        try {

            // Set the old name from the name.
            this.oldName = this.name;
        }
        catch(Exception e) {
            this.createMessage("Unable to rename the snapshot.");
        }
    }

    /**
     * Process the reset button.
     *
     * @param  actionEvent  the action event.
     */
    public void processResetButton(ActionEvent actionEvent) {

        try {

            // Reset the name.
            this.name = this.oldName;
        }
        catch(Exception e) {
            this.createMessage("Unable to reset.");
        }
    }

    /**
     * Process the restore button.
     *
     * @param  actionEvent  the action event.
     */
    public void processRestoreButton(ActionEvent actionEvent) {

        try {

            // Restore the snapshot.
            getJPreferManager().restoreSnapshot(this.name);
        }
        catch(Exception e) {
            this.createMessage("Unable to restore the snapshot.");
        }
    }

    /**
     * Set the name.
     *
     * @param  name  the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the old name.
     *
     * @param  oldName  the old name.
     */
    public void setOldName(String oldName) {
        this.oldName = oldName;
    }
}
