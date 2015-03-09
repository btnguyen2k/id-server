package globals;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;
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
        int thriftPort = 0;
        if (Play.isDev()) {
            thriftPort = 9090;
        } else {
            try {
                thriftPort = Integer.parseInt(System.getProperty("thrift.port"));
            } catch (Exception e) {
                thriftPort = 0;
            }
        }

        if (thriftPort > 0) {
            TServer thriftServer = null;
            try {
                TProcessorFactory processorFactory = new TProcessorFactory(
                        new TIdService.Processor<TIdService.Iface>(TIdServiceImpl.instance));
                TProtocolFactory protocolFactory = new TCompactProtocol.Factory();
                thriftServer = ThriftServerUtils.createThreadedSelectorServer(processorFactory,
                        protocolFactory, thriftPort, 3000, 0, 0, 4, 128);
            } catch (Exception e) {
                thriftServer = null;
            }
            Logger.info("Starting Thrift API server on port " + thriftPort + "...");
            Registry.startThriftServer(thriftServer);
        }
    }
}
