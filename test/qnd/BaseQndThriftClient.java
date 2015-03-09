package qnd;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.github.btnguyen2k.idserver.thrift.TIdService;
import com.github.ddth.thriftpool.ITProtocolFactory;
import com.github.ddth.thriftpool.PoolConfig;
import com.github.ddth.thriftpool.ThriftClientPool;

public class BaseQndThriftClient {
    protected static ITProtocolFactory protocolFactory(final String host, final int port) {
        ITProtocolFactory protocolFactory = new ITProtocolFactory() {
            @Override
            public TProtocol create() {
                TTransport transport = new TFramedTransport(new TSocket(host, port));
                TProtocol protocol = new TCompactProtocol(transport);
                return protocol;
            }
        };
        return protocolFactory;
    }

    protected static ThriftClientPool<TIdService.Client, TIdService.Iface> clientPool(
            final String host, final int port) {
        final ThriftClientPool<TIdService.Client, TIdService.Iface> pool = new ThriftClientPool<TIdService.Client, TIdService.Iface>();
        pool.setClientClass(TIdService.Client.class).setClientInterface(TIdService.Iface.class);
        pool.setTProtocolFactory(protocolFactory(host, port));
        pool.setPoolConfig(new PoolConfig().setMaxActive(8192).setMaxWaitTime(10000));
        pool.init();
        return pool;
    }
}
