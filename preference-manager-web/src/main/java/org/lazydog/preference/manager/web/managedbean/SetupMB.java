package org.lazydog.preference.manager.web.managedbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import org.lazydog.preference.manager.PreferenceManager;
import org.lazydog.preference.manager.model.SetupType;


/**
 * Setup managed bean.
 * 
 * @author  Ron Rickard
 */
public class SetupMB implements Serializable {

    private static final String FAILURE = "failure";
    private static final String SUCCESS = "success";

    @EJB(mappedName="ejb/PreferenceManager", beanInterface=PreferenceManager.class)
    protected PreferenceManager preferenceManager;
    private String type;

    /**
     * Configure.
     *
     * @return  setup type or failure outcome.
     */
    public String configure() {

        // Declare.
        String outcome;

        // Set the outcome to failure.
        outcome = FAILURE;

        try {

            // Save the setup type.
            preferenceManager.saveSetupType(SetupType.valueOf(this.type));

            // Set the outcome to success.
            outcome = SUCCESS;
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }

        return outcome;
    }

    /**
     * Get the password.
     *
     * @return  the password.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Get the setup types.
     *
     * @return  the setup types.
     */
    public Map<String,Boolean> getSetupTypes() {

        // Declare.
        Map<String,Boolean> setupTypes;

        // Initialize.
        setupTypes = new HashMap<String,Boolean>();

        try {

            // Loop through the setup types in order of precedence.
            for (SetupType setupType : SetupType.values()) {

                // Check if the setup is the setup type.
                if (preferenceManager.getSetupType() == setupType) {

                    // Add the role and lower precedent roles to the roles map.
                    switch(setupType) {
                        case MANAGER:
                            setupTypes.put(SetupType.MANAGER.toString(), Boolean.TRUE);
                        case STANDALONE:
                            setupTypes.put(SetupType.STANDALONE.toString(), Boolean.TRUE);
                        case AGENT:
                            setupTypes.put(SetupType.AGENT.toString(), Boolean.TRUE);
                            break;
                    }
                    break;
                }
                else {

                    // This setup is not the setup type.
                    setupTypes.put(setupType.toString(), Boolean.FALSE);
                }
            }
        }
        catch(Exception e) {
            // TODO: handle exception.
e.printStackTrace();
        }

        return setupTypes;
    }

    /**
     * Reconfigure.
     *
     * @return  success or failure outcome.
     */
    public String reconfigure() {

        // Declare.
        String outcome;

        // Set the outcome to failure.
        outcome = FAILURE;

        try {

            // Clear the configuration.
            preferenceManager.clearConfiguration();

            // Set the outcome to success.
            outcome = SUCCESS;
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }

        return outcome;
    }

    /**
     * Set the type.
     *
     * @param  type  the type.
     */
    public void setType(String type) {
        this.type = type;
    }
}
