package org.lazydog.preference.manager.model;

/**
 * Agent.
 *
 * @author  Ron Rickard
 */
public class Agent
    extends org.lazydog.preference.model.Agent {

    private Boolean enabled;
    private Integer id;
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
