package org.lazydog.preference.manager.web.managedbean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import org.lazydog.preference.manager.Configuration;
import org.lazydog.preference.manager.model.Agent;


/**
 * Agent managed bean.
 *
 * @author  Ron Rickard
 */
public class AgentMB implements Serializable {

    private Agent agent;
    private Integer agentId;
    
    /**
     * Get the agent.
     * 
     * @return  the agent.
     */
    public Agent getAgent() {
        return this.agent;
    }

    /**
     * Get the agents.
     * 
     * @return  the agents.
     */
    public List<Agent> getAgents() {

        // Declare.
        List<Agent> agents;

        // Initialize.
        agents = null;
        
        try {

            // Get the agents.
            agents = Configuration.getAgents();
        }
        catch(Exception e) {
            // TO DO: handle exception.
        }

        return agents;
    }

    /**
     * Initialize.
     */
    @PostConstruct
    public void initialize() {

        // Create a new agent.
        this.agent = new Agent();
    }

    /**
     * Process the add button.
     *
     * @param  actionEvent  the action event.
     */
    public void processAddButton(ActionEvent actionEvent) {
System.err.println("processAddButton invoked");
        this.agent = new Agent();
    }

    /**
     * Process the cancel button.
     *
     * @param  actionEvent  the action event.
     */
    public void processCancelButton(ActionEvent actionEvent) {
System.err.println("processCancelButton invoked");
    }

    /**
     * Process the delete button.
     *
     * @param  actionEvent  the action event.
     */
    public void processDeleteButton(ActionEvent actionEvent) {
System.err.println("processDeleteButton invoked");
    }

    /**
     * Process the disable button.
     *
     * @param  actionEvent  the action event.
     */
    public void processDisableButton(ActionEvent actionEvent) {
System.err.println("processDisableButton invoked");

        try {
            this.agent = Configuration.disableAgent(this.agentId);
        }
        catch(Exception e) {}
    }

    /**
     * Process the enable button.
     *
     * @param  actionEvent  the action event.
     */
    public void processEnableButton(ActionEvent actionEvent) {
System.err.println("processEnableButton invoked");
        try {
            this.agent = Configuration.enableAgent(this.agentId);
        }
        catch(Exception e) {}
    }

    /**
     * Process the modify button.
     *
     * @param  actionEvent  the action event.
     */
    public void processModifyButton(ActionEvent actionEvent) {
System.err.println("processModifyButton invoked");
        try {
            this.agent = Configuration.getAgent(this.agentId);
        }
        catch(Exception e) {}
    }

    /**
     * Process the OK button.
     *
     * @param  actionEvent  the action event.
     */
    public void processOkButton(ActionEvent actionEvent) {
System.err.println("processOkButton invoked");
        try {
            // Save the agent.
            this.agent = Configuration.saveAgent(this.agent);
        }
        catch(Exception e) {}
    }

    /**
     * Process the refresh button.
     *
     * @param  actionEvent  the action event.
     */
    public void processRefreshButton(ActionEvent actionEvent) {
System.err.println("processRefreshButton invoked");
    }

    /**
     * Process the reset button.
     *
     * @param  actionEvent  the action event.
     */
    public void processResetButton(ActionEvent actionEvent) {
System.err.println("processResetButton invoked");
        try {
            
            // Check if the agent is not new.
            if (this.agent.getId() != null) {

                // Restore the agent to the saved agent.
                this.agent = Configuration.getAgent(this.agent.getId());
            }
            else {

                // Create a new agent.
                this.agent = new Agent();
            }
        }
        catch(Exception e) {}
    }

    /**
     * Process the synchronize button.
     *
     * @param  actionEvent  the action event.
     */
    public void processSynchronizeButton(ActionEvent actionEvent) {
System.err.println("processSynchronizeButton invoked");
    }

    /**
     * Process the synchronize all button.
     *
     * @param  actionEvent  the action event.
     */
    public void processSynchronizeAllButton(ActionEvent actionEvent) {
System.err.println("processSynchronizeAllButton invoked");
    }

    /**
     * Set the agent.
     *
     * @param  agent  the agent.
     */
    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    /**
     * Set the agent ID.
     *
     * @param  agentId  the agent ID.
     */
    public void setAgentId(Integer agentId) {
System.err.println("setting to " + agentId);
        this.agentId = agentId;
    }
}
