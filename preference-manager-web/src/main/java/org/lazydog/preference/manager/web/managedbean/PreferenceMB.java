package org.lazydog.preference.manager.web.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.event.ActionEvent;
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
    private String value;

    /**
     * Get the key.
     *
     * @return  the key.
     */
    public String getKey() {
        return this.key;
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
     * Get the preferences.
     *
     * @return  the preferences.
     */
    public Map<String,String> getPreferences() {

        // Declare.
        Map<String,String> preferences;

        // Get the preferences from the preference group on the session.
        preferences = (SessionUtility.getValue(SessionKey.PREFERENCE_GROUP, PreferenceGroup.class) != null) ?
                SessionUtility.getValue(SessionKey.PREFERENCE_GROUP, PreferenceGroup.class).getPreferences() :
                null;

        return preferences;
    }

    /**
     * Get the preference keys.
     *
     * @return  the preference keys.
     */
    public List<String> getPreferenceKeys() {

        // Declare.
        List<String> preferenceKeys;

        // Initialize.
        preferenceKeys = null;

        // Check if there are preferences.
        if (this.getPreferences() != null) {

            // Get the preference keys.
            preferenceKeys = new ArrayList();
            preferenceKeys.addAll(this.getPreferences().keySet());
        }

        return preferenceKeys;
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
System.err.println("key = " + this.key);
            // Get the value.
            this.value = (String)this.getPreferences().get(this.key);
System.err.println("value = " + this.value);
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
