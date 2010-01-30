package org.lazydog.preference.model;

/**
 * Agent.
 *
 * @author  Ron Rickard
 */
public class Agent {

    private Integer jmxPort;
    private String login;
    private String password;
    private String serverName;

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
        toString.append(", jmxPort = ").append(this.getJmxPort());
        toString.append(", login = ").append(this.getLogin());
        toString.append(", password = ").append(this.getPassword());
        toString.append(", serverName = ").append(this.getServerName());
        toString.append("]");

        return toString.toString();
    }
}
