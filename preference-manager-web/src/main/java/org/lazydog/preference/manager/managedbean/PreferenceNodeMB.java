package org.lazydog.preference.manager.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javax.faces.event.ActionEvent;
import org.lazydog.preference.manager.model.PreferenceNode;
import org.lazydog.preference.manager.utility.SessionKey;
import org.lazydog.preference.manager.utility.SessionUtility;


/**
 * Preference node managed bean.
 * 
 * @author  Ron Rickard
 */
public class PreferenceNodeMB implements Serializable {

    private String absolutePath;
    private List<PreferenceNode> rootNodes;

    /**
     * Get the absolute path.
     *
     * @return  the absolute path.
     */
    public String getAbsolutePath() {
        return this.absolutePath;
    }

    /**
     * Get the root nodes.
     *
     * @return  the root nodes.
     */
    public List<PreferenceNode> getRootNodes() {

        // Check if there are root nodes.
        if (this.rootNodes == null) {

            // Get the root nodes.
            this.rootNodes = new ArrayList<PreferenceNode>();
            this.rootNodes.add(new PreferenceNode(PreferenceNode.ROOT_NODE_NAME));
        }

        return this.rootNodes;
    }

    /**
     * Process the add button.
     *
     * @param  actionEvent  the action event.
     */
    public void processAddButton(ActionEvent actionEvent) {
System.err.println("processAddButton invoked");
    }

    /**
     * Process the cancel button.
     *
     * @param  actionEvent  the action event.
     */
    public void processCancelButton(ActionEvent actionEvent) {
System.err.println("processCancelButton invoked");
    }

    /**
     * Process the delete button.
     *
     * @param  actionEvent  the action event.
     */
    public void processDeleteButton(ActionEvent actionEvent) {
System.err.println("processDeleteButton invoked");
    }

    /**
     * Process the modify button.
     *
     * @param  actionEvent  the action event.
     */
    public void processModifyButton(ActionEvent actionEvent) {
System.err.println("processModifyButton invoked");
    }

    /**
     * Process the OK button.
     *
     * @param  actionEvent  the action event.
     */
    public void processOkButton(ActionEvent actionEvent) {
System.err.println("processOkButton invoked");
        try {

            Preferences.userRoot().node(this.absolutePath);
            Preferences.userRoot().flush();
        }
        catch(Exception e) {}
    }

    /**
     * Process the reset button.
     *
     * @param  actionEvent  the action event.
     */
    public void processResetButton(ActionEvent actionEvent) {
System.err.println("processResetButton invoked");
    }

    /**
     * Set the absolute path.
     *
     * @param  absolutePath  the absolute path.
     */
    public void setAbsolutePath(String absolutePath) {

        // Set the absolute path.
        this.absolutePath = absolutePath;

        // Put the preference node on the session.
        SessionUtility.putValue(SessionKey.PREFERENCE_NODE, new PreferenceNode(absolutePath));
        
    }
}
