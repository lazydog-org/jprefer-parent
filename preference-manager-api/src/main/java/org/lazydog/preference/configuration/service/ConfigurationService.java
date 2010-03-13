package org.lazydog.preference.configuration.service;

import java.util.List;
import org.lazydog.preference.configuration.model.Agent;
import org.lazydog.preference.configuration.model.SetupType;


/**
 * Configuration service.
 *
 * @author Ron Rickard
 */
public interface ConfigurationService {

    public Agent findAgent(int id)
            throws ConfigurationServiceException;
    public List<Agent> findAgents()
            throws ConfigurationServiceException;
    public SetupType findSetupType()
            throws ConfigurationServiceException;
    public Agent persistAgent(Agent agent)
            throws ConfigurationServiceException;
    public SetupType persistSetupType(SetupType setupType)
            throws ConfigurationServiceException;
    public void removeAgent(int id)
            throws ConfigurationServiceException;
    public void removeSetupType()
            throws ConfigurationServiceException;
}
