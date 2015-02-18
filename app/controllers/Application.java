package controllers;

import globals.Registry;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.github.ddth.commons.utils.SerializationUtils;

import play.mvc.Result;
import util.Constants;
import api.IdApi;

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

    public static Result nextId(final String namespace, final String engine) {
        IdApi idApi = Registry.getIdApi();
        try {
            long id = idApi.nextId(engine, namespace);
            if (id < 0) {
                return doResponse(400, id, "Invalid namespace [" + namespace + "]!");
            }
            if (id == 0) {
                return doResponse(404, id, "Engine not supported [" + engine + "]!");
            }
            return doResponse(200, id, "Successful");
        } catch (Exception e) {
            final String logMsg = "Exception [" + e.getClass() + "]: " + e.getMessage();
            return doResponse(500, -1, logMsg);
        }
    }

    /*
     * Handle: GET:/index
     */
    public static Result index() throws Exception {
        return ok("Test");
    }
}
