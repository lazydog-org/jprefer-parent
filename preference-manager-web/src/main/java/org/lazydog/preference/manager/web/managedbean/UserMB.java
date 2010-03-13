package org.lazydog.preference.manager.web.managedbean;

import java.io.Serializable;
import javax.faces.event.ActionEvent;
import org.lazydog.preference.manager.configuration.Configuration;


/**
 * User managed bean.
 * 
 * @author  Ron Rickard
 */
public class UserMB implements Serializable {

    private static final String AGENT_SETUP = "agent";
    private static final String FAILURE = "failure";
    private static final String MANAGER_SETUP = "manager";
    private static final String NO_SETUP = "setup";
    private static final String STANDALONE_SETUP = "standalone";

    private boolean authenticated;
    private String name;
    private String password;

    /**
     * Authenticate.
     *
     * @param  actionEvent  action event.
     *
     * @return  success or failure outcome.
     */
    public void authenticate(ActionEvent actionEvent) {

        // TODO: code to authenticate the user.

        // Flag that the user is authenticated.
        this.authenticated = true;
    }

    /**
     * Get the name.
     *
     * @return  the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the password.
     *
     * @return  the password.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Login.
     *
     * @return  setup type or failure outcome.
     */
    public String login() {

        // Declare.
        String outcome;

        // Set the outcome to failure.
        outcome = FAILURE;

        // Check if the user is authenticated.
        if (this.authenticated) {

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
            else {
                outcome = NO_SETUP;
            }
        }
        else {

            // Set the outcome to failure.
            outcome = FAILURE;
        }

        return outcome;
    }

    /**
     * Set the name.
     * 
     * @param  name  the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the password.
     *
     * @param  password  the password.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
