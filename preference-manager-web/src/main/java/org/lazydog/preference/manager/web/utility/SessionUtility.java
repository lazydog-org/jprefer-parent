package org.lazydog.preference.manager.web.utility;

import javax.faces.context.FacesContext;


/**
 * Session utility.
 * 
 * @author  Ron Rickard
 */
public class SessionUtility {

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
