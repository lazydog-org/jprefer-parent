package org.lazydog.preference.manager.managedbean;

import java.io.Serializable;
import java.util.prefs.Preferences;
import javax.faces.event.ActionEvent;
import org.lazydog.preference.manager.model.PreferenceNode;


/**
 * Preference managed bean.
 * 
 * @author  Ron Rickard
 */
public class PreferenceMB implements Serializable {

    private String key;
    private String absolutePath;
    private PreferenceNode[] rootNodes;
    private String value;

    /**
     * Get the absolute path.
     *
     * @return  the absolute path.
     */
    public String getAbsolutePath() {
        return this.absolutePath;
    }

    /**
     * Get the key.
     *
     * @return  the key.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Get the root nodes.
     *
     * @return  the root nodes.
     */
    public PreferenceNode[] getRootNodes() {

        if (this.rootNodes == null) {

            this.rootNodes = new PreferenceNode[1];
            this.rootNodes[0] = new PreferenceNode(PreferenceNode.ROOT_NODE_NAME);
        }

        return this.rootNodes;
    }

    /**
     * Get the value.
     *
     * @return  the value.
     */
    public String getValue() {
        return this.value;
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
        this.absolutePath = absolutePath;
    }

    /**
     * Set the key.
     *
     * @param  key  the key.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Set the value.
     *
     * @param  value  the value.
     */
    public void setValue(String value) {
        this.value = value;
    }
}
