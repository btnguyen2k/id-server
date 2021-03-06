package thrift;

import globals.Registry;

import org.apache.thrift.TException;

import play.Logger;
import api.IdApi;

import com.github.btnguyen2k.idserver.thrift.TIdResponse;
import com.github.btnguyen2k.idserver.thrift.TIdService;

public class TIdServiceImpl implements TIdService.Iface {

    public final static TIdServiceImpl instance = new TIdServiceImpl();

    /**
     * {@inheritDoc}
     */
    @Override
    public void ping() throws TException {
        Registry.incConcurrency();
        try {
        } finally {
            Registry.decConcurrency();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean ping2() throws TException {
        Registry.incConcurrency();
        try {
            return true;
        } finally {
            Registry.decConcurrency();
        }
    }

    private static TIdResponse doResponse(int status, long id, String message) {
        TIdResponse response = new TIdResponse(status, id, message);
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TIdResponse nextId(String _namespace, String _engine) throws TException {
        IdApi idApi = Registry.getIdApi();
        try {
            long id = idApi.nextId(_engine, _namespace);
            if (id == 0) {
                return doResponse(404, id, "Engine not supported [" + _engine + "]!");
            }
            if (id < 0) {
                return doResponse(400, id, "Invalid namespace [" + _namespace + "]!");
            }
            return doResponse(200, id, "Successful");
        } catch (Exception e) {
            final String logMsg = "Exception [" + e.getClass() + "]: " + e.getMessage();
            Logger.error(logMsg, e);
            return doResponse(500, -1, logMsg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TIdResponse currentId(String _namespace, String _engine) throws TException {
        IdApi idApi = Registry.getIdApi();
        try {
            long id = idApi.currentId(_engine, _namespace);
            if (id == -2) {
                return doResponse(400, id, "Engine [" + _engine
                        + "] does not support this operation");
            }
            if (id < 0) {
                return doResponse(400, id, "Invalid namespace [" + _namespace + "]!");
            }
            return doResponse(200, id, "Successful");
        } catch (Exception e) {
            final String logMsg = "Exception [" + e.getClass() + "]: " + e.getMessage();
            Logger.error(logMsg, e);
            return doResponse(500, -1, logMsg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TIdResponse setValue(String _namespace, long _value, String _engine) throws TException {
        IdApi idApi = Registry.getIdApi();
        try {
            boolean result = idApi.setValue(_engine, _namespace, _value);
            if (result) {
                return doResponse(200, _value, "Successful");
            } else {
                return doResponse(400, -1, "Failed");
            }
        } catch (Exception e) {
            final String logMsg = "Exception [" + e.getClass() + "]: " + e.getMessage();
            Logger.error(logMsg, e);
            return doResponse(500, -1, logMsg);
        }
    }

}
