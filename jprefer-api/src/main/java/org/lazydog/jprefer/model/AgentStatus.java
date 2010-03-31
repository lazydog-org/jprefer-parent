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
package org.lazydog.jprefer.model;

import java.io.Serializable;


/**
 * Agent status.
 *
 * @author  Ron Rickard
 */
public enum AgentStatus implements Serializable {
    DOWN          ("Down"),
    UP_SYNCED     ("Up, Sync'd"),
    UP_NOT_SYNCED ("Up, Not Sync'd");

    private String toString;

    AgentStatus(String toString) {
        this.toString = toString;
    }

    /**
     * The status as a String.
     *
     * @return  the status as a String.
     */
    @Override
    public String toString() {
        return this.toString;
    }
};

