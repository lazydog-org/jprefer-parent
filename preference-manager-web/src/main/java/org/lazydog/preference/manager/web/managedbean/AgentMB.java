package org.lazydog.preference.manager.web.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import org.lazydog.preference.manager.model.Agent;
import org.lazydog.preference.manager.PreferenceManager;


/**
 * Agent managed bean.
 *
 * @author  Ron Rickard
 */
public class AgentMB extends AbstractMB implements Serializable {

    private Agent agent;
    private Integer id;
    @EJB(mappedName="ejb/PreferenceManager", beanInterface=PreferenceManager.class)
    protected PreferenceManager preferenceManager;

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
        agents = new ArrayList<Agent>();
        
        try {

            // Get the agents.
            agents = preferenceManager.getAgents();
        }
        catch(Exception e) {
            this.createMessage("Unable to get the agents.");
        }

        return agents;
    }

    /**
     * Initialize.
     */
    @PostConstruct
    public void initialize() {

        // Create an agent.
        this.agent = new Agent();

        // Set message available to false.
        this.setMessageAvailable(Boolean.FALSE);
    }

    /**
     * Process the delete button.
     *
     * @param  actionEvent  the action event.
     */
    public void processDeleteButton(ActionEvent actionEvent) {

        try {

            // Remove the agent.
            preferenceManager.removeAgent(this.id);
        }
        catch(Exception e) {
            this.createMessage("Unable to delete the agent.");
        }
    }

    /**
     * Process the disable button.
     *
     * @param  actionEvent  the action event.
     */
    public void processDisableButton(ActionEvent actionEvent) {

        try {

            // Disable the agent.
            this.agent = preferenceManager.disableAgent(this.id);
        }
        catch(Exception e) {
            this.createMessage("Unable to disable the agent.");
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
            this.agent = preferenceManager.enableAgent(this.id);
        }
        catch(Exception e) {
            this.createMessage("Unable to enable the agent.");
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
            this.agent = preferenceManager.getAgent(this.id);
        }
        catch(Exception e) {
            this.createMessage("Unable to modify the agent.");
        }
    }

    /**
     * Process the OK button.
     *
     * @param  actionEvent  the action event.
     */
    public void processOkButton(ActionEvent actionEvent) {

        try {

            // Validate the agent.
            if (this.agent.validate().size() == 0) {

                // Save the agent.
                preferenceManager.saveAgent(this.agent);
            }
            else {

                // Loop through the violation messages.
                for (String violationMessage : this.agent.validate()) {
                    this.createMessage(violationMessage);
                }
            }
        }
        catch(Exception e) {

            // Check if this is a new agent.
            if (this.agent.getId() == null) {
                this.createMessage("Unable to add the agent.");
            }
            else {
                this.createMessage("Unable to modify the agent.");
            }
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
                this.agent = preferenceManager.getAgent(this.agent.getId());
            }
            else {

                // Create a new agent.
                this.agent = new Agent();
            }
        }
        catch(Exception e) {
            this.createMessage("Unable to reset.");
        }
    }

    /**
     * Process the synchronize button.
     *
     * @param  actionEvent  the action event.
     */
    public void processSynchronizeButton(ActionEvent actionEvent) {

        try {

            // Synchronize the agent.
            preferenceManager.synchronizeAgent(preferenceManager.getAgent(this.id));
        }
        catch(Exception e) {
            this.createMessage("Unable to synchronize the agent.");
        }
    }

    /**
     * Process the synchronize all button.
     *
     * @param  actionEvent  the action event.
     */
    public void processSynchronizeAllButton(ActionEvent actionEvent) {

        try {

            // Synchronize all agents.
            preferenceManager.synchronizeAgents();
        }
        catch(Exception e) {
            this.createMessage("Unable to synchronize all the agents.");
        }
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
