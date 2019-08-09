package server.database.login;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import server.database.users.UserController;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.Properties;

public class LoginController {

    private final UserController userController;

    public LoginController(UserController userController) {
        this.userController = userController;
    }


    String verifyIdToken(String idTokenString) {
        String CLIENT_SECRET = getClientSecret();

        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        try {
            GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(
                    JacksonFactory.getDefaultInstance(), new StringReader(CLIENT_SECRET));

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientSecrets.getDetails().getClientId()))
                .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                if (payload.getHostedDomain().equals("morris.umn.edu")) {
                    String subjectID = payload.getSubject();
                    String user = userController.getUserBySub(subjectID);
                    String firstName = (String) payload.get("given_name");
                    String lastName = (String) payload.get("family_name");

                    if (user.equals("")) {
                        userController.addNewUser(subjectID, firstName, lastName);
                        user = userController.getUserBySub(payload.getSubject());
                    }

                    return user;
                }
            } else {
                System.out.println("Invalid ID token.");
            }
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }


        return null;
    }

    private String getClientSecret() {
        Properties properties = new Properties();
        try {
            properties.load(LoginController.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            return "IOException Occurred";
        }

        return properties.getProperty("CLIENT_SECRET");
    }
}
