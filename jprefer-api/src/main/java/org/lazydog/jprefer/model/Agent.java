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
package org.lazydog.jprefer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.Validation;
import javax.validation.Validator;
import org.lazydog.jprefer.validation.constraints.Port;


/**
 * Agent.
 *
 * @author  Ron Rickard
 */
public class Agent implements Serializable {

    private Boolean enabled;
    private Integer id;
    @NotNull(message="JMX port is required.")
    @Port(message="JMX port is invalid.")
    private String jmxPort;
    @NotNull(message="Login is required.")
    @Size(min=1, message="Login is required.")
    private String login;
    @NotNull(message = "Password is required.")
    @Size(min=1, message="Password is required.")
    private String password;
    @NotNull(message = "Server name is required.")
    @Size(min=1, message="Server name is required.")
    private String serverName;
    private AgentStatus status = AgentStatus.DOWN;

    /**
     * Is the agent enabled?
     *
     * @return  true if the agent is enabled, otherwise false.
     */
    public Boolean getEnabled() {
        return this.enabled;
    }

    /**
     * Get the ID.
     *
     * @return  the ID.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Get the JMX port.
     *
     * @return  the JMX port.
     */
    public String getJmxPort() {
        return this.jmxPort;
    }

    /**
     * Get the login.
     *
     * @return  the login.
     */
    public String getLogin() {
        return this.login;
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
     * Get the server name.
     *
     * @return  the server name.
     */
    public String getServerName() {
        return this.serverName;
    }

    /**
     * Get the status.
     * 
     * @return  the status.
     */
    public AgentStatus getStatus() {
        return this.status;
    }

    /**
     * Set if the agent is enabled.
     * 
     * @param  enabled  true if the agent is enabled, otherwise false.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Set the ID.
     * 
     * @param  id  the ID.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Set the JMX port.
     *
     * @param  jmxPort  the JMX port.
     */
    public void setJmxPort(String jmxPort) {
        this.jmxPort = jmxPort;
    }

    /**
     * Set the login.
     *
     * @param  login  the login.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Set the password.
     *
     * @param  password  the password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the server name.
     *
     * @param  serverName  the server name.
     */
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    /**
     * Set the status.
     * 
     * @param  status  the status.
     */
    public void setStatus(AgentStatus status) {

        // Check if the status exists.
        if (status == null) {

            // Set the status to down.
            status = AgentStatus.DOWN;
        }
        this.status = status;
    }

    /**
     * Get this object as a String.
     *
     * @return  this object as a String.
     */
    @Override
    public String toString() {

        // Declare.
        StringBuffer toString;

        // Initialize.
        toString = new StringBuffer();

        toString.append("Agent [");
        toString.append("id = ").append(this.getId());
        toString.append(", enabled = ").append(this.getEnabled());
        toString.append(", jmxPort = ").append(this.getJmxPort());
        toString.append(", login = ").append(this.getLogin());
        toString.append(", password = ").append(this.getPassword());
        toString.append(", serverName = ").append(this.getServerName());
        toString.append(", status = ").append(this.getStatus().toString());
        toString.append("]");

        return toString.toString();
    }

    /**
     * Validate this object.
     *
     * @return  the list of violation messages.
     */
    public List<String> validate() {

        // Declare.
        Set<ConstraintViolation<Agent>> constraintViolations;
        Validator validator;
        List<String> violationMessages;

        // Initialize.
        violationMessages = new ArrayList<String>();

        // Get the validator.
        validator = Validation.buildDefaultValidatorFactory().getValidator();

        // Validate this object.
        constraintViolations = validator.validate(this);

        // Loop through the constraint violations.
        for (ConstraintViolation constraintViolation : constraintViolations) {

            // Add the constraint violation message to the violation messages.
            violationMessages.add(constraintViolation.getMessage());
        }

        return violationMessages;
    }
}
