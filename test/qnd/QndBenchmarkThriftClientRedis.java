package qnd;

import org.apache.thrift.transport.TTransportException;

import test.utils.Benchmark;
import test.utils.BenchmarkResult;
import test.utils.Operation;

import com.github.btnguyen2k.idserver.thrift.TIdResponse;
import com.github.btnguyen2k.idserver.thrift.TIdService;
import com.github.ddth.thriftpool.ThriftClientPool;

public class QndBenchmarkThriftClientRedis extends BaseQndThriftClient {

    private static void runTest(final int numRuns, final int numThreads, final int numNamespaces,
            final String engine) throws TTransportException {
        final ThriftClientPool<TIdService.Client, TIdService.Iface> pool = clientPool("localhost",
                9090);

        BenchmarkResult result = new Benchmark(new Operation() {
            @Override
            public void run(int runId) {
                String namespace = String.valueOf(runId % numNamespaces);
                try {
                    TIdService.Iface client = pool.borrowObject();
                    try {
                        TIdResponse id = client.nextId(namespace, engine);
                        if (id.status != 200) {
                            System.out.println(id);
                        }
                    } finally {
                        pool.returnObject(client);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, numRuns, numThreads).run();
        System.out.println("[" + engine + "]:\t" + result.summarize());

        pool.destroy();
    }

    public static void main(String[] args) throws TTransportException {
        for (int i = 0; i < 10; i++) {
            runTest(320000, 16, 4, "Redis");
        }
    }
}
