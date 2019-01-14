package server.database.system_information;

import com.google.gson.Gson;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.util.Iterator;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class SystemController {

    private final Gson gson;
    private MongoDatabase database;

    private final MongoCollection<Document> abstractCollection;
    private final MongoCollection<Document> usersCollection;
    private final MongoCollection<Document> reviewGroupCollection;


    // Construct controller for user.
    public SystemController(MongoDatabase database) {
        gson = new Gson();
        this.database = database;
        abstractCollection = database.getCollection("abstracts");
        usersCollection = database.getCollection("users");
        reviewGroupCollection = database.getCollection("reviewGroups");
    }

    /**  Helper method that gets a single user specified by the `id`
     //     * parameter in the request.
     //     *
     //     * @param id the Mongo ID of the desired user
     //     * @return the desired user as a JSON object if the user with that ID is found,
     //     * and `null` if no user with that ID is found
     //     */
    public String getAllSystemInformation() {
        Document systemInformationDocument = new Document();
        systemInformationDocument.append("primarySubmissions", 5);

        //FindIterable<Document> user = userCollection.find(eq("_id", idToFind));

        return JSON.serialize(systemInformationDocument);
    }
}
