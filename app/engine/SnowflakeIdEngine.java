package engine;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.github.ddth.id.SnowflakeIdGenerator;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * Snowflake-implementation of {@link IIdEngine}.
 * 
 * @author ThanhNB
 * @since 0.1.0
 */
public class SnowflakeIdEngine implements IIdEngine {

    private LoadingCache<String, SnowflakeIdGenerator> _cache;
    private long nodeId = -1;

    protected long getNodeId() {
        return nodeId;
    }

    public SnowflakeIdEngine setNodeId(long nodeId) {
        this.nodeId = nodeId;
        return this;
    }

    /**
     * Init method
     * 
     * @return
     */
    public SnowflakeIdEngine init() {
        if (nodeId < 0) {
            nodeId = 0;
            try {
                InetAddress ip = InetAddress.getLocalHost();
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                byte[] mac = network.getHardwareAddress();
                for (byte temp : mac) {
                    nodeId = (nodeId << 8) | ((int) temp & 0xFF);
                }
            } catch (Exception e) {
                nodeId = System.currentTimeMillis();
            }

        }
        _cache = CacheBuilder.newBuilder()
                .removalListener(new RemovalListener<String, SnowflakeIdGenerator>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, SnowflakeIdGenerator> entry) {
                        // EMPTY
                    }
                }).expireAfterAccess(3600, TimeUnit.SECONDS)
                .build(new CacheLoader<String, SnowflakeIdGenerator>() {
                    @Override
                    public SnowflakeIdGenerator load(String namespace) throws Exception {
                        return SnowflakeIdGenerator.getInstance(nodeId);
                    }
                });
        return this;
    }

    /**
     * Destroy method.
     */
    public void destroy() {
        if (_cache != null) {
            _cache.invalidateAll();
            _cache = null;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws ExecutionException
     */
    @Override
    public long nextId(String namespace) throws ExecutionException {
        return _cache.get(namespace).generateId64();
    }

}
