package server.database.system_information;

import spark.Request;
import spark.Response;

public class SystemRequestHandler {

    private final SystemController systemController;
    public SystemRequestHandler(SystemController systemController){
        this.systemController = systemController;
    }

    /**Method called from Server when the 'api/users' endpoint is received.
     * This handles the request received and the response
     * that will be sent back.
     *@param req the HTTP request
     * @param res the HTTP response
     * @return an array of users in JSON formatted String
     */
    public String getSystemInformation(Request req, Response res)
    {
        res.type("application/json");
        return systemController.getAllSystemInformation();
    }
}
