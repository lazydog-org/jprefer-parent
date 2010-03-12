package org.lazydog.preference.configuration.service.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.lazydog.preference.configuration.model.SetupType;
import org.lazydog.preference.configuration.model.Agent;
import org.lazydog.preference.configuration.service.ConfigurationService;
import org.lazydog.preference.configuration.service.ConfigurationServiceException;


/**
 * Configuration data access object.
 *
 * @author  Ron Rickard
 */
public class ConfigurationServiceImpl implements ConfigurationService {

    private static final String AGENT_KEY_PREFIX = "agent.";
    private static final String AGENT_KEY_REGEX = AGENT_KEY_PREFIX + "\\d+";
    private static final String AGENT_VALUE_REGEX = "(.*),(\\d*),(.*),(.*),(.*)";
    private static final String AGENT_VALUE_SEPARATOR = ",";
    private static final String SEQUENCE_KEY = "sequence";
    private static final int START_SEQUENCE = 1;
    private static final String SETUP_TYPE_KEY = "setup.type";
    private static final int SERVER_NAME_GROUP = 1;
    private static final int JMX_PORT_GROUP = 2;
    private static final int LOGIN_GROUP = 3;
    private static final int PASSWORD_GROUP = 4;
    private static final int ENABLED_GROUP = 5;

    private Preferences preferences;

    /**
     * Constructor.
     */
    public ConfigurationServiceImpl() {
        preferences = Preferences.userNodeForPackage(this.getClass());
    }
    
    /**
     * Find the agent.
     *
     * @param  id  the ID.
     *
     * @return  the agent.
     *
     * @throws  ConfigurationDAOException  if unable to find the agent.
     */
    @Override
    public Agent findAgent(int id)
            throws ConfigurationServiceException {

        // Declare.
        Agent agent;

        // Initialize.
        agent = null;

        try {

            // Declare.
            String agentValue;

            // Get the agent value.
            agentValue = this.preferences.get(this.getAgentKey(id), null);

            // Interpret the agent value as an agent.
            agent = this.interpret(agentValue, id);
        }
        catch(Exception e) {
            throw new ConfigurationServiceException(
                    "Unable to find the agent, " + id + ".", e);
        }

        return agent;
    }

    /**
     * Find the agents.
     *
     * @return  the agents.
     *
     * @throws  ConfigurationDAOException  if unable to find the agents.
     */
    @Override
    public List<Agent> findAgents()
            throws ConfigurationServiceException {

        // Declare.
        List<Agent> agents;

        // Initialize.
        agents = new ArrayList<Agent>();

        try {

            // Declare.
            String[] keys;

            // Get the keys.
            keys = this.preferences.keys();

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
        catch(Exception e) {
            throw new ConfigurationServiceException(
                    "Unable to find the agents.", e);
        }

        return agents;
    }

    /**
     * Find the setup type.
     *
     * @return  the setup type.
     *
     * @throws  ConfigurationDAOException  if unable to find the setup type.
     */
    @Override
    public SetupType findSetupType()
            throws ConfigurationServiceException {

        // Declare.
        SetupType setupType;

        // Initialize.
        setupType = null;

        try {

            // Get the setup type.
            setupType = SetupType.valueOf(this.preferences.get(SETUP_TYPE_KEY, null));
        }
        catch(Exception e) {
            throw new ConfigurationServiceException(
                    "Unable to find the setup type.", e);
        }
        return setupType;
    }

    /**
     * Get the agent key.
     * 
     * @param  id  the ID.
     * 
     * @return  the agent key.
     */
    private String getAgentKey(int id) {
        return AGENT_KEY_PREFIX + id;
    }

    /**
     * Get the sequence and prime for the next sequence.
     * 
     * @return  the sequence.
     * 
     * @throws  BackingStoreException  if unable to prime for the next sequence.
     */
    private int getSequence()
            throws BackingStoreException {

        // Declare.
        int nextSequence;
        int sequence;

        // Get the current sequence.
        sequence = this.preferences.getInt(SEQUENCE_KEY, START_SEQUENCE);

        // Store the next sequence.
        nextSequence = sequence + 1;
        this.preferences.putInt(SEQUENCE_KEY, nextSequence);
        this.preferences.flush();

        return sequence;
    }

    /**
     * Interpret the agent as a agent value.
     * 
     * @param  agent  the agent.
     * 
     * @return  the agent value or null if the agent is null.
     */
    private String interpret(Agent agent) {

        // Declare.
        StringBuffer agentValue;

        // Initialize.
        agentValue = null;

        // Check if the agent exits.
        if (agent != null) {

            // Set the agent value.
            agentValue = new StringBuffer();
            agentValue.append(agent.getServerName());
            agentValue.append(AGENT_VALUE_SEPARATOR);
            agentValue.append(agent.getJmxPort());
            agentValue.append(AGENT_VALUE_SEPARATOR);
            agentValue.append(agent.getLogin());
            agentValue.append(AGENT_VALUE_SEPARATOR);
            agentValue.append(agent.getPassword());
            agentValue.append(AGENT_VALUE_SEPARATOR);
            agentValue.append(agent.getEnabled());
        }

        return (agentValue != null) ? agentValue.toString() : null;
    }

    /**
     * Interpret the agent value as an agent.
     * 
     * @param  agentValue  the comma-separate agent value.
     * @param  id          the ID.
     * 
     * @return  the agent or null if the agent value is null or is not valid.
     */
    private Agent interpret(String agentValue, int id) {

        // Declare.
        Agent agent;

        // Initialize.
        agent = null;

        // Declare.
        Matcher matcher;

        // Create a matcher to parse.
        matcher = Pattern.compile(AGENT_VALUE_REGEX).matcher(agentValue);

        // Check if there is an agent value
        // and it matches the agent value regular expression.
        if (agentValue != null &&
            matcher.matches()) {

            // Declare.
            boolean enabled;
            String enabledValue;
            int jmxPort;
            String jmxPortValue;

            // Parse the enabled and JMX port values.
            enabledValue = matcher.group(ENABLED_GROUP);
            enabled = (enabledValue != null) ? Boolean.valueOf(enabledValue) : Boolean.FALSE;
            jmxPortValue = matcher.group(JMX_PORT_GROUP);
            jmxPort = (jmxPortValue != null) ? Integer.valueOf(jmxPortValue) : new Integer(0);

            // Set the agent.
            agent = new Agent();
            agent.setEnabled(enabled);
            agent.setId(Integer.valueOf(id));
            agent.setJmxPort(jmxPort);
            agent.setLogin(matcher.group(LOGIN_GROUP));
            agent.setPassword(matcher.group(PASSWORD_GROUP));
            agent.setServerName(matcher.group(SERVER_NAME_GROUP));
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
     * @throws  ConfigurationDAOException  if unable to persist the agent.
     */
    @Override
    public Agent persistAgent(Agent agent)
            throws ConfigurationServiceException {

        try {

            // Declare.
            String agentKey;
            String agentValue;
            int id;

            // The ID is the agent ID, otherwise it is the sequence.
            id = (agent.getId() != null) ? agent.getId() : this.getSequence();

            // Get the agent key and value.
            agentKey = this.getAgentKey(id);
            agentValue = this.interpret(agent);

            // Store the agent.
            this.preferences.put(agentKey, agentValue);
            this.preferences.flush();

            // Get the agent.
            agent = this.findAgent(id);
        }
        catch(Exception e) {
            throw new ConfigurationServiceException(
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
     * @throws  ConfigurationDAOException  if unable to persist the setup type.
     */
    @Override
    public SetupType persistSetupType(SetupType setupType)
            throws ConfigurationServiceException {

        try {

            // Store the setup type.
            this.preferences.put(SETUP_TYPE_KEY, setupType.toString());
            this.preferences.flush();

            // Get the setup type.
            setupType = this.findSetupType();
        }
        catch(Exception e) {
            throw new ConfigurationServiceException(
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
     * @throws  ConfigurationDAOException  if unable to remove the agent.
     */
    @Override
    public void removeAgent(int id)
            throws ConfigurationServiceException {

        try {

            // Remove the agent.
            this.preferences.remove(this.getAgentKey(id));
        }
        catch(Exception e) {
            throw new ConfigurationServiceException(
                    "Unable to remove the agent, " + id + ".", e);
        }
    }

    /**
     * Remove the agent specified by the ID.
     *
     * @param  id  the ID.
     *
     * @throws  ConfigurationDAOException  if unable to remove the agent.
     */
    @Override
    public void removeSetupType()
            throws ConfigurationServiceException {

        try {

            // Remove the setup type.
            this.preferences.remove(SETUP_TYPE_KEY);
        }
        catch(Exception e) {
            throw new ConfigurationServiceException(
                    "Unable to remove the setup type.", e);
        }
    }

    public static void main(String[] args) throws Exception {

        ConfigurationServiceImpl dao = new ConfigurationServiceImpl();

        Agent agent1 = new Agent();
        agent1.setEnabled(Boolean.FALSE);
        agent1.setJmxPort(8686);
        agent1.setLogin("admin");
        agent1.setPassword("adminadmin");
        agent1.setServerName("SIC36565.sic.nwie.net");
        System.out.println("Before store: " + agent1);

        agent1 = dao.persistAgent(agent1);
        System.out.println("After store: " + agent1);

        agent1.setEnabled(Boolean.TRUE);
        agent1 = dao.persistAgent(agent1);
        System.out.println("After change: " + agent1);

        Agent agent2 = dao.findAgent(agent1.getId());
        System.out.println("Find: " + agent2);

        agent2.setServerName("another.sic.nwie.net");
        agent2.setId(null);
        agent2 = dao.persistAgent(agent2);
        System.out.println("After store: " + agent2);

        List<Agent> agents = dao.findAgents();
        for (Agent agent : agents) {
            System.out.println("Find agents (both): " + agent);
        }
/*
        dao.remove(agent2.getId());
        agents = dao.findAgents();
        for (Agent agent : agents) {
            System.out.println("Find agents (one): " + agent);
        }

        dao.remove(agent1.getId());
        agents = dao.findAgents();
        for (Agent agent : agents) {
            System.out.println("Find agents (none): " + agent);
        }
 */
    }
}
