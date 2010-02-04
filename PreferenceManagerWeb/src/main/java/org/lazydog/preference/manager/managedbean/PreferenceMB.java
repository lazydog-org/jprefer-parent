package org.lazydog.preference.manager.managedbean;

import java.io.Serializable;
import javax.faces.event.ActionEvent;
import org.lazydog.preference.manager.model.PreferenceNode;


/**
 * Preference managed bean.
 * 
 * @author  Ron Rickard
 */
public class PreferenceMB implements Serializable {

    private PreferenceNode[] rootNodes;

    /**
     * Get the root nodes.
     *
     * @return  the root nodes.
     */
    public PreferenceNode[] getRootNodes() {

        if (this.rootNodes == null) {

            this.rootNodes = new PreferenceNode[1];
            this.rootNodes[0] = new PreferenceNode(PreferenceNode.ROOT_NODE_NAME);
        }

        return this.rootNodes;
    }

    /**
     * Process the add button.
     *
     * @param  actionEvent  the action event.
     */
    public void processAddButton(ActionEvent actionEvent) {
System.err.println("processAddButton invoked");
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
     * Process the modify button.
     *
     * @param  actionEvent  the action event.
     */
    public void processModifyButton(ActionEvent actionEvent) {
System.err.println("processModifyButton invoked");
    }
}
