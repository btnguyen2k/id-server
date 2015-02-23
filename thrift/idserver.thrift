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
}
