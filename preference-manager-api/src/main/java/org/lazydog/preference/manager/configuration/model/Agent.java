package org.lazydog.preference.manager.configuration.model;


/**
 * Agent.
 *
 * @author  Ron Rickard
 */
public class Agent {

    private Boolean enabled;
    private Integer id;
    private Integer jmxPort;
    private String login;
    private String password;
    private String serverName;
    private String status;

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
    public Integer getJmxPort() {
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
    public String getStatus() {
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
    public void setJmxPort(Integer jmxPort) {
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
    public void setStatus(String status) {
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
        toString.append("]");

        return toString.toString();
    }
}
