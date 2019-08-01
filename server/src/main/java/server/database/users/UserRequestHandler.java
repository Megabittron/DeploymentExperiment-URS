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

    public String getUserBySubjectID(Request req, Response res){
        res.type("application/json");
        String id = req.params("subjectID");
        String user;
        try {
            user = userController.getUserBySub(id);
        } catch (IllegalArgumentException e) {
            // This is thrown if the ID doesn't have the appropriate
            // form for a Mongo Object ID.
            // https://docs.mongodb.com/manual/reference/method/ObjectId/
            res.status(400);
            res.body("The requested user id " + id + " wasn't a legal Mongo Object ID.\n" +
                "See 'https://docs.mongodb.com/manual/reference/method/ObjectId/' for more info.");
            return "";
        }
        if (user != null) {
            return user;
        } else {
            res.status(404);
            res.body("The requested user with id " + id + " was not found");
            return "";
        }
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
