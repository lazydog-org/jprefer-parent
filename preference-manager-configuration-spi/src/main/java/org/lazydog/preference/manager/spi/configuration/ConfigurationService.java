package org.lazydog.preference.manager.spi.configuration;

import java.util.List;
import org.lazydog.preference.manager.model.Agent;
import org.lazydog.preference.manager.model.SetupType;
import org.lazydog.preference.manager.ServiceException;


/**
 * Configuration service.
 *
 * @author Ron Rickard
 */
public interface ConfigurationService {

    public Agent findAgent(int id)
            throws ServiceException;
    public List<Agent> findAgents()
            throws ServiceException;
    public SetupType findSetupType()
            throws ServiceException;
    public Agent persistAgent(Agent agent)
            throws ServiceException;
    public SetupType persistSetupType(SetupType setupType)
            throws ServiceException;
    public void removeAgent(int id)
            throws ServiceException;
    public void removeSetupType()
            throws ServiceException;
}
