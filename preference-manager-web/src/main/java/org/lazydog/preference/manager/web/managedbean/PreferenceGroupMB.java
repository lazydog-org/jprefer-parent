package org.lazydog.preference.manager.web.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;
import org.lazydog.preference.manager.preference.group.PreferenceGroup;
import org.lazydog.preference.manager.preference.group.PreferenceGroupFactory;
import org.lazydog.preference.manager.web.utility.SessionKey;
import org.lazydog.preference.manager.web.utility.SessionUtility;


/**
 * Preference group managed bean.
 * 
 * @author  Ron Rickard
 */
public class PreferenceGroupMB implements Serializable {

    private String id;
    private List<PreferenceGroup> rootGroups;

    /**
     * Get the ID.
     *
     * @return  the ID.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Get the root groups.
     *
     * @return  the root groups.
     */
    public List<PreferenceGroup> getRootGroups() {

        // Check if there are root groups.
        if (this.rootGroups == null) {

            // Get the root groups.
            this.rootGroups = new ArrayList<PreferenceGroup>();
            this.rootGroups.add(PreferenceGroupFactory.create());
        }

        return this.rootGroups;
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

            PreferenceGroupFactory.create(this.id);
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
     * Set the ID.
     *
     * @param  id  the ID.
     */
    public void setId(String id) {

        // Set the ID.
        this.id = id;

        // Put the preference group on the session.
        SessionUtility.putValue(SessionKey.PREFERENCE_GROUP, PreferenceGroupFactory.create(id));
        
    }
}
