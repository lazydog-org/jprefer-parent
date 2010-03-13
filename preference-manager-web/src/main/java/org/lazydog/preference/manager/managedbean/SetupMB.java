package org.lazydog.preference.manager.managedbean;

import java.io.Serializable;
import org.lazydog.preference.manager.configuration.Configuration;
import org.lazydog.preference.configuration.model.SetupType;


/**
 * Setup managed bean.
 * 
 * @author  Ron Rickard
 */
public class SetupMB implements Serializable {

    private static final String AGENT_SETUP = "agent";
    private static final String FAILURE = "failure";
    private static final String MANAGER_SETUP = "manager";
    private static final String STANDALONE_SETUP = "standalone";

    private Boolean delete;
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
            Configuration.saveSetupType(SetupType.valueOf(type));
            
            // Check if this is an agent setup.
            if (Configuration.isAgentSetup()) {
                outcome = AGENT_SETUP;
            }

            // Check if this is a manager setup.
            else if (Configuration.isManagerSetup()) {
                outcome = MANAGER_SETUP;
            }

            // Check if this is a standalone setup.
            else if (Configuration.isStandaloneSetup()) {
                outcome = STANDALONE_SETUP;
            }
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }

        return outcome;
    }

    /**
     * Delete the existing preferences?
     *
     * @return  true to delete the existing preferences, otherwise false.
     */
    public Boolean getDelete() {
        return this.delete;
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
     * Set if the existing preferences are to be deleted.
     * 
     * @param  delete  true if the existing preferences are to be deleted, otherwise false.
     */
    public void setDelete(Boolean delete) {
        this.delete = delete;
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