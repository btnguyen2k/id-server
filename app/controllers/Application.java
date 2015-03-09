package controllers;

import globals.Registry;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import play.Logger;
import play.api.templates.Html;
import play.mvc.Result;
import util.Constants;
import api.IdApi;

import com.github.ddth.commons.utils.SerializationUtils;
import com.github.ddth.tsc.DataPoint;
import com.github.ddth.tsc.ICounter;
import com.github.ddth.tsc.ICounterFactory;

public class Application extends BaseController {

    public static final Charset UTF8 = Charset.forName("UTF-8");

    private static Result doResponse(int status, long id, String message) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constants.RESPONSE_FIELD_STATUS, status);
        result.put(Constants.RESPONSE_FIELD_ID, id);
        result.put(Constants.RESPONSE_FIELD_MESSAGE, message);
        response().setHeader(CONTENT_TYPE, "application/json");
        response().setHeader(CONTENT_ENCODING, "utf-8");
        return ok(SerializationUtils.toJsonString(result));
    }

    /*
     * Handles: GET:/nextId/:namespace/:engine
     */
    public static Result nextId(final String namespace, final String engine) {
        IdApi idApi = Registry.getIdApi();
        try {
            long id = idApi.nextId(engine, namespace);
            if (id == 0) {
                return doResponse(404, id, "Engine not supported [" + engine + "]!");
            }
            if (id < 0) {
                return doResponse(400, id, "Invalid namespace [" + namespace + "]!");
            }
            return doResponse(200, id, "Successful");
        } catch (Exception e) {
            final String logMsg = "Exception [" + e.getClass() + "]: " + e.getMessage();
            Logger.error(logMsg, e);
            return doResponse(500, -1, logMsg);
        }
    }

    /*
     * Handles: GET:/currentId/:namespace/:engine
     */
    public static Result currentId(final String namespace, final String engine) {
        IdApi idApi = Registry.getIdApi();
        try {
            long id = idApi.currentId(engine, namespace);
            if (id == -2) {
                return doResponse(400, id, "Engine [" + engine
                        + "] does not support this operation");
            }
            if (id < 0) {
                return doResponse(400, id, "Invalid namespace [" + namespace + "]!");
            }
            return doResponse(200, id, "Successful");
        } catch (Exception e) {
            final String logMsg = "Exception [" + e.getClass() + "]: " + e.getMessage();
            Logger.error(logMsg, e);
            return doResponse(500, -1, logMsg);
        }
    }

    private static DataPoint[] buildCounterData(ICounter counter, long timestamp) {
        long last1Min = timestamp - 60 * 1000L;
        long last5Mins = timestamp - 5 * 60 * 1000L;
        long last15Mins = timestamp - 15 * 60 * 1000L;
        DataPoint[] result = new DataPoint[] {
                new DataPoint(DataPoint.Type.SUM, last1Min, 0, ICounter.STEPS_1_MIN * 1000),
                new DataPoint(DataPoint.Type.SUM, last5Mins, 0, ICounter.STEPS_5_MINS * 1000),
                new DataPoint(DataPoint.Type.SUM, last15Mins, 0, ICounter.STEPS_15_MINS * 1000) };
        if (counter == null) {
            return result;
        }

        DataPoint[] tempArr = counter.getSeries(last1Min, timestamp);
        for (DataPoint dp : tempArr) {
            result[0].add(dp);
        }
        tempArr = counter.getSeries(last5Mins, timestamp);
        for (DataPoint dp : tempArr) {
            result[1].add(dp);
        }
        tempArr = counter.getSeries(last15Mins, timestamp);
        for (DataPoint dp : tempArr) {
            result[2].add(dp);
        }
        return result;
    }

    /*
     * Handle: GET:/index
     */
    public static Result index() throws Exception {
        Map<String, DataPoint[]> statsLocal = new HashMap<String, DataPoint[]>();
        Map<String, DataPoint[]> statsGlobal = new HashMap<String, DataPoint[]>();
        Map<String, Long> countersLocal = new HashMap<String, Long>();
        Map<String, Long> countersGlobal = new HashMap<String, Long>();
        ICounterFactory localCounterFactory = Registry.getLocalCounterFactory();
        ICounterFactory globalCounterFactory = Registry.getGlobalCounterFactory();

        long timestamp = System.currentTimeMillis();
        String[] tscNames = new String[] { Registry.TSC_TOTAL, Registry.TSC_SUCCESSFUL,
                Registry.TSC_FAILED_ENGINE, Registry.TSC_FAILED_NAMESPACE };
        for (String name : tscNames) {
            statsLocal.put(
                    name,
                    buildCounterData(
                            localCounterFactory != null ? localCounterFactory.getCounter(name)
                                    : null, timestamp));
            statsGlobal.put(
                    name,
                    buildCounterData(
                            globalCounterFactory != null ? globalCounterFactory.getCounter(name)
                                    : null, timestamp));
        }

        String[] counterNames = new String[] { Registry.COUNTER_TOTAL, Registry.COUNTER_SUCCESSFUL,
                Registry.COUNTER_FAILED_ENGINE, Registry.COUNTER_FAILED_NAMESPACE };
        for (String name : counterNames) {
            ICounter counter = localCounterFactory != null ? localCounterFactory.getCounter(name)
                    : null;
            DataPoint dp = counter != null ? counter.get(0) : null;
            long value = dp != null ? dp.value() : 0;
            countersLocal.put(name, value);

            counter = globalCounterFactory != null ? globalCounterFactory.getCounter(name) : null;
            dp = counter != null ? counter.get(0) : null;
            value = dp != null ? dp.value() : 0;
            countersGlobal.put(name, value);
        }
        
        long[] concurrency = Registry.getConcurrency();

        Html html = render("index", concurrency, countersLocal, statsLocal, countersGlobal, statsGlobal);
        return ok(html);
    }
}
