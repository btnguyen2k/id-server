package engine;

/**
 * API to generate IDs.
 * 
 * @author ThanhNB
 * @since 0.1.0
 */
public interface IIdEngine {
    /**
     * Generates next ID for a namespace.
     * 
     * @param namespace
     * @return
     * @throws Exception
     */
    public long nextId(final String namespace) throws Exception;

    /**
     * Gets current ID for a namespace.
     * 
     * @param namespace
     * @return
     * @throws Exception
     */
    public long currentId(final String namespace) throws Exception;

    /**
     * Sets value for a namespace.
     * 
     * @param namespace
     * @param value
     * @return
     * @throws Exception
     */
    public boolean setValue(final String namespace, final long value) throws Exception;
}
