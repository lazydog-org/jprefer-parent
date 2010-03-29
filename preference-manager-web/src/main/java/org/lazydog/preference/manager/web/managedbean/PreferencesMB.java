package org.lazydog.preference.manager.web.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import org.lazydog.preference.manager.model.Preference;
import org.lazydog.preference.manager.model.PreferencesTree;


/**
 * Preferences managed bean.
 * 
 * @author  Ron Rickard
 */
public class PreferencesMB extends AbstractMB implements Serializable {

    private enum ActionType {
        ADD,
        COPY,
        MOVE;
    }
    private String actionType;
    private String oldPath;
    private String path;
    private List<PreferencesTree> preferencesTrees;

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

        // Check if the preferences trees do not exist.
        if (this.preferencesTrees == null) {

            // Initialize.
            this.preferencesTrees = new ArrayList<PreferencesTree>();

            try {

                // Get the children of the preferences tree.
                this.preferencesTrees = getPreferenceManager()
                        .getPreferencesTree().getChildren();
            }
            catch(Exception e) {
                this.createMessage("Unable to get the preferences trees.");
            }
        }

        return this.preferencesTrees;
    }

    /**
     * Initialize.
     */
    @PostConstruct
    public void initialize() {

        // Set the action type.
        this.actionType = ActionType.ADD.toString();

        // Set message available to false.
        this.setMessageAvailable(Boolean.FALSE);
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
            this.createMessage("Unable to copy the preference path.");
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
            getPreferenceManager().removePreferencePath(this.path);
        }
        catch(Exception e) {
            this.createMessage("Unable to delete the preference path.");
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
            this.createMessage("Unable to move the preference path.");
        }
    }

    /**
     * Process the OK button.
     *
     * @param  actionEvent  the action event.
     */
    public void processOkButton(ActionEvent actionEvent) {

        try {

            // Validate the agent.
            if (Preference.validatePath(this.path).size() == 0) {

                // Check if the action type is add.
                if (ActionType.valueOf(this.actionType) == ActionType.ADD) {

                    // Add the preference path.
                    getPreferenceManager().savePreferencePath(this.path);
                }

                // Check if the path starts with the old path.
                else if (!this.path.startsWith(this.oldPath)) {

                    // Check if the action type is copy.
                    if (ActionType.valueOf(this.actionType) == ActionType.COPY) {

                        // Copy the preference path.
                        getPreferenceManager().copyPreferencePath(this.oldPath, this.path);
                    }

                    // Check if the action type is move.
                    else if (ActionType.valueOf(this.actionType) == ActionType.MOVE) {

                        // Move the preference path.
                        getPreferenceManager().movePreferencePath(this.oldPath, this.path);
                    }
                }
                else {
                    this.createMessage("New path cannot start with the old path.");
                }
            }
            else {

                // Loop through the violation messages.
                for (String violationMessage : Preference.validatePath(this.path)) {
                    this.createMessage(violationMessage);
                }
            }
        }
        catch(Exception e) {

            // Check if the action type is add.
            if (ActionType.valueOf(this.actionType) == ActionType.ADD) {
                this.createMessage("Unable to add the preference path.");
            }

            // Check if the action type is copy.
            else if (ActionType.valueOf(this.actionType) == ActionType.COPY) {
                this.createMessage("Unable to copy the preference path.");
            }

            // Check if the action type is move.
            else if (ActionType.valueOf(this.actionType) == ActionType.MOVE) {
                this.createMessage("Unable to move the preference path.");
            }
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
            this.createMessage("Unable to reset.");
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
