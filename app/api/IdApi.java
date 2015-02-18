package api;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import util.Constants;
import engine.IIdEngine;

/**
 * Engine to generate IDs.
 * 
 * @author ThanhNB
 * @since 0.1.0
 */
public class IdApi implements ApplicationContextAware {

    private IIdEngine defaultEngine;
    private Map<String, IIdEngine> engines = new HashMap<String, IIdEngine>();

    private boolean engineJdbcEnabled;
    private boolean engineRedisEnabled;
    private boolean engineSnowflakeEnabled;
    private boolean engineZookeeperEnabled;

    public IdApi setEngineJdbcEnabled(boolean value) {
        this.engineJdbcEnabled = value;
        return this;
    }

    public IdApi setEngineRedisEnabled(boolean value) {
        this.engineRedisEnabled = value;
        return this;
    }

    public IdApi setEngineSnowflakeEnabled(boolean value) {
        this.engineSnowflakeEnabled = value;
        return this;
    }

    public IdApi setEngineZookeeperEnabled(boolean value) {
        this.engineZookeeperEnabled = value;
        return this;
    }

    protected IIdEngine getDefaultEngine() {
        return defaultEngine;
    }

    public IdApi setDefaultEngine(IIdEngine defaultEngine) {
        this.defaultEngine = defaultEngine;
        return this;
    }

    /**
     * Init method.
     * 
     * @return
     */
    public IdApi init() {
        if (engineJdbcEnabled) {
            engines.put(Constants.ENGINE_JDBC, getBean("ENGINE_JDBC", IIdEngine.class));
        }

        if (engineSnowflakeEnabled) {
            engines.put(Constants.ENGINE_SNOWFLAKE, getBean("ENGINE_SNOWFLAKE", IIdEngine.class));
        }

        if (engineRedisEnabled) {
            engines.put(Constants.ENGINE_REDIS, getBean("ENGINE_REDIS", IIdEngine.class));
        }

        if (engineZookeeperEnabled) {
            engines.put(Constants.ENGINE_ZOOKEEPER, getBean("ENGINE_ZOOKEEPER", IIdEngine.class));
        }

        return this;
    }

    /**
     * Destroy method.
     */
    public void destroy() {
        // EMPTY
    }

    /**
     * Generates next ID for a namespace, using default engine.
     * 
     * @param namespace
     * @return
     * @throws Exception
     */
    public long nextId(final String namespace) throws Exception {
        return nextId(null, namespace);
    }

    private final static Pattern PATTERN_NAMESPACE = Pattern.compile("^[\\w]+$");

    /**
     * Generates next ID for a namespace, using specified engine.
     * 
     * @param engine
     * @param namespace
     * @return next ID as a long, or {@code -1} if engine is invalid/not
     *         supported.
     * @throws Exception
     */
    public long nextId(final String engine, String namespace) throws Exception {
        if (StringUtils.isBlank(namespace)) {
            namespace = "default";
        } else {
            namespace = namespace.trim().toLowerCase();
        }
        if (!PATTERN_NAMESPACE.matcher(namespace).matches()) {
            return -1;
        }

        IIdEngine idEngine = null;
        if (StringUtils.equalsIgnoreCase(engine, Constants.ENGINE_JDBC)) {
            idEngine = engines.get(Constants.ENGINE_JDBC);
        } else if (StringUtils.equalsIgnoreCase(engine, Constants.ENGINE_REDIS)) {
            idEngine = engines.get(Constants.ENGINE_REDIS);
        } else if (StringUtils.equalsIgnoreCase(engine, Constants.ENGINE_SNOWFLAKE)) {
            idEngine = engines.get(Constants.ENGINE_SNOWFLAKE);
        } else if (StringUtils.equalsIgnoreCase(engine, Constants.ENGINE_ZK)
                || StringUtils.equalsIgnoreCase(engine, Constants.ENGINE_ZOOKEEPER)) {
            idEngine = engines.get(Constants.ENGINE_ZOOKEEPER);
        } else {
            idEngine = getDefaultEngine();
        }

        if (idEngine == null) {
            return 0;
        }

        return idEngine.nextId(namespace);
    }

    private ApplicationContext appContext;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        this.appContext = appContext;
    }

    private <T> T getBean(String name, Class<T> clazz) {
        try {
            return appContext.getBean(name, clazz);
        } catch (BeansException e) {
            return null;
        }
    }
}
