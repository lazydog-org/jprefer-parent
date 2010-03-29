package org.lazydog.preference.manager.web.managedbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.lazydog.preference.manager.model.SetupType;


/**
 * Setup managed bean.
 * 
 * @author  Ron Rickard
 */
public class SetupMB extends AbstractMB implements Serializable {

    private static final String FAILURE = "failure";
    private static final String SUCCESS = "success";

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
            getPreferenceManager().saveSetupType(SetupType.valueOf(this.type));

            // Set the outcome to success.
            outcome = SUCCESS;
        }
        catch(Exception e) {
            this.createMessage("Unable to configure the server.");
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
     * Is the server setup as an agent?
     * 
     * @return  true if the server is setup as an agent, otherwise false.
     */
    public Boolean getAgentSetupType() {

        // Declare.
        Boolean agentSetupType;

        // Initialize.
        agentSetupType = Boolean.FALSE;

        try {

                // Check if the setup is the agent setup type.
                if (getPreferenceManager().getSetupType() == SetupType.AGENT) {
                    agentSetupType = Boolean.TRUE;
                }
        }
        catch(Exception e) {
            this.createMessage("Unable to get the agent setup type.");
        }

        return agentSetupType;
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
                if (getPreferenceManager().getSetupType() == setupType) {

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
            this.createMessage("Unable to get the setup types.");
        }

        return setupTypes;
    }

    /**
     * Initialize.
     */
    @PostConstruct
    public void initialize() {

        // Set message available to false.
        this.setMessageAvailable(Boolean.FALSE);
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
            getPreferenceManager().clearConfiguration();

            // Set the outcome to success.
            outcome = SUCCESS;
        }
        catch(Exception e) {
            this.createMessage("Unable to reconfigure the server.");
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
