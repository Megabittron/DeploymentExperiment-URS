package server.database.login;

import com.mongodb.util.JSON;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

/**
 *
 */
public class LoginRequestHandler {

    private final LoginController loginController;

    public LoginRequestHandler(LoginController loginController) {
        this.loginController = loginController;
    }

    /**
     * Method called from Server when the 'api/login' endpoint is received.
     * Get a JSON response with a list of all the users in the database.
     *
     * @param req the HTTP request
     * @param res the HTTP response
     * @return one user in JSON formatted string and if it fails it will return text with a different HTTP status code
     */

    public String loginUser(Request req, Response res) {
        res.type("application/json");
        String idTokenString = req.params("token");
        String verifyResponse = loginController.verifyIdToken(idTokenString);

        return verifyResponse;

    }
}
