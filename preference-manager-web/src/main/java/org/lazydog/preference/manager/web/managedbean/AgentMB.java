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
    private Integer id;
    
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
System.err.println("Unable to get the agents.\n" + e);
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
     * Process the delete button.
     *
     * @param  actionEvent  the action event.
     */
    public void processDeleteButton(ActionEvent actionEvent) {

        try {

            // Remove the agent.
            Configuration.removeAgent(this.id);
        }
        catch(Exception e) {
            // TO DO: handle exception.
System.err.println("Unable to delete the agent, " + this.id + ".\n" + e);
        };
    }

    /**
     * Process the disable button.
     *
     * @param  actionEvent  the action event.
     */
    public void processDisableButton(ActionEvent actionEvent) {

        try {

            // Disable the agent.
            this.agent = Configuration.disableAgent(this.id);
        }
        catch(Exception e) {
            // TO DO: handle exception.
System.err.println("Unable to disable the agent, " + this.id + ".\n" + e);
        }
    }

    /**
     * Process the enable button.
     *
     * @param  actionEvent  the action event.
     */
    public void processEnableButton(ActionEvent actionEvent) {

        try {

            // Enable the agent.
            this.agent = Configuration.enableAgent(this.id);
        }
        catch(Exception e) {
            // TO DO: handle exception.
System.err.println("Unable to enable the agent, " + this.id + ".\n" + e);
        }
    }

    /**
     * Process the modify button.
     *
     * @param  actionEvent  the action event.
     */
    public void processModifyButton(ActionEvent actionEvent) {

        try {

            // Get the agent.
            this.agent = Configuration.getAgent(this.id);
        }
        catch(Exception e) {
            // TO DO: handle exception.
System.err.println("Unable to modify the agent, " + this.id + ".\n" + e);
        }
    }

    /**
     * Process the OK button.
     *
     * @param  actionEvent  the action event.
     */
    public void processOkButton(ActionEvent actionEvent) {

        try {

            // Save the agent.
            this.agent = Configuration.saveAgent(this.agent);
        }
        catch(Exception e) {
            // TO DO: handle exception.
System.err.println("Unable to add/modify the agent.\n" + e);
        }
    }

    /**
     * Process the reset button.
     *
     * @param  actionEvent  the action event.
     */
    public void processResetButton(ActionEvent actionEvent) {

        try {
            
            // Check if the agent exists already.
            if (this.agent.getId() != null) {

                // Reset the agent.
                this.agent = Configuration.getAgent(this.agent.getId());
            }
            else {

                // Create a new agent.
                this.agent = new Agent();
            }
        }
        catch(Exception e) {
            // TO DO: handle exception.
System.err.println("Unable to reset the agent, " + this.agent.getId() + ".\n" + e);
        }
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
     * Set the ID.
     *
     * @param  id  the ID.
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
