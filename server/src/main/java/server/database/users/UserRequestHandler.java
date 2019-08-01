package server.database.users;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import spark.Request;
import spark.Response;

public class UserRequestHandler {

    private final UserController userController;
    public UserRequestHandler(UserController userController){
        this.userController = userController;
    }

    /**Method called from Server when the 'api/users' endpoint is received.
     *
     *@param req the HTTP request
     * @param res the HTTP response
     * @return an array of users in a JSON formatted String
     */
    public String getUsers(Request req, Response res)
    {
        res.type("application/json");
        return userController.getUsers(req.queryMap().toMap());
    }

    public String editUsertShirtSize(Request req, Response res){
        res.type("application/json");
        Object o = JSON.parse(req.body());

        try {
            if(o.getClass().equals(BasicDBObject.class))
            {
                try {
                    BasicDBObject dbO = (BasicDBObject) o;

                    String userID = dbO.getString("_id");
                    String tShirtSize = dbO.getString("ShirtSize");

                    return userController.editUsertShirtSize(userID, tShirtSize);
                }
                catch(NullPointerException e)
                {
                    return null;
                }

            }
            else
            {
                System.err.println("Expected BasicDBObject, received " + o.getClass());
                return null;
            }
        }
        catch(RuntimeException ree)
        {
            ree.printStackTrace();
            return null;
        }


    }

    public String editUserrole(Request req, Response res){
        res.type("application/json");
        Object o = JSON.parse(req.body());

        try {
            if(o.getClass().equals(BasicDBObject.class))
            {
                try {
                    BasicDBObject dbO = (BasicDBObject) o;

                    String userID = dbO.getString("userID");
                    String role = dbO.getString("position");

                    System.out.println("userID: " + userID);
                    System.out.println("position: " + role);

                    return userController.editUserrole(userID, role);
                }
                catch(NullPointerException e)
                {
                    return null;
                }

            }
            else
            {
                System.err.println("Expected BasicDBObject, received " + o.getClass());
                return null;
            }
        }
        catch(RuntimeException ree)
        {
            ree.printStackTrace();
            return null;
        }
    }
}
