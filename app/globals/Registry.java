package globals;

import org.apache.thrift.server.TServer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import play.Logger;
import api.IdApi;

import com.github.ddth.tsc.DataPoint;
import com.github.ddth.tsc.ICounter;
import com.github.ddth.tsc.ICounterFactory;

public class Registry {

    public static void init() {
        initApplicationContext();

        localCounterFactory = getBean("TSC_LOCAL", ICounterFactory.class);
        globalCounterFactory = getBean("TSC_GLOBAL", ICounterFactory.class);
        idApi = getBean(IdApi.class);
    }

    public static void destroy() {
        stopThriftServer();

        destroyApplicationContext();
    }

    /*----------------------------------------------------------------------*/
    private static ICounterFactory localCounterFactory, globalCounterFactory;
    public final static String TSC_TOTAL = "IDSERVER_TSC_TOTAL";
    public final static String TSC_SUCCESSFUL = "IDSERVER_TSC_SUCCESSFUL";
    public final static String TSC_FAILED_NAMESPACE = "IDSERVER_TSC_FAILED_NAMESPACE";
    public final static String TSC_FAILED_ENGINE = "IDSERVER_TSC_FAILED_ENGINE";

    public final static String COUNTER_TOTAL = "IDSERVER_COUNTER_TOTAL";
    public final static String COUNTER_SUCCESSFUL = "IDSERVER_COUNTER_SUCCESSFUL";
    public final static String COUNTER_FAILED_NAMESPACE = "IDSERVER_COUNTER_FAILED_NAMESPACE";
    public final static String COUNTER_FAILED_ENGINE = "IDSERVER_COUNTER_FAILED_ENGINE";

    public final static String COUNTER_CONCURENCY = "IDSERVER_CONCURENCY";

    /**
     * @since 0.2.0
     */
    public static void incConcurrency() {
        ICounter counter;

        counter = localCounterFactory != null ? localCounterFactory.getCounter(COUNTER_CONCURENCY)
                : null;
        if (counter != null) {
            counter.add(1000, 1);
        }

        counter = globalCounterFactory != null ? globalCounterFactory
                .getCounter(COUNTER_CONCURENCY) : null;
        if (counter != null) {
            counter.add(1000, 1);
        }
    }

    /**
     * @since 0.2.0
     */
    public static void decConcurrency() {
        ICounter counter;

        counter = localCounterFactory != null ? localCounterFactory.getCounter(COUNTER_CONCURENCY)
                : null;
        if (counter != null) {
            counter.add(1000, -1);
        }

        counter = globalCounterFactory != null ? globalCounterFactory
                .getCounter(COUNTER_CONCURENCY) : null;
        if (counter != null) {
            counter.add(1000, -1);
        }
    }

    /**
     * @since 0.2.0
     */
    public static long[] getConcurrency() {
        ICounter counter;
        DataPoint dp;
        long[] result = new long[2];

        counter = localCounterFactory != null ? localCounterFactory.getCounter(COUNTER_CONCURENCY)
                : null;
        dp = counter != null ? counter.get(1000) : null;
        result[0] = dp != null ? dp.value() : 0;

        counter = globalCounterFactory != null ? globalCounterFactory
                .getCounter(COUNTER_CONCURENCY) : null;
        dp = counter != null ? counter.get(1000) : null;
        result[1] = dp != null ? dp.value() : 0;

        return result;
    }

    /**
     * @since 0.2.0
     */
    private static void _updateTscCounters(final String name) {
        ICounter counterLocal = localCounterFactory != null ? localCounterFactory.getCounter(name)
                : null;
        if (counterLocal != null) {
            counterLocal.add(1);
        }

        ICounter counterGlobal = globalCounterFactory != null ? globalCounterFactory
                .getCounter(name) : null;
        if (counterGlobal != null) {
            counterGlobal.add(1);
        }
    }

    /**
     * @since 0.2.0
     */
    private static void _updateCounters(final String name) {
        ICounter counterLocal = localCounterFactory != null ? localCounterFactory.getCounter(name)
                : null;
        if (counterLocal != null) {
            counterLocal.add(0, 1);
        }

        ICounter counterGlobal = globalCounterFactory != null ? globalCounterFactory
                .getCounter(name) : null;
        if (counterGlobal != null) {
            counterGlobal.add(0, 1);
        }
    }

    public static void updateCounters(final int status) {
        _updateTscCounters(TSC_TOTAL);
        _updateCounters(COUNTER_TOTAL);
        switch (status) {
        case -1:
            _updateTscCounters(TSC_FAILED_NAMESPACE);
            _updateCounters(COUNTER_FAILED_NAMESPACE);
            break;
        case 0:
            _updateTscCounters(TSC_FAILED_ENGINE);
            _updateCounters(COUNTER_FAILED_ENGINE);
            break;
        default:
            _updateTscCounters(TSC_SUCCESSFUL);
            _updateCounters(COUNTER_SUCCESSFUL);
        }
    }

    public static ICounterFactory getLocalCounterFactory() {
        return localCounterFactory;
    }

    public static ICounterFactory getGlobalCounterFactory() {
        return globalCounterFactory;
    }

    /*----------------------------------------------------------------------*/
    private static IdApi idApi;

    public static IdApi getIdApi() {
        return idApi;
    }

    /*----------------------------------------------------------------------*/
    private static TServer thriftServer = null;

    public static void startThriftServer(final TServer thriftServer) {
        Registry.thriftServer = thriftServer;
        Thread t = new Thread("Thrift Server") {
            public void run() {
                thriftServer.serve();
            }
        };
        t.start();
    }

    public static void stopThriftServer() {
        if (thriftServer != null) {
            try {
                thriftServer.stop();
            } catch (Exception e) {
                Logger.warn(e.getMessage(), e);
            } finally {
                thriftServer = null;
            }
        }
    }

    /*----------------------------------------------------------------------*/
    private static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> clazz) {
        try {
            return applicationContext.getBean(clazz);
        } catch (BeansException e) {
            return null;
        }
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        try {
            return applicationContext.getBean(name, clazz);
        } catch (BeansException e) {
            return null;
        }
    }

    synchronized private static void initApplicationContext() {
        if (Registry.applicationContext == null) {
            String configFile = "spring/beans.xml";
            ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                    configFile);
            applicationContext.start();
            Registry.applicationContext = applicationContext;
        }
    }

    synchronized private static void destroyApplicationContext() {
        if (applicationContext != null) {
            try {
                ((AbstractApplicationContext) applicationContext).destroy();
            } catch (Exception e) {
                Logger.warn(e.getMessage(), e);
            } finally {
                applicationContext = null;
            }
        }
    }
}
