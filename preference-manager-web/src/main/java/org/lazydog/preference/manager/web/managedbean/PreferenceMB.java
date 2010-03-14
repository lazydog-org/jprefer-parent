package org.lazydog.preference.manager.web.managedbean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import org.lazydog.preference.manager.model.Preference;
import org.lazydog.preference.manager.model.PreferenceGroup;
import org.lazydog.preference.manager.web.utility.SessionKey;
import org.lazydog.preference.manager.web.utility.SessionUtility;


/**
 * Preference managed bean.
 * 
 * @author  Ron Rickard
 */
public class PreferenceMB implements Serializable {

    private String key;
    private Preference preference;

    /**
     * Get the key.
     *
     * @return  the key.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Get the preference.
     *
     * @return  the preference.
     */
    public Preference getPreference() {
        return this.preference;
    }

    /**
     * Get the preferences.
     *
     * @return  the preferences.
     */
    public List<Preference> getPreferences() {

        // Declare.
        List<Preference> preferences;

        // Get the preferences from the preference group on the session.
        preferences = (SessionUtility.getValue(SessionKey.PREFERENCE_GROUP, PreferenceGroup.class) != null) ?
                SessionUtility.getValue(SessionKey.PREFERENCE_GROUP, PreferenceGroup.class).getPreferences() :
                null;

        return preferences;
    }

    /**
     * Initialize.
     */
    @PostConstruct
    public void initialize() {

        // Create a new preference.
        this.preference = new Preference();
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
     * Set the key.
     *
     * @param  key  the key.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Set the preference.
     *
     * @param  preference  the preference.
     */
    public void setPreference(Preference preference) {
        this.preference = preference;
    }
}
