package globals;

import org.apache.thrift.server.TServer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import play.Logger;
import api.IdApi;

public class Registry {

    public static void init() {
        initApplicationContext();
        idApi = getBean(IdApi.class);
    }

    public static void destroy() {
        stopThriftServer();
        destroyApplicationContext();
    }

    /*----------------------------------------------------------------------*/
    private static TServer thriftServer = null;

    public static void startThriftServer(final TServer thriftServer) {
        Registry.thriftServer = thriftServer;
        Thread t = new Thread("Thrift Server") {
            public void run() {
                System.out.println("Starting Thrift server " + thriftServer);
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
    private static IdApi idApi;

    public static IdApi getIdApi() {
        return idApi;
    }

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
