package org.lazydog.preference.manager.web.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    private String oldKey;
    private String oldValue;
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
     * Get the old key.
     *
     * @return  the old key.
     */
    public String getOldKey() {
        return this.oldKey;
    }

    /**
     * Get the old value.
     *
     * @return  the old value.
     */
    public String getOldValue() {
        return this.oldValue;
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
                new LinkedHashMap<String,String>();

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
        preferenceKeys = new ArrayList();

        // Get the preference keys.
        preferenceKeys.addAll(this.getPreferences().keySet());

        return preferenceKeys;
    }

    /**
     * Process the delete button.
     *
     * @param  actionEvent  the action event.
     */
    public void processDeleteButton(ActionEvent actionEvent) {

        try {

            // Remove the preference.
            this.getPreferences().remove(this.key);
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to delete the preference " + this.key + ".\n" + e);
        }
    }

    /**
     * Process the modify button.
     *
     * @param  actionEvent  the action event.
     */
    public void processModifyButton(ActionEvent actionEvent) {

        try {

            // Set the value, old key, and old value from the key.
            this.value = (String)this.getPreferences().get(this.key);
            this.oldKey = this.key;
            this.oldValue = this.value;
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to modify the preference " + this.key + ".\n" + e);
        }
    }

    /**
     * Process the OK button.
     *
     * @param  actionEvent  the action event.
     */
    public void processOkButton(ActionEvent actionEvent) {

        try {

            // Check if the old key exists.
            if (this.oldKey != null && !this.oldKey.equals("")) {

                // Remove the old preference.
                this.getPreferences().remove(this.oldKey);
            }

            // Add the new preference.
            this.getPreferences().put(this.key, this.value);
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to add/modify the preference " + this.key + ".\n" + e);
        }
    }

    /**
     * Process the reset button.
     *
     * @param  actionEvent  the action event.
     */
    public void processResetButton(ActionEvent actionEvent) {

        try {

            // Reset the key and value.
            this.key = this.oldKey;
            this.value = this.oldValue;
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to reset the preference " + this.oldKey + ".\n" + e);
        }
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
     * Set the old key.
     *
     * @param  oldKey  the old key.
     */
    public void setOldKey(String oldKey) {
        this.oldKey = oldKey;
    }

    /**
     * Set the old value.
     *
     * @param  oldValue  the old value.
     */
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
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
