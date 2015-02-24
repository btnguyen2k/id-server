package globals;

import org.apache.thrift.server.TServer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import play.Logger;
import api.IdApi;

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
    public final static String COUNTER_TOTAL = "IDSERVER_TOTAL";
    public final static String COUNTER_SUCCESSFUL = "IDSERVER_SUCCESSFUL";
    public final static String COUNTER_FAILED_NAMESPACE = "IDSERVER_FAILED_NAMESPACE";
    public final static String COUNTER_FAILED_ENGINE = "IDSERVER_FAILED_ENGINE";

    private static void _updateCounters(String name) {
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

    public static void updateCounters(final int status) {
        _updateCounters(COUNTER_TOTAL);
        switch (status) {
        case -1:
            _updateCounters(COUNTER_FAILED_NAMESPACE);
            break;
        case 0:
            _updateCounters(COUNTER_FAILED_ENGINE);
            break;
        default:
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
