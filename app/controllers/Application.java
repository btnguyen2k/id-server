package controllers;

import globals.Registry;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import play.mvc.Result;
import util.Constants;
import api.IdApi;

import com.github.ddth.commons.utils.SerializationUtils;

public class Application extends BaseController {

    public static final Charset UTF8 = Charset.forName("UTF-8");

    private static Result doResponse(int status, Object message) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constants.RESPONSE_FIELD_STATUS, status);
        result.put(Constants.RESPONSE_FIELD_MESSAGE, message);
        response().setHeader(CONTENT_TYPE, "application/json");
        response().setHeader(CONTENT_ENCODING, "utf-8");
        return ok(SerializationUtils.toJsonString(result));
    }

    public static Result nextId(final String namespace, final String engine) {
        IdApi idApi = Registry.getIdApi();
        try {
            long id = idApi.nextId(engine, namespace);
            if (id < 0) {
                return doResponse(400, "Invalid namespace [" + namespace + "]!");
            }
            if (id == 0) {
                return doResponse(404, "Engine not supported [" + engine + "]!");
            }
            return doResponse(200, id);
        } catch (Exception e) {
            final String logMsg = "Exception [" + e.getClass() + "]: " + e.getMessage();
            return doResponse(500, logMsg);
        }
    }

    /*
     * Handle: GET:/index
     */
    public static Result index() throws Exception {
        return ok("Test");
    }
}
