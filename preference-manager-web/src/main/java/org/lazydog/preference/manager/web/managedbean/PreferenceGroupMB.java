package org.lazydog.preference.manager.web.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;
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
        try {

            // Put the preference group on the session.
            SessionUtility.putValue(SessionKey.PREFERENCE_GROUP, Preference.getPreferenceGroup(this.id));
        }
        catch(Exception e) {}
    }

    /**
     * Process the OK button.
     *
     * @param  actionEvent  the action event.
     */
    public void processOkButton(ActionEvent actionEvent) {
System.err.println("processOkButton invoked");
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
     * Set the ID.
     *
     * @param  id  the ID.
     */
    public void setId(String id) {

        // Set the ID.
        this.id = id;
    }
}
