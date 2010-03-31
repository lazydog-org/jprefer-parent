/**
 * Copyright 2009, 2010 lazydog.org.
 *
 * This file is part of JPrefer.
 *
 * JPrefer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JPrefer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JPrefer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.lazydog.jprefer.web.managedbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import org.lazydog.jprefer.model.Role;
import org.lazydog.jprefer.model.SetupType;


/**
 * User managed bean.
 * 
 * @author  Ron Rickard
 */
public class UserMB extends AbstractMB implements Serializable {

    private static final String FAILURE = "failure";
    private static final String NO_SETUP = "setup";
    private static final String SUCCESS = "success";

    private String password;
    private String username;
    
    /**
     * Authenticate.
     *
     * @return  null
     */
    public String authenticate() {

        try {

            // Declare.
            FacesContext context;
            RequestDispatcher dispatcher;
            HttpServletRequest request;
            HttpServletResponse response;

            // Dispatch to loginProxy.jsp.
            context = FacesContext.getCurrentInstance();
            request = (HttpServletRequest)context.getExternalContext().getRequest();
            request.setAttribute("j_username", this.username);
            request.setAttribute("j_password", this.password);
            response = (HttpServletResponse)context.getExternalContext().getResponse();
            dispatcher = request.getRequestDispatcher("/pages/loginProxy.jsp");
            dispatcher.forward(request, response);
        }
        catch(Exception e) {
            this.createMessage("Unable to authenticate username/password.");
        }

        return null;
    }

    /**
     * Is the user authenticated?
     *
     * @return  true if the user is authenticated, otherwise false.
     */
    public Boolean getAuthenticated() {

        // Declare.
        Boolean authenticated;

        // Set authenticated to false.
        authenticated = false;

        try {

            // Check if the user exists.
            if (FacesContext.getCurrentInstance().getExternalContext()
                    .getRemoteUser() != null) {

                // Set authenticated to true.
                authenticated = true;
            }
        }
        catch(Exception e) {
            this.createMessage("Unable to determine authentication.");
        }

        return authenticated;
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
     * Get the roles.
     * 
     * @return  the roles.
     */
    public Map<String,Boolean> getRoles() {

        // Declare.
        Map<String,Boolean> roles;

        // Initialize.
        roles = new HashMap<String,Boolean>();

        try {

            // Loop through the roles in order of precedence.
            for (Role role : Role.values()) {

                // Check if the user is in the role.
                if (FacesContext.getCurrentInstance().getExternalContext()
                        .isUserInRole(role.toString())) {

                    // Add the role and lower precedent roles to the roles map.
                    switch(role) {
                        case ADMIN:
                            roles.put(Role.ADMIN.toString(), Boolean.TRUE);
                        case OPERATOR:
                            roles.put(Role.OPERATOR.toString(), Boolean.TRUE);
                        case USER:
                            roles.put(Role.USER.toString(), Boolean.TRUE);
                            break;
                    }
                    break;
                }
                else {

                    // The user is not in the role.
                    roles.put(role.toString(), Boolean.FALSE);
                }
            }
        }
        catch(Exception e) {
            this.createMessage("Unable to get the roles.");
        }

        return roles;
    }

    /**
     * Get the user.
     * 
     * @return  the user.
     */
    public String getUser() {

        // Declare.
        String user;

        // Initialize.
        user = null;

        try {

            user = FacesContext.getCurrentInstance().getExternalContext()
                    .getRemoteUser();
        }
        catch(Exception e) {
            this.createMessage("Unable to get the user.");
        }

        return user;
    }

    /**
     * Get the username.
     *
     * @return  the username.
     */
    public String getUsername() {
        return this.username;
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
     * Login.
     *
     * @return  setup type or failure outcome.
     */
    public String login() {

        // Declare.
        String outcome;

        // Set the outcome to failure.
        outcome = FAILURE;

        try {

            // Check if there is no setup.
            if (getPreferenceManager().getSetupType() == SetupType.UNKNOWN) {

                // Set the outcome to no setup.
                outcome = NO_SETUP;
            }
            else {

                // Set the outcome to success.
                outcome = SUCCESS;
            }
        }
        catch(Exception e) {
            // Already handled.
        }

        return outcome;
    }

    /**
     * Logout.
     *
     * @return  success or failure outcome.
     */
    public String logout() {

        // Declare.
        String outcome;

        // Set the outcome to failure.
        outcome = FAILURE;

        try {

            // Declare.
            FacesContext context;
            HttpSession session;

            // Invalidate the session.
            context = FacesContext.getCurrentInstance();
            session = (HttpSession)context.getExternalContext().getSession(false);
            session.invalidate();

            // Set the outcome to success.
            outcome = SUCCESS;
        }
        catch(Exception e) {
            // Already handled.
        }
        
        return outcome;
    }

    /**
     * Set the username.
     * 
     * @param  username  the username.
     */
    public void setUsername(String username) {
        this.username = username;
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
