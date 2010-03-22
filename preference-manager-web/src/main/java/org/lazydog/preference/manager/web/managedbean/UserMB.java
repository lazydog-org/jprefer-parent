package org.lazydog.preference.manager.web.managedbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import org.lazydog.preference.manager.PreferenceManager;


/**
 * User managed bean.
 * 
 * @author  Ron Rickard
 */
public class UserMB implements Serializable {

    // Roles ordered by precendence.
    private enum Roles {
        ADMIN,
        OPERATOR,
        USER;
    }
    private static final String AGENT_SETUP = "agent";
    private static final String FAILURE = "failure";
    private static final String MANAGER_SETUP = "manager";
    private static final String NO_SETUP = "setup";
    private static final String STANDALONE_SETUP = "standalone";
    private static final String SUCCESS = "success";

    private String password;
    @EJB(mappedName="ejb/PreferenceManager", beanInterface=PreferenceManager.class)
    protected PreferenceManager preferenceManager;
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
            request.setAttribute("j_password", password);
            response = (HttpServletResponse)context.getExternalContext().getResponse();
            dispatcher = request.getRequestDispatcher("/pages/loginProxy.jsp");
            dispatcher.forward(request, response);
        }
        catch(Exception e) {
            // TODO: handle exception.
e.printStackTrace();
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
            // TODO: handle exception.
e.printStackTrace();
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
            for (Roles role : Roles.values()) {

                // Assume the user is not in the role.
                roles.put(role.toString(), Boolean.FALSE);

                // Check if the user is in the role.
                if (FacesContext.getCurrentInstance().getExternalContext()
                        .isUserInRole(role.toString())) {

                    // Add the role and lower precedent roles to the roles map.
                    switch(role) {
                        case ADMIN:
                            roles.put(Roles.ADMIN.toString(), Boolean.TRUE);
                        case OPERATOR:
                            roles.put(Roles.OPERATOR.toString(), Boolean.TRUE);
                        case USER:
                            roles.put(Roles.USER.toString(), Boolean.TRUE);
                            break;
                    }
                    break;
                }
            }
        }
        catch(Exception e) {
            // TODO: handle exception.
e.printStackTrace();
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
            // TODO: handle exception.
e.printStackTrace();
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

            // Check if this is an agent setup.
            if (preferenceManager.isAgentSetup()) {
                outcome = AGENT_SETUP;
            }

            // Check if this is a manager setup.
            else if (preferenceManager.isManagerSetup()) {
                outcome = MANAGER_SETUP;
            }

            // Check if this is a standalone setup.
            else if (preferenceManager.isStandaloneSetup()) {
                outcome = STANDALONE_SETUP;
            }
            else {
                outcome = NO_SETUP;
            }
        }
        catch(Exception e) {
            // Ignore.
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
            // TODO: handle exception.
e.printStackTrace();
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
