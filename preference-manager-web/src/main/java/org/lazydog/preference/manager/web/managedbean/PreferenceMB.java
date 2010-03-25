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
public class PreferenceMB extends AbstractMB implements Serializable {

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

        // Set message available to false.
        this.setMessageAvailable(Boolean.FALSE);
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
            this.createMessage("Unable to delete the preference .");
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
            this.createMessage("Unable to modify the preference.");
        }
    }

    /**
     * Process the OK button.
     *
     * @param  actionEvent  the action event.
     */
    public void processOkButton(ActionEvent actionEvent) {

        try {

            // Validate the preference.
            if (this.preference.validate().size() == 0) {

                // Check if the old key exists.
                if (this.oldKey != null && !this.oldKey.equals("")) {

                    // Remove the old preference.
                    preferenceManager.removePreference(this.preference.getPath(), this.oldKey);
                }

                // Add the new preference.
                preferenceManager.savePreference(this.preference);
            }
            else {

                // Loop through the violation messages.
                for (String violationMessage : this.preference.validate()) {
                    this.createMessage(violationMessage);
                }
            }
        }
        catch(Exception e) {

            // Check if this is a new preference.
            if (this.oldKey == null || this.oldKey.equals("")) {
                this.createMessage("Unable to add the preference.");
            }
            else {
                this.createMessage("Unable to modify the preference.");
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

            // Reset the key and value.
            this.preference.setKey(this.oldKey);
            this.preference.setValue(this.oldValue);
        }
        catch(Exception e) {
            this.createMessage("Unable to reset.");
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
