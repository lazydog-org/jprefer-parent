package org.lazydog.preference.manager.web.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;
import org.lazydog.preference.manager.model.PreferenceGroup;
import org.lazydog.preference.manager.model.PreferenceGroupTree;
import org.lazydog.preference.manager.Preference;
import org.lazydog.preference.manager.web.utility.SessionKey;
import org.lazydog.preference.manager.web.utility.SessionUtility;


/**
 * Preference group managed bean.
 * 
 * @author  Ron Rickard
 */
public class PreferenceGroupMB implements Serializable {

    private String id;
    private String oldId;
    private List<PreferenceGroupTree> preferenceGroupTrees;

    /**
     * Get the ID.
     *
     * @return  the ID.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Get the old ID.
     *
     * @return  the old ID.
     */
    public String getOldId() {
        return this.oldId;
    }

    /**
     * Get the preference group trees.
     *
     * @return  the preference group trees.
     */
    public List<PreferenceGroupTree> getPreferenceGroupTrees() {

        // Check if there is a preference group tree.
        if (this.preferenceGroupTrees == null) {

            // Get the preference group trees.
            this.preferenceGroupTrees = new ArrayList<PreferenceGroupTree>();
            this.preferenceGroupTrees.addAll(Preference.getPreferenceGroupTree().getChildren());
        }

        return this.preferenceGroupTrees;
    }

    /**
     * Process the add button.
     *
     * @param  actionEvent  the action event.
     */
    public void processAddButton(ActionEvent actionEvent) {
System.err.println("processAddButton invoked");
        try {

            // Put a new preference the session.
            SessionUtility.putValue(SessionKey.PREFERENCE_GROUP, new PreferenceGroup());
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to add the preference group.\n" + e);
        }
    }

    /**
     * Process the cancel button.
     *
     * @param  actionEvent  the action event.
     */
    public void processCancelButton(ActionEvent actionEvent) {
System.err.println("processCancelButton invoked");
        try {

            // Reove the preference group from the session.
            SessionUtility.removeValue(SessionKey.PREFERENCE_GROUP);
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to cancel the add/modify of the preference group " + this.oldId + ".\n" + e);
        }
    }

    /**
     * Process the delete button.
     *
     * @param  actionEvent  the action event.
     */
    public void processDeleteButton(ActionEvent actionEvent) {
System.err.println("processDeleteButton invoked");
        try {

            // Remove the preference group.
            Preference.removePreferenceGroup(this.id);
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to delete the preference group " + this.oldId + ".\n" + e);
        }
    }

    /**
     * Process the modify button.
     *
     * @param  actionEvent  the action event.
     */
    public void processModifyButton(ActionEvent actionEvent) {
System.err.println("processModifyButton invoked");
        try {

            // Put the preference group on the session.
            SessionUtility.putValue(SessionKey.PREFERENCE_GROUP, Preference.getPreferenceGroup(this.id));

            // Set the old ID.
            this.oldId = this.id;
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to modify the preference group " + this.id + ".\n" + e);
        }
    }

    /**
     * Process the OK button.
     *
     * @param  actionEvent  the action event.
     */
    public void processOkButton(ActionEvent actionEvent) {
System.err.println("processOkButton invoked");
        try {

            // Declare.
            PreferenceGroup preferenceGroup;

            // Check if the old ID exists.
            if (this.oldId != null) {

                // Remove the old preference group.
                Preference.removePreferenceGroup(this.oldId);
            }

            // Get the preference group from the session.
            preferenceGroup = SessionUtility.getValue(SessionKey.PREFERENCE_GROUP, PreferenceGroup.class);
            preferenceGroup.setId(id);

            // Add the new preference.
            //Preference.addPreferenceGroup(preferenceGroup);
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to add/modify the preference group " + this.id + ".\n" + e);
        }
    }

    /**
     * Process the reset button.
     *
     * @param  actionEvent  the action event.
     */
    public void processResetButton(ActionEvent actionEvent) {
System.err.println("processResetButton invoked");
        try {

            // Check if the old ID exists.
            if (this.oldId != null && !this.oldId.equals("")) {

                // Put the old preference group on the session.
                SessionUtility.putValue(SessionKey.PREFERENCE_GROUP, Preference.getPreferenceGroup(this.oldId));

                // Reset the preference group ID.
                this.id = this.oldId;
            }
            else {

                // Put a new preference the session.
                SessionUtility.putValue(SessionKey.PREFERENCE_GROUP, new PreferenceGroup());
            }
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to reset the preference group " + this.oldId + ".\n" + e);
        }
    }

    /**
     * Set the ID.
     *
     * @param  id  the ID.
     */
    public void setId(String id) {

        // Set the ID.
        this.id = id;
    }

    /**
     * Set the old ID.
     *
     * @param  oldId  the old ID.
     */
    public void setOldId(String oldId) {

        // Set the old ID.
        this.oldId = oldId;
    }
}
