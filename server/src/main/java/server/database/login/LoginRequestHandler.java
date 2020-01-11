package server.database.login;

import com.mongodb.BasicDBObject;
import server.database.enums.Role;
import spark.Request;
import spark.Response;

public class LoginRequestHandler {

    private final LoginController loginController;

    public LoginRequestHandler(LoginController loginController) {
        this.loginController = loginController;
    }

    /**
     * Method called by 'api/login' endpoint.
     *
     * @param req HTTP request
     * @param res HTTP response
     * @return One user as a JSON formatted string
     */

    public String loginUser(Request req, Response res) {
        res.type("application/json");

        String idTokenString;
        String verifyResponse;

        try {
            BasicDBObject tokenJSON = BasicDBObject.parse(req.body());
            idTokenString = tokenJSON.getString("id_token");

            verifyResponse = loginController.verifyIdToken(idTokenString);

            if (verifyResponse != null) {

                BasicDBObject userObject = BasicDBObject.parse(verifyResponse);

                req.session().attribute("isSignedIn", true);
                req.session().attribute("Role", Role.valueOfRole(userObject.getString("Role")));
                req.session().attribute("SubjectID", userObject.getString("SubjectID"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return verifyResponse;
    }

    public String logoutUser(Request req, Response res) {
        req.session().invalidate();

        return "OK";
    }
}
