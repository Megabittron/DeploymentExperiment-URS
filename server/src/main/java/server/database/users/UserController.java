package server.database.users;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.mongodb.client.model.Filters.in;

public class UserController {

    private final MongoCollection<Document> userCollection;

    public UserController(MongoDatabase database) {
        userCollection = database.getCollection("users");
    }

    /**
     * Helper method that gets a single user specified by the `subjectID`
     * //     * parameter in the request.
     * //     *
     * //     * @param subjectID the Google subjectID of the desired user
     * //     * @return the desired user as a JSON object if the user with that subjectID is found,
     * //     * and `null` if no user with that subjectID is found
     * //
     */
    public String getUserBySub(String subjectID) {
        Document filterDoc = new Document();
        filterDoc.append("SubjectID", subjectID);

        FindIterable<Document> user = userCollection.find(filterDoc).limit(1);

        String userJSON = "";

        for (Document doc: user) {
            userJSON = doc.toJson();
        }

        return userJSON;
    }

    /**
     * Helper method which iterates through the collection, receiving all
     * //     * documents if no query parameter is specified. If the SubjectID query parameter
     * //     * is specified, then the collection is filtered so only documents of that
     * //     * specified SubjectID are found.
     * //     *
     * //     * @param queryParams
     * //     * @return an array of Users in a JSON formatted string
     * //
     */
    String getUsers(Map<String, String[]> queryParams) {

        Document filterDoc = new Document();

        if (queryParams.containsKey("SubjectID")) {
            String targetAge = queryParams.get("SubjectID")[0];
            filterDoc = filterDoc.append("SubjectID", targetAge);
        }

        if (queryParams.containsKey("FirstName")) {
            String targetContent = (queryParams.get("FirstName")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("FirstName", contentRegQuery);
        }

        FindIterable<Document> matchingUsers = userCollection.find(filterDoc);

        return serializeIterable(matchingUsers);
    }

    public static String serializeIterable(Iterable<Document> documents) {
        return StreamSupport.stream(documents.spliterator(), false)
            .map(Document::toJson)
            .collect(Collectors.joining(", ", "[", "]"));
    }

    public String addNewUser(String subjectID, String firstName, String lastName, String email) {

        Document newUser = new Document();
        newUser.append("SubjectID", subjectID);
        newUser.append("FirstName", firstName);
        newUser.append("LastName", lastName);
        newUser.append("ShirtSize", "");
        newUser.append("Role", "user");
        newUser.append("Email", email);

        try {
            userCollection.insertOne(newUser);
            ObjectId id = newUser.getObjectId("_id");
            System.err.println("Successfully added new user [_id= " + id + ", SubjectID= " + subjectID + ", FirstName= " + firstName + ", LastName= " + lastName + ", Email=" + email + "]");

            return new BasicDBObject("_id", id).toJson();
        } catch (MongoException e) {
            e.printStackTrace();
            return null;
        }
    }

    String editUsertShirtSize(String userID, String size) {

        Document newtShirtSize = new Document();
        newtShirtSize.append("ShirtSize", size);

        return updateUserField(size, userID, newtShirtSize, "ShirtSize");
    }

    String editUserRole(String userID, String position) {

        Document newrole = new Document();
        newrole.append("Role", position);

        return updateUserField(position, userID, newrole, "Role");
    }

    private String updateUserField(String field, String id, Document updateDoc, String fieldName) {
        Document setQuery = new Document();
        Document searchQuery = new Document();

        setQuery.append("$set", updateDoc);

        try {
            searchQuery.append("_id", new ObjectId(id));
            userCollection.updateOne(searchQuery, setQuery);

            if(fieldName.equals("Role")){
                return new BasicDBObject("Role",field).toJson();
            } else if (fieldName.equals("ShirtSize")) {
                return new BasicDBObject("ShirtSize",field).toJson();
            } else {
                return "Unknown update to user field";
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();

            return new Document().toJson();
        } catch (MongoException e) {
            e.printStackTrace();

            return null;
        }
    }

}
