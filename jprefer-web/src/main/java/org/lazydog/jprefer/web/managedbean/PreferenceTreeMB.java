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
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import org.lazydog.jprefer.model.Preference;
import org.lazydog.jprefer.model.PreferencesTree;


/**
 * Preference tree managed bean.
 * 
 * @author  Ron Rickard
 */
public class PreferenceTreeMB extends AbstractMB implements Serializable {

    private enum ActionType {
        ADD,
        COPY,
        MOVE;
    }
    private String actionType;
    private String oldPath;
    private String path;

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
            preferencesTrees = getJPreferManager()
                    .getPreferencesTree().getChildren();
        }
        catch(Exception e) {
            this.createMessage("Unable to get the preferences trees.");
        }

        return preferencesTrees;
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
            getJPreferManager().removePreferencePath(this.path);
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
                    getJPreferManager().savePreferencePath(this.path);
                }

                // Check if the path starts with the old path.
                else if (!this.path.startsWith(this.oldPath)) {

                    // Check if the action type is copy.
                    if (ActionType.valueOf(this.actionType) == ActionType.COPY) {

                        // Copy the preference path.
                        getJPreferManager().copyPreferencePath(this.oldPath, this.path);
                    }

                    // Check if the action type is move.
                    else if (ActionType.valueOf(this.actionType) == ActionType.MOVE) {

                        // Move the preference path.
                        getJPreferManager().movePreferencePath(this.oldPath, this.path);
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
