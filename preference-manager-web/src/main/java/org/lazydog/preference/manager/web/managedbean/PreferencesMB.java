package org.lazydog.preference.manager.web.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import org.lazydog.preference.manager.model.PreferencesTree;
import org.lazydog.preference.manager.PreferenceManager;


/**
 * Preferences managed bean.
 * 
 * @author  Ron Rickard
 */
public class PreferencesMB implements Serializable {

    private enum ActionType {
        ADD,
        COPY,
        MOVE;
    }
    private String actionType;
    private String oldPath;
    private String path;
    @EJB(mappedName="ejb/PreferenceManager", beanInterface=PreferenceManager.class)
    protected PreferenceManager preferenceManager;

    /**
     * Get the action type.
     *
     * @return  the action type.
     */
    public String getActionType() {
        return this.actionType;
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
     * Get the path.
     *
     * @return  the path.
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Get the preferences trees.
     *
     * @return  the preferences trees.
     */
    public List<PreferencesTree> getPreferencesTrees() {

        // Declare.
        List<PreferencesTree> preferencesTrees;

        // Initialize.
        preferencesTrees = new ArrayList<PreferencesTree>();

        try {

            // Get the children of the preferences tree.
            preferencesTrees = preferenceManager.getPreferencesTree().getChildren();
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

            // Set the action type.
            this.actionType = ActionType.ADD.toString();
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to add the preferences " + this.path + ".\n" + e);
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

            // Set the action type.
            this.actionType = ActionType.COPY.toString();
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

            // Remove the preference path.
            preferenceManager.removePreferencePath(this.path);
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to delete the preference path " + this.path + ".\n" + e);
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

            // Set the action type.
            this.actionType = ActionType.MOVE.toString();
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

            // Check if the action type is add.
            if (ActionType.valueOf(this.actionType) == ActionType.ADD) {

                // Add the preference path.
                preferenceManager.savePreferencePath(this.path);
            }
            // Check if the action type is copy.
            else if (ActionType.valueOf(this.actionType) == ActionType.COPY) {

                // Copy the preference path.
                preferenceManager.copyPreferencePath(this.oldPath, this.path);
            }

            // Check if the action type is move.
            else if (ActionType.valueOf(this.actionType) == ActionType.MOVE) {

                // Move the preference path.
                preferenceManager.movePreferencePath(this.oldPath, this.path);
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
     * Set the action type.
     *
     * @param  actionType  the action type.
     */
    public void setActionType(String actionType) {

        // Set the action type.
        this.actionType = actionType;
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

    /**
     * Set the path.
     *
     * @param  path  the path.
     */
    public void setPath(String path) {

        // Set the path.
        this.path = path;
    }
}
