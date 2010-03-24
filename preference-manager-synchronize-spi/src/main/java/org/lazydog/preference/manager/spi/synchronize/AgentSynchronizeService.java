package org.lazydog.preference.manager.spi.synchronize;

import javax.management.MXBean;


/**
 * Agent synchronize service MXBean.
 * 
 * @author  Ron Rickard
 */
@MXBean
public interface AgentSynchronizeService extends SynchronizeService {

    public static final String OBJECT_NAME = "org.lazydog.preference.manager:type=SynchronizeService";
}
