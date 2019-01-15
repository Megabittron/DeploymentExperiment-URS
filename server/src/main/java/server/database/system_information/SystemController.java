package server.database.system_information;

import com.google.gson.Gson;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.print.Doc;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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


    public String getAllSystemInformation() {
        Document systemInformationDocument = new Document();
        Document filterDoc = new Document();
        Document reviewGroups = new Document();
        Document reviewGroup = new Document();
        Document reviewGroup2 = new Document();
        reviewGroup.append("users", usersCollection.find().limit(4));
        reviewGroup2.append("users", usersCollection.find().limit(2));

        List<Document> array = new ArrayList<>();
        array.add(reviewGroup);
        array.add(reviewGroup2);

        filterDoc.append("isPrimarySubmission", true);
        systemInformationDocument.append("primarySubmissions", abstractCollection.count(filterDoc));
        systemInformationDocument.append("submissionStored", abstractCollection.count());

        filterDoc.clear();
        filterDoc.append("resubmitFlag", true);
        systemInformationDocument.append("submissionsFlagged", abstractCollection.count(filterDoc));
        systemInformationDocument.append("totalUsers", usersCollection.count());

        filterDoc.clear();
        filterDoc.append("Role", "user");
        systemInformationDocument.append("users", usersCollection.count(filterDoc));

        filterDoc.append("Role", "admin");
        systemInformationDocument.append("admins", usersCollection.count(filterDoc));

        filterDoc.append("Role", "chair");
        systemInformationDocument.append("chairs", usersCollection.count(filterDoc));

        filterDoc.append("Role", "reviewer");
        systemInformationDocument.append("reviewers", usersCollection.count(filterDoc));

        systemInformationDocument.append("reviewGroups", array);

        filterDoc.clear();
        filterDoc.append("ShirtSize", "xs");
        systemInformationDocument.append("xs", usersCollection.count(filterDoc));

        filterDoc.clear();
        filterDoc.append("ShirtSize", "s");
        systemInformationDocument.append("s", usersCollection.count(filterDoc));

        filterDoc.clear();
        filterDoc.append("ShirtSize", "m");
        systemInformationDocument.append("m", usersCollection.count(filterDoc));

        filterDoc.clear();
        filterDoc.append("ShirtSize", "l");
        systemInformationDocument.append("l", usersCollection.count(filterDoc));

        filterDoc.clear();
        filterDoc.append("ShirtSize", "xl");
        systemInformationDocument.append("xl", usersCollection.count(filterDoc));

        filterDoc.clear();
        filterDoc.append("ShirtSize", "xxl");
        systemInformationDocument.append("xxl", usersCollection.count(filterDoc));

        return JSON.serialize(systemInformationDocument);
    }
}
