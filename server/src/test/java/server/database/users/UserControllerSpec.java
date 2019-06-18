package server.database.users;

import com.mongodb.util.JSON;
import server.database.users.UserController;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.*;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

//Test code borrowed from https://github.com/UMM-CSci-3601-S19/iteration-3-toon-squad 's UserControllerSpec
public class UserControllerSpec {
    private UserController userController;
    private ObjectId knownId;

    @Before
    public void clearAndPopulateDB() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("test");
        MongoCollection<Document> userDocuments = db.getCollection("users");
        userDocuments.drop();
        List<Document> testUsers = new ArrayList<>();
        testUsers.add(Document.parse("{\n" +
            "                    _id: \"5b199522a21a8c67f0ec85b1\",\n" +
            "                    SubjectID: \"default\",\n" +
            "                    FirstName: \"Jack\",\n" +
            "                    LastName: \"Imholte\",\n" +
            "                    tShirtSize: \"XXL\",\n" +
            "                    role: \"admin\",\n" +
            "                }"));
        testUsers.add(Document.parse("{\n" +
            "                    _id: \"5b199522a21a8c67f0ec85b2\",\n" +
            "                    SubjectID: \"default\",\n" +
            "                    FirstName: \"Mike\",\n" +
            "                    LastName: \"Jones\",\n" +
            "                    tShirtSize: \"small\",\n" +
            "                    role: \"user\",\n" +
            "                }"));
        testUsers.add(Document.parse("{\n" +
            "                    _id: \"5b199522a21a8c67f0ec85b3\",\n" +
            "                    SubjectID: \"default\",\n" +
            "                    FirstName: \"John\",\n" +
            "                    LastName: \"Hammer\",\n" +
            "                    tShirtSize: \"large\",\n" +
            "                    role: \"chair\",\n" +
            "                }"));
        testUsers.add(Document.parse("{\n" +
            "                    _id: \"5b199522a21a8c67f0ec85b4\",\n" +
            "                    SubjectID: \"default\",\n" +
            "                    FirstName: \"Marge\",\n" +
            "                    LastName: \"Moride\",\n" +
            "                    tShirtSize: \"medium\",\n" +
            "                    role: \"reviewer\",\n" +
            "                }"));
        userDocuments.insertMany(testUsers);

        knownId = new ObjectId();
        BasicDBObject knownObj = new BasicDBObject("_id", knownId);
        knownObj = knownObj
            .append("SubjectID","default")
            .append("FirstName","James")
            .append("LastName","Jim")
            .append("tShirtSize","small")
            .append("role","reviewer");
        userDocuments.insertOne(Document.parse(knownObj.toJson()));
        userController = new UserController(db);
    }

    @Test
    public void getUser() {
        String desiredUser = userController.getUser("5b199522a21a8c67f0ec85b1");
        assertNotNull("getUser should have found a user ", desiredUser);
        System.out.println("Desired user: " + desiredUser);

        String nonExistingUser = userController.getUser("6b199532a21a8c76f0ec85b9");
        assertEquals("[ ]", nonExistingUser); //TODO: Please look over the assertion syntax
    }
}
