package org.lazydog.preference.manager.web.managedbean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import org.lazydog.preference.manager.model.Preference;
import org.lazydog.preference.manager.PreferenceManager;


/**
 * Preference managed bean.
 * 
 * @author  Ron Rickard
 */
public class PreferenceMB implements Serializable {

    private String oldKey;
    private String oldValue;
    private Preference preference;
    @EJB(mappedName="ejb/PreferenceManager", beanInterface=PreferenceManager.class)
    protected PreferenceManager preferenceManager;

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
     * Get the preference.
     *
     * @return  the key.
     */
    public Preference getPreference() {
        return this.preference;
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
     * Process the delete button.
     *
     * @param  actionEvent  the action event.
     */
    public void processDeleteButton(ActionEvent actionEvent) {

        try {

            // Remove the preference.
            preferenceManager.removePreference(this.preference.getPath(), this.preference.getKey());
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to delete the preference " + this.preference + ".\n" + e);
        }
    }

    /**
     * Process the modify button.
     *
     * @param  actionEvent  the action event.
     */
    public void processModifyButton(ActionEvent actionEvent) {

        try {

            // Set the old key, and old value.
            this.oldKey = this.preference.getKey();
            this.oldValue = this.preference.getValue();
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to modify the preference " + this.preference.getKey() + ".\n" + e);
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
                preferenceManager.removePreference(this.preference.getPath(), this.oldKey);
            }

            // Add the new preference.
            preferenceManager.savePreference(this.preference);
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to add/modify the preference " + this.preference + ".\n" + e);
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
            this.preference.setKey(this.oldKey);
            this.preference.setValue(this.oldValue);
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to reset the preference " + this.oldKey + ".\n" + e);
        }
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
     * Set the preference.
     *
     * @param  preference  the preference.
     */
    public void setPreference(Preference preference) {
        this.preference = preference;
    }
}
