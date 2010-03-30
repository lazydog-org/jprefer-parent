/**
 * Copyright 2009, 2010 lazydog.org.
 *
 * This file is part of Preference Manager.
 *
 * Preference Manager is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Preference Manager is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Preference Manager.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.lazydog.preference.manager.web.utility;

import javax.faces.context.FacesContext;


/**
 * Session utility.
 * 
 * @author  Ron Rickard
 */
public final class SessionUtility {

    /**
     * Get the value for the specified key from the session.
     * 
     * @param  key  the key.
     * 
     * @return  the value or null if no value exists.
     */
    public static <T> T getValue(SessionKey key, Class<T> classObj) {
        return classObj.cast(FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get(key.toString()));
    }

    /**
     * Put the key-value pair on the session.
     *
     * @param  key    the key.
     * @param  value  the value.
     */
    public static void putValue(SessionKey key,
                                Object value) {

        // Put the key-value pair on the session map.
        FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().put(key.toString(), value);
    }

    /**
     * Remove the key-value pair from the session.
     *
     * @param  key  the key.
     */
    public static void removeValue(SessionKey key) {

        // Remove the key-value pair from the session map.
        FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().remove(key.toString());
    }

    /**
     * Check if the value for the key exists in the session.
     *
     * @param  key  the key.
     *
     * @return  true if the value exists in the session, otherwise return false.
     */
    public static boolean valueExists(SessionKey key) {
        return (getValue(key, Object.class) != null) ? true : false;
    }
}
