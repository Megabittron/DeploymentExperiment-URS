package server.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import server.database.system_information.SystemController;
import server.database.system_information.SystemRequestHandler;
import server.database.users.UserRequestHandler;
import server.database.users.UserController;
import server.database.abstracts.AbstractController;
import server.database.abstracts.AbstractRequestHandler;
import server.database.login.LoginController;
import server.database.login.LoginRequestHandler;
import spark.Request;
import spark.Response;
import spark.Route;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import static spark.Spark.*;

public class Server {
    private static final String databaseName = "dev";
    private static final int serverPort = 4567;

    public static void main(String[] args) throws IOException {

        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase(databaseName);

        UserController userController = new UserController(database);
        UserRequestHandler userRequestHandler = new UserRequestHandler(userController);

        AbstractController abstractController = new AbstractController(database);
        AbstractRequestHandler abstractRequestHandler = new AbstractRequestHandler(abstractController);

        LoginController loginController = new LoginController(userController);
        LoginRequestHandler loginRequestHandler = new LoginRequestHandler(loginController);

        SystemController systemController = new SystemController(database);
        SystemRequestHandler systemRequestHandler = new SystemRequestHandler(systemController);

        port(serverPort);

        // Specify where assets like images will be "stored"
        staticFiles.location("/public");

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            String requestMethod = request.requestMethod();
            String requestURI = request.uri();
            boolean authenticated;

            response.header("Access-Control-Allow-Origin", "http://localhost:9000");
            response.header("Access-Control-Allow-Credentials", "true");

            if (!requestMethod.equals("OPTIONS") && !requestURI.equals("/api/login")) {
                authenticated = request.session().attribute("isSignedIn") != null;

                if (!authenticated) {
                    halt(401, "You are not welcome here");
                }
            }
        });
        
        redirect.get("", "/");

        Route clientRoute = (req, res) -> {
            InputStream stream = userController.getClass().getResourceAsStream("/public/index.html");
            return stream != null ? IOUtils.toString(stream) : "Sorry, we couldn't find that!";
        };

        Route notFoundRoute = (req, res) -> {
            res.type("text");
            res.status(404);
            return "Sorry, we couldn't find that!!";
        };

        get("/", clientRoute);


        get("api/users", userRequestHandler::getUsers);
        get("api/users/:_id", userRequestHandler::getUserJSON);
        put("api/users/:_id", userRequestHandler::editUsertShirtSize);

        get("api/abstracts", abstractRequestHandler::getAbstracts);
        get("api/abstracts/:id", abstractRequestHandler::getAbstractJSON);
        post("api/abstracts/new", abstractRequestHandler::addNewAbstract);

        post("api/login", loginRequestHandler::loginUser);
        post("api/logout", loginRequestHandler::logoutUser);

        get("api/system-information", systemRequestHandler::getSystemInformation);

        get("api/error", (req, res) -> {
            throw new RuntimeException("A demonstration error");
        });

        // Called after each request to insert the GZIP header into the response.
        // This causes the response to be compressed _if_ the client specified
        // in their request that they can accept compressed responses.
        after("*", Server::addGzipHeader);

        get("api/*", notFoundRoute);

        get("/*", clientRoute);

        // Handle "404" file not found requests:
        notFound(notFoundRoute);

    }

    // Enable GZIP for all responses
    private static void addGzipHeader(Request request, Response response) {
        response.header("Content-Encoding", "gzip");
    }
}
