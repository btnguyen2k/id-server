package qnd;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.github.btnguyen2k.idserver.thrift.TIdService;

public class QndThriftClient {

    public static void main(String[] args) throws TException {
        TTransport transport = new TFramedTransport(new TSocket("localhost", 9090));
        transport.open();

        // TProtocol protocol = new TBinaryProtocol(transport);
        TProtocol protocol = new TCompactProtocol(transport);
        TIdService.Client client = new TIdService.Client(protocol);
        System.out.println(client.nextId("default", "redis"));
        System.out.println(client.currentId("default", "redis"));
    }
}
