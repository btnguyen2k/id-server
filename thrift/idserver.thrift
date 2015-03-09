// Thrift definition file for id-server

namespace java com.github.btnguyen2k.idserver.thrift

struct TIdResponse {
    1: i32 status,
    2: i64 id,
    3: string message
}

service TIdService {
    /**
     * "Ping" the server. This method is to test if server is reachable.
     */
    oneway void ping(),
    
    /**
     * "Ping" the server. This method is to test if server is reachable.
     */
    bool ping2(),

    /**
     * Generates next ID for a namespace, using specified engine.
     * 
     * @param _namespace
     * @param _engine
     * @return
     */
    TIdResponse nextId(1: string _namespace, 2: string _engine),
    
    /**
     * Gets current ID for a namespace, using specified engine.
     * 
     * @param namespace
     * @param engine
     * @return
     */
    TIdResponse currentId(1: string _namespace, 2: string _engine)
    
    /**
     * Set ID value for a namespace, using specified engine.
     * 
     * @param namespace
     * @param value
     * @param engine
     * @return
     */
    TIdResponse setValue(1: string _namespace, 2: i64 _value, 3: string _engine)
}
