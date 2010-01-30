package org.lazydog.preference.manager.managedbean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;


/**
 * User managed bean.
 * 
 * @author  Ron Rickard
 */
public class UserMB implements Serializable {

    private static final String FAILURE = "failure";
    private static final String SUCCESS = "success";

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
     * @return  success or failure outcome.
     */
    public String login() {

        // Declare.
        String outcome;

        // Set the outcome to failure.
        outcome = FAILURE;

        if (this.authenticated) {

            // Set the outcome to success.
            outcome = SUCCESS;
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
