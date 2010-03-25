package org.lazydog.preference.manager.internal.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.lazydog.preference.manager.model.Agent;
import org.lazydog.preference.manager.model.SetupType;
import org.lazydog.preference.manager.ServiceException;
import org.lazydog.preference.manager.spi.configuration.ConfigurationService;


/**
 * Configuration service implementation.
 *
 * @author  Ron Rickard
 */
public final class ConfigurationServiceImpl implements ConfigurationService {

    private static final String AGENT_KEY_PREFIX = "agent.";
    private static final String AGENT_KEY_REGEX = AGENT_KEY_PREFIX + "\\d+";
    private static final String AGENT_VALUE_REGEX = "(.*),(\\d*),(.*),(.*),(.*)";
    private static final String AGENT_VALUE_SEPARATOR = ",";
    private static final String CONFIGURATION_PATH = "org/lazydog/preference/manager/configuration";
    private static final String SEQUENCE_KEY = "sequence";
    private static final int START_SEQUENCE = 1;
    private static final String SETUP_TYPE_KEY = "setup.type";
    private static final int SERVER_NAME_GROUP = 1;
    private static final int JMX_PORT_GROUP = 2;
    private static final int LOGIN_GROUP = 3;
    private static final int PASSWORD_GROUP = 4;
    private static final int ENABLED_GROUP = 5;

    private static final Preferences preferences =
            Preferences.userRoot().node(CONFIGURATION_PATH);

    /**
     * Find the agent.
     *
     * @param  id  the ID.
     *
     * @return  the agent.
     *
     * @throws  ServiceException          if unable to find the agent.
     * @throws  IllegalArgumentException  if the ID is invalid.
     */
    @Override
    public Agent findAgent(int id) 
            throws ServiceException {

        // Declare.
        Agent agent;
        String agentValue;

        // Get the agent value.
        agentValue = preferences.get(getAgentKey(id), null);

        try {

            // Interpret the agent value as an agent.
            agent = interpret(agentValue, id);
        }
        catch(IllegalArgumentException e) {
            throw new ServiceException(
                    "Unable to find the agent, " + id + ".", e);
        }

        return agent;
    }

    /**
     * Find the agents.
     *
     * @return  the agents.
     *
     * @throws  ServiceException  if unable to find the agents.
     */
    @Override
    public List<Agent> findAgents()
            throws ServiceException {

        // Declare.
        List<Agent> agents;

        // Initialize.
        agents = new ArrayList<Agent>();

        try {

            // Declare.
            String[] keys;

            // Get the keys.
            keys = preferences.keys();

            // Loop through the keys.
            for (int x = 0; x < keys.length; x++) {

                // Declare.
                String key;

                // Get the key.
                key = keys[x];

                // Check if the key is a agent key.
                if (key.matches(AGENT_KEY_REGEX)) {

                    // Declare.
                    Agent agent;
                    int id;

                    // Parse the ID from the agent key.
                    id = Integer.parseInt(key.replace(AGENT_KEY_PREFIX, ""));

                    // Find the agent.
                    agent = this.findAgent(id);

                    // Add the agent to the agents.
                    agents.add(agent);
                }
            }
        }
        catch(BackingStoreException e) {
            throw new ServiceException("Unable to find the agents.", e);
        }

        return agents;
    }

    /**
     * Find the setup type.
     *
     * @return  the setup type.
     *
     * @throws  ServiceException  if unable to find the setup type.
     */
    @Override
    public SetupType findSetupType() 
            throws ServiceException {
        return SetupType.valueOf(preferences.get(
                SETUP_TYPE_KEY, SetupType.UNKNOWN.toString()));
    }

    /**
     * Get the agent key.
     * 
     * @param  id  the ID.
     * 
     * @return  the agent key.
     *
     * @throws  IllegalArgumentException  if the ID is invalid.
     */
    private static String getAgentKey(int id) {

        // Check if the ID is invalid.
        if (id < START_SEQUENCE) {
            throw new IllegalArgumentException("The ID is invalid.");
        }

        return AGENT_KEY_PREFIX + id;
    }

    /**
     * Get the sequence.
     * 
     * @return  the sequence.
     * 
     * @throws  BackingStoreException  if unable to get the sequence.
     */
    private static int getSequence()
            throws BackingStoreException {

        // Declare.
        int nextSequence;
        int sequence;

        // Get the current sequence.
        sequence = preferences.getInt(SEQUENCE_KEY, START_SEQUENCE);

        // Store the next sequence.
        nextSequence = sequence + 1;
        preferences.putInt(SEQUENCE_KEY, nextSequence);
        preferences.flush();

        return sequence;
    }

    /**
     * Interpret the agent as a agent value.
     * 
     * @param  agent  the agent.
     * 
     * @return  the agent value.
     *
     * @throws  NullPointerException  if the agent is null.
     */
    private static String interpret(Agent agent) {

        // Declare.
        StringBuffer agentValue;

        // Check if the agent exits.
        if (agent != null) {

            // Set the agent value.
            agentValue = new StringBuffer();
            agentValue.append((agent.getServerName() == null) ? "" : agent.getServerName());
            agentValue.append(AGENT_VALUE_SEPARATOR);
            agentValue.append((agent.getJmxPort() == null) ? "" : agent.getJmxPort());
            agentValue.append(AGENT_VALUE_SEPARATOR);
            agentValue.append((agent.getLogin() == null) ? "" : agent.getLogin());
            agentValue.append(AGENT_VALUE_SEPARATOR);
            agentValue.append((agent.getPassword() == null) ? "" : agent.getPassword());
            agentValue.append(AGENT_VALUE_SEPARATOR);
            agentValue.append((agent.getEnabled() == null) ? "" : agent.getEnabled());
        }
        else {
            throw new NullPointerException("The agent is null.");
        }

        return (agentValue != null) ? agentValue.toString() : null;
    }

    /**
     * Interpret the agent value as an agent.
     * 
     * @param  agentValue  the comma-separate agent value.
     * @param  id          the ID.
     * 
     * @return  the agent.
     *
     * @throws  IllegalArgumentException   if the agent value or ID is invalid.
     */
    private static Agent interpret(String agentValue, int id) {

        // Declare.
        Agent agent;
        Matcher matcher;

        // Create a matcher to parse.
        matcher = Pattern.compile(AGENT_VALUE_REGEX).matcher(agentValue);

        // Check if the ID is invalid.
        if (id < START_SEQUENCE) {
            throw new IllegalArgumentException("The ID is invalid.");
        }

        // Check if there is an agent value
        // and it matches the agent value regular expression.
        if (agentValue != null &&
            matcher.matches()) {

            // Declare.
            boolean enabled;
            String enabledValue;

            // Parse the enabled and JMX port values.
            enabledValue = matcher.group(ENABLED_GROUP);
            enabled = (enabledValue != null) ? Boolean.valueOf(enabledValue) : Boolean.FALSE;

            // Set the agent.
            agent = new Agent();
            agent.setEnabled(enabled);
            agent.setId(Integer.valueOf(id));
            agent.setJmxPort(matcher.group(JMX_PORT_GROUP));
            agent.setLogin(matcher.group(LOGIN_GROUP));
            agent.setPassword(matcher.group(PASSWORD_GROUP));
            agent.setServerName(matcher.group(SERVER_NAME_GROUP));
        }
        else {
            throw new IllegalArgumentException("The agent value is invalid.");
        }

        return agent;
    }

    /**
     * Persist the agent.
     *
     * @param  agent  the agent.
     *
     * @return  the agent.
     *
     * @throws  ServiceException      if unable to persist the agent.
     * @throws  NullPointerException  if the agent is null.
     */
    @Override
    public Agent persistAgent(Agent agent)
            throws ServiceException {

        try {

            // Check if the agent is not null.
            if (agent != null) {

                // Declare.
                String agentKey;
                String agentValue;
                int id;

                // The ID is the agent ID, otherwise it is the sequence.
                id = (agent.getId() != null) ? agent.getId() : getSequence();

                // Get the agent key and value.
                agentKey = getAgentKey(id);
                agentValue = interpret(agent);

                // Store the agent.
                preferences.put(agentKey, agentValue);
                preferences.flush();

                // Get the agent.
                agent = this.findAgent(id);
            }
            else {
                throw new NullPointerException("The agent is null.");
            }
        }
        catch(BackingStoreException e) {
            throw new ServiceException(
                    "Unable to persist the agent, " + agent + ".", e);
        }

        return agent;
    }

    /**
     * Persist the setup type.
     * 
     * @param  setupType  the setup type.
     * 
     * @return  the setup type.
     * 
     * @throws  ServiceException      if unable to persist the setup type.
     * @throws  NullPointerException  if the setup type is null.
     */
    @Override
    public SetupType persistSetupType(SetupType setupType)
            throws ServiceException {

        try {

            // Check if the setup type is not null.
            if (setupType != null) {

                // Store the setup type.
                preferences.put(SETUP_TYPE_KEY, setupType.toString());
                preferences.flush();

                // Get the setup type.
                setupType = this.findSetupType();
            }
            else {
                throw new NullPointerException("The setup type is null.");
            }
        }
        catch(BackingStoreException e) {
            throw new ServiceException(
                    "Unable to persist the setup type, "
                    + setupType.toString() + ".", e);
        }

        return setupType;
    }

    /**
     * Remove the agent.
     *
     * @param  id  the ID.
     *
     * @throws  ServiceException          if unable to remove the agent.
     * @throws  IllegalArgumentException  if the ID is invalid.
     */
    @Override
    public void removeAgent(int id)
            throws ServiceException {

        try {

            // Remove the agent.
            preferences.remove(getAgentKey(id));
            preferences.flush();
        }
        catch(BackingStoreException e) {
            throw new ServiceException(
                    "Unable to remove the agent, " + id + ".", e);
        }
    }

    /**
     * Remove the setup type.
     *
     * @throws  ServiceException  if unable to remove the setup type.
     */
    @Override
    public void removeSetupType()
            throws ServiceException {

        try {

            // Remove the setup type.
            preferences.remove(SETUP_TYPE_KEY);
            preferences.flush();
        }
        catch(BackingStoreException e) {
            throw new ServiceException(
                    "Unable to remove the setup type.", e);
        }
    }
}
