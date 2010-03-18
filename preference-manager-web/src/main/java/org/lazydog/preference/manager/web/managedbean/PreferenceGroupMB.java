package org.lazydog.preference.manager.web.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;
import org.lazydog.preference.manager.model.PreferencesTree;
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
     * Get the preferences trees.
     *
     * @return  the preferences trees.
     */
    public List<PreferencesTree> getPreferencesTrees() {

        List<PreferencesTree> preferencesTrees;

        preferencesTrees = new ArrayList<PreferencesTree>();

        try {

            // Get the children of the preferences tree.
            preferencesTrees = Preference.getPreferencesTree().getChildren();
        }
        catch(Exception e) {
              // TODO: handle exception.
System.err.println("Unable to get the preferences trees.\n" + e);
        }

        return preferencesTrees;
    }

    /**
     * Process the add button.
     *
     * @param  actionEvent  the action event.
     */
    public void processAddButton(ActionEvent actionEvent) {

        try {

        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to add the preferences.\n" + e);
        }
    }

    /**
     * Process the cancel button.
     *
     * @param  actionEvent  the action event.
     */
    public void processCancelButton(ActionEvent actionEvent) {

        try {

        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to cancel the add/copy/move of the preferences.\n" + e);
        }
    }

    /**
     * Process the copy button.
     *
     * @param  actionEvent  the action event.
     */
    public void processCopyButton(ActionEvent actionEvent) {

        try {

            // Set the old path.
            this.oldPath = this.path;
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to copy the preferences " + this.path + ".\n" + e);
        }
    }

    /**
     * Process the delete button.
     *
     * @param  actionEvent  the action event.
     */
    public void processDeleteButton(ActionEvent actionEvent) {

        try {

            // Remove the preferences.
            Preference.removePreferences(this.path);
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to delete the preferences " + this.path + ".\n" + e);
        }
    }

    /**
     * Process the move button.
     *
     * @param  actionEvent  the action event.
     */
    public void processMoveButton(ActionEvent actionEvent) {

        try {

            // Set the old path.
            this.oldPath = this.path;
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to move the preferences " + this.path + ".\n" + e);
        }
    }

    /**
     * Process the OK button.
     *
     * @param  actionEvent  the action event.
     */
    public void processOkButton(ActionEvent actionEvent) {

        try {

            // Check if the old path exists.
            if (this.oldPath != null && !this.oldPath.equals("")) {

                // Move the preferences.
                Preference.movePreferences(this.oldPath, this.path);
            }
            else {

                // Add the new preference.
                Preference.savePreferences(this.path);
            }
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to add/copy/move the preferences " + this.path + ".\n" + e);
        }
    }

    /**
     * Process the reset button.
     *
     * @param  actionEvent  the action event.
     */
    public void processResetButton(ActionEvent actionEvent) {

        try {

            // Reset the path.
            this.path = this.oldPath;
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to reset the preferences " + this.oldPath + ".\n" + e);
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
