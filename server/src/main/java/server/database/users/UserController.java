package server.database.users;

import com.google.gson.Gson;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Iterator;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class UserController {

    private final Gson gson;
    private MongoDatabase database;

    // userCollection is the collection that the users data is in.

    private final MongoCollection<Document> userCollection;

    // Construct controller for user.
    public UserController(MongoDatabase database) {
        gson = new Gson();
        this.database = database;
        userCollection = database.getCollection("users");
    }

    /**  Helper method that gets a single user specified by the `id`
     //     * parameter in the request.
     //     *
     //     * @param id the Mongo ID of the desired user
     //     * @return the desired user as a JSON object if the user with that ID is found,
     //     * and `null` if no user with that ID is found
     //     */
    public String getUser(String id) {
        ObjectId idToFind = new ObjectId(id);

        FindIterable<Document> user = userCollection.find(eq("_id", idToFind));

        return JSON.serialize(user);
    }

    /**  Helper method that gets a single user specified by the `id`
     //     * parameter in the request.
     //     *
     //     * @param id the Mongo ID of the desired user
     //     * @return the desired user as a JSON object if the user with that ID is found,
     //     * and `null` if no user with that ID is found
     //     */
    public String getUserBySub(String subjectID) {
        Document filterDoc = new Document();
        filterDoc.append("SubjectID", subjectID);

        FindIterable<Document> user = userCollection.find(filterDoc);

        return JSON.serialize(user);
    }

    /** Helper method which iterates through the collection, receiving all
     //     * documents if no query parameter is specified. If the SubjectID query parameter
     //     * is specified, then the collection is filtered so only documents of that
     //     * specified SubjectID are found.
     //     *
     //     * @param queryParams
     //     * @return an array of Users in a JSON formatted string
     //     */
    public String getUsers(Map<String, String[]> queryParams) {

        Document filterDoc = new Document();

        if (queryParams.containsKey("SubjectID")) {
            int targetAge = Integer.parseInt(queryParams.get("SubjectID")[0]);
            filterDoc = filterDoc.append("SubjectID", targetAge);
        }

        if (queryParams.containsKey("FirstName")) {
            String targetContent = (queryParams.get("FirstName")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("company", contentRegQuery);
        }

        //FindIterable comes from mongo, Document comes from Gson

        FindIterable<Document> matchingUsers = userCollection.find(filterDoc);

        return JSON.serialize(matchingUsers);
    }

    public String addNewUser(String subjectID, String firstName, String lastName) {


        // by default a user is given an empty tshirt size and the role of a user
        Document newUser = new Document();
        newUser.append("SubjectID", subjectID);
        newUser.append("FirstName", firstName);
        newUser.append("LastName", lastName);
        newUser.append("ShirtSize", "");
        newUser.append("Role", "user");

        try {
            userCollection.insertOne(newUser);
            ObjectId id = newUser.getObjectId("_id");
            System.err.println("Successfully added new user [_id= " + id + ", SubjectID= " + subjectID + ", FirstName= " + firstName + ", LastName= " + lastName + "]");

            return JSON.serialize(id);
        } catch(MongoException me) {
            me.printStackTrace();
            return null;
        }
    }

    //edits user t-shirt size settings
    public String editUsertShirtSize(String userID, String size) {

        Document filterDoc = new Document();
        String id;

        if (!(userID == "")) {
            id = userID;
        } else {
            return JSON.serialize("[ ]");
        }

        Document newtShirtSize = new Document();
        newtShirtSize.append("tShirtSize", size);

        Document setQuery = new Document();
        setQuery.append("$set", newtShirtSize);

        //Document searchQuery = new Document().append("_id", new ObjectId(id));
        Document searchQuery = new Document().append("_id", new ObjectId(id));

        try {
            userCollection.updateOne(searchQuery, setQuery);
            return JSON.serialize(size);
        } catch(MongoException me) {
            me.printStackTrace();
            return null;
        }
    }
    // changing a user's role setting
    public String editUserrole(String userID, String position) {

        Document filterDoc = new Document();
        String id;

        if (!(userID == "")) {
            id = userID;
        } else {
            return JSON.serialize("[ ]");
        }

        Document newrole = new Document();
        newrole.append("role", position);

        Document setQuery = new Document();
        setQuery.append("$set", newrole);

        //Document searchQuery = new Document().append("_id", new ObjectId(id));
        Document searchQuery = new Document().append("_id", new ObjectId(id));

        try {
            userCollection.updateOne(searchQuery, setQuery);

            return JSON.serialize(position);
        } catch(MongoException me) {
            me.printStackTrace();
            return null;
        }
    }

}
