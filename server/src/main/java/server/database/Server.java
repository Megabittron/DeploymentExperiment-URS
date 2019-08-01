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
import static spark.Spark.*;

public class Server {
    private static final String databaseName = "dev";
    private static final int serverPort = 4567;

    public static void main(String[] args) {

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

        options("/*", (req, res) -> {

            String accessControlRequestHeaders = req.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                res.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = req.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                res.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((req, res) -> {
            String reqMethod = req.requestMethod();
            String reqURI = req.uri();
            boolean authenticated;

            res.header("Access-Control-Allow-Origin", "http://localhost:9000");
            res.header("Access-Control-Allow-Credentials", "true");

            if (!reqMethod.equals("OPTIONS") && !reqURI.equals("/api/login")) {
                authenticated = req.session().attribute("isSignedIn") != null;
                
                if (!authenticated) {
                    halt(401, "You are not welcome here");
                }
            }
        });


        get("api/users", userRequestHandler::getUsers);
        get("api/users/:_id", userRequestHandler::getUserJSON);
        put("api/users/:_id", userRequestHandler::editUsertShirtSize);

        get("api/abstracts", abstractRequestHandler::getAbstracts);
        get("api/abstracts/:id", abstractRequestHandler::getAbstractJSON);
        post("api/abstracts/new", abstractRequestHandler::addNewAbstract);

        post("api/login", loginRequestHandler::loginUser);
        post("api/logout", loginRequestHandler::logoutUser);

        get("api/system-information", systemRequestHandler::getSystemInformation);


        after("*", Server::addGzipHeader);

        // Handle "404" file not found requests:
        notFound((req, res) -> {
            res.type("text");
            res.status(404);
            return "Sorry, we couldn't find that!!";
        });

    }

    // Enable GZIP for all responses
    private static void addGzipHeader(Request req, Response res) {
        res.header("Content-Encoding", "gzip");
    }
}
