package org.lazydog.preference.manager.configuration.service;

import java.util.List;
import org.lazydog.preference.manager.model.Agent;
import org.lazydog.preference.manager.model.SetupType;
import org.lazydog.preference.manager.service.ServiceException;


/**
 * Configuration service.
 *
 * @author Ron Rickard
 */
public interface ConfigurationService {

    public Agent findAgent(int id);
    public List<Agent> findAgents()
            throws ServiceException;
    public SetupType findSetupType();
    public Agent persistAgent(Agent agent)
            throws ServiceException;
    public SetupType persistSetupType(SetupType setupType)
            throws ServiceException;
    public void removeAgent(int id)
            throws ServiceException;
    public void removeSetupType()
            throws ServiceException;
}
