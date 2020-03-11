package server.database.users;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import spark.Request;
import spark.Response;

public class UserRequestHandler {

    private final UserController userController;

    public UserRequestHandler(UserController userController) {
        this.userController = userController;
    }

    /**
     * Method called from Server when the 'api/users' endpoint is received.
     *
     * @param req the HTTP request
     * @param res the HTTP response
     * @return an array of users in a JSON formatted String
     */
    public String getUsers(Request req, Response res) {
        res.type("application/json");
        return userController.getUsers(req.queryMap().toMap());
    }

    public String editUsertShirtSize(Request req, Response res) {
        res.type("application/json");
        BasicDBObject dbO = BasicDBObject.parse(req.body());

        try {
            String userID = dbO.getString("_id");
            String tShirtSize = dbO.getString("ShirtSize");

            return userController.editUsertShirtSize(userID, tShirtSize);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String editUserRole(Request req, Response res) {
        res.type("application/json");
        BasicDBObject dbO = BasicDBObject.parse(req.body());

        try {
            String userID = dbO.getString("_id");
            String role = dbO.getString("Role");

            return userController.editUserRole(userID, role);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
