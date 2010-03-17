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

    private String path;
    private String oldPath;
    private List<PreferenceGroupTree> preferenceGroupTrees;

    /**
     * Get the path.
     *
     * @return  the path.
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Get the old path.
     *
     * @return  the old path.
     */
    public String getOldPath() {
        return this.oldPath;
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

        try {

            // Reove the preference group from the session.
            SessionUtility.removeValue(SessionKey.PREFERENCE_GROUP);
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to cancel the add/modify of the preference group " + this.oldPath + ".\n" + e);
        }
    }

    /**
     * Process the delete button.
     *
     * @param  actionEvent  the action event.
     */
    public void processDeleteButton(ActionEvent actionEvent) {

        try {

            // Remove the preference group.
            Preference.removePreferenceGroup(this.path);
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to delete the preference group " + this.oldPath + ".\n" + e);
        }
    }

    /**
     * Process the modify button.
     *
     * @param  actionEvent  the action event.
     */
    public void processModifyButton(ActionEvent actionEvent) {

        try {

            // Put the preference group on the session.
            SessionUtility.putValue(SessionKey.PREFERENCE_GROUP, Preference.getPreferenceGroup(this.path));

            // Set the old path.
            this.oldPath = this.path;
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to modify the preference group " + this.path + ".\n" + e);
        }
    }

    /**
     * Process the OK button.
     *
     * @param  actionEvent  the action event.
     */
    public void processOkButton(ActionEvent actionEvent) {

        try {

            // Declare.
            PreferenceGroup preferenceGroup;

            // Check if the old path exists.
            if (this.oldPath != null && !this.oldPath.equals("")) {

                // Remove the old preference group.
                Preference.removePreferenceGroup(this.oldPath);
            }

            // Get the preference group from the session.
            preferenceGroup = SessionUtility.getValue(SessionKey.PREFERENCE_GROUP, PreferenceGroup.class);
            preferenceGroup.setPath(path);

            // Add the new preference.
            Preference.savePreferenceGroup(preferenceGroup);
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to add/modify the preference group " + this.path + ".\n" + e);
        }
    }

    /**
     * Process the reset button.
     *
     * @param  actionEvent  the action event.
     */
    public void processResetButton(ActionEvent actionEvent) {

        try {

            // Check if the old path exists.
            if (this.oldPath != null && !this.oldPath.equals("")) {

                // Put the old preference group on the session.
                SessionUtility.putValue(SessionKey.PREFERENCE_GROUP, Preference.getPreferenceGroup(this.oldPath));

                // Reset the absolute path.
                this.path = this.oldPath;
            }
            else {

                // Put a new preference the session.
                SessionUtility.putValue(SessionKey.PREFERENCE_GROUP, new PreferenceGroup());
            }
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to reset the preference group " + this.oldPath + ".\n" + e);
        }
    }

    /**
     * Set the path.
     *
     * @param  path  the path.
     */
    public void setPath(String path) {

        // Set the path.
        this.path = path;
    }

    /**
     * Set the old path.
     *
     * @param  oldPath  the old path.
     */
    public void setOldPath(String oldPath) {

        // Set the old absolute path.
        this.oldPath = oldPath;
    }
}
