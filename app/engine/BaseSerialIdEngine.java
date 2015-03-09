package engine;

import com.github.ddth.id.SerialIdGenerator;

/**
 * Base implementation of {@link IIdEngine}.
 * 
 * <p>
 * IDs generated by this engine are:
 * <ul>
 * <li>Unique: Unique within the namespace.</li>
 * <li>Ascending: Next generated ID is larger than previous ones.</li>
 * <li>Serial: {@code next-id} = {@code previous-id + 1}!</li>
 * </ul>
 * <p/>
 * 
 * @author ThanhNB
 * @since 0.1.0
 */
public class BaseSerialIdEngine implements IIdEngine {
    private SerialIdGenerator idGenerator;

    protected SerialIdGenerator getIdGenerator() {
        return idGenerator;
    }

    public BaseSerialIdEngine setIdGenerator(SerialIdGenerator idGenerator) {
        this.idGenerator = idGenerator;
        return this;
    }

    /**
     * Init method
     * 
     * @return
     */
    public BaseSerialIdEngine init() {
        return this;
    }

    /**
     * Destroy method.
     */
    public void destroy() {
    }

    protected String normalizeNamespace(final String namespace) {
        return namespace;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long nextId(final String namespace) {
        return idGenerator.nextId(normalizeNamespace(namespace));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long currentId(final String namespace) {
        return idGenerator.currentId(normalizeNamespace(namespace));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setValue(String namespace, long value) throws Exception {
        return idGenerator.setValue(namespace, value);
    }
}
