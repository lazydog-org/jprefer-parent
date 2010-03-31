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
 * along with Preference Manager.  If not, see <http://www.gnu.org/licenses/>.
 */
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
