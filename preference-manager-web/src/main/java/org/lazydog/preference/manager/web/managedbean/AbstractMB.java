package org.lazydog.preference.manager.web.managedbean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.lazydog.preference.manager.PreferenceManager;
import org.lazydog.preference.manager.PreferenceManagerFactory;


/**
 * Abstract managed bean.
 *
 * @author  Ron Rickard
 */
public abstract class AbstractMB {

    private Boolean messageAvailable;

    /**
     * Create a message.
     *
     * @param  message  the message.
     */
    protected void createMessage(String message) {

        // Add the message.
        FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(message));

        // Set message available to true.
        this.messageAvailable = Boolean.TRUE;
    }

    /**
     * Is a message available?
     *
     * @return  true if a message is available, otherwise false.
     */
    public Boolean getMessageAvailable() {
        return this.messageAvailable;
    }

    /**
     * Get the preference manager.
     * 
     * @return  the preference manager.
     */
    public PreferenceManager getPreferenceManager() {
        return PreferenceManagerFactory.create();
    }

    /**
     * Set if a message is available.
     *
     * @param  messageAvailable  true if a message is available, otherwise false.
     */
    protected void setMessageAvailable(Boolean messageAvailable) {
        this.messageAvailable = messageAvailable;
    }
}
