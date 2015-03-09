package qnd;

import org.apache.thrift.transport.TTransportException;

import test.utils.Benchmark;
import test.utils.BenchmarkResult;
import test.utils.Operation;

import com.github.btnguyen2k.idserver.thrift.TIdService;
import com.github.ddth.thriftpool.ThriftClientPool;

public class QndBenchmarkThriftClientPing extends BaseQndThriftClient {

    private static void runTest(final int numRuns, final int numThreads) throws TTransportException {
        final ThriftClientPool<TIdService.Client, TIdService.Iface> pool = clientPool("localhost",
                9090);

        BenchmarkResult result = new Benchmark(new Operation() {
            @Override
            public void run(int runId) {
                try {
                    TIdService.Iface client = pool.borrowObject();
                    try {
                        client.ping();
                    } finally {
                        pool.returnObject(client);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, numRuns, numThreads).run();
        System.out.println(result.summarize());

        pool.destroy();
    }

    /**
     * @param args
     * @throws TTransportException
     */
    public static void main(String[] args) throws TTransportException {
        for (int i = 0; i < 10; i++) {
            runTest(320000, 64);
        }
    }
}
