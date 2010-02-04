package org.lazydog.preference.manager.model;

/**
 * Preference.
 *
 * @author  Ron Rickard
 */
public class Preference {

    private String key;
    private String value;

    /**
     * Get the key.
     *
     * @return  the key.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Get the value.
     *
     * @return  the value.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Set the key.
     *
     * @param  key  the key.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Set the value.
     *
     * @param  value  the value.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Get this object as a String.
     *
     * @return  this object as a String.
     */
    @Override
    public String toString() {
        return this.key + " " + this.value;
    }
}
