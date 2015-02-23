package globals;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;

import play.Application;
import play.GlobalSettings;
import thrift.TIdServiceImpl;
import util.ThriftServerUtils;

import com.github.btnguyen2k.idserver.thrift.TIdService;

public class Bootstrap extends GlobalSettings {

    public void onStart(Application app) {
        super.onStart(app);
        Registry.init();

        startThriftServer();
    }

    @Override
    public void onStop(Application app) {
        Registry.destroy();
        super.onStop(app);
    }

    private void startThriftServer() {
        TServer thriftServer = null;
        try {
            int thriftPort = 9090;
            TProcessorFactory processorFactory = new TProcessorFactory(
                    new TIdService.Processor<TIdService.Iface>(TIdServiceImpl.instance));
            // TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
            TProtocolFactory protocolFactory = new TCompactProtocol.Factory();
            thriftServer = ThriftServerUtils.createThreadedSelectorServer(processorFactory,
                    protocolFactory, thriftPort, 3000, 0, 0, 2, 8);
        } catch (Exception e) {
            thriftServer = null;
        }
        Registry.startThriftServer(thriftServer);
    }
}
