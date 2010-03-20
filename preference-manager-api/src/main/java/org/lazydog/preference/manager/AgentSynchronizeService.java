package org.lazydog.preference.manager;

import javax.management.MXBean;


/**
 * Agent synchronize service MXBean.
 * 
 * @author  Ron Rickard
 */
@MXBean
public interface AgentSynchronizeService {

    public static final String OBJECT_NAME = "org.lazydog.preference.manager:type=SynchronizeService";

    public String exportDocument()
            throws ServiceException;
    public String exportDocument(String path)
            throws ServiceException;
    public void importDocument(String document)
            throws ServiceException;
    public void importDocument(String path, String document)
            throws ServiceException;
}
