package server.database.system_information;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;

public class SystemController {

    private final MongoCollection<Document> abstractCollection;
    private final MongoCollection<Document> usersCollection;
    private final MongoCollection<Document> reviewGroupCollection;

    private final JsonWriterSettings relaxed = JsonWriterSettings.builder().outputMode(JsonMode.RELAXED).build();


    // Construct controller for user.
    public SystemController(MongoDatabase database) {
        abstractCollection = database.getCollection("abstracts");
        usersCollection = database.getCollection("users");
        reviewGroupCollection = database.getCollection("reviewGroups");
    }


    String getAllSystemInformation() {
        Document systemInformationDocument = new Document();
        Document filterDoc = new Document();

        filterDoc.append("isPrimarySubmission", true);
        systemInformationDocument.append("primarySubmissions", abstractCollection.countDocuments(filterDoc));
        systemInformationDocument.append("submissionStored", abstractCollection.countDocuments());

        filterDoc.clear();
        filterDoc.append("resubmitFlag", true);
        systemInformationDocument.append("submissionsFlagged", abstractCollection.countDocuments(filterDoc));
        systemInformationDocument.append("totalUsers", usersCollection.countDocuments());

        filterDoc.clear();
        filterDoc.append("Role", "user");
        systemInformationDocument.append("users", usersCollection.countDocuments(filterDoc));

        filterDoc.append("Role", "admin");
        systemInformationDocument.append("admins", usersCollection.countDocuments(filterDoc));

        filterDoc.append("Role", "chair");
        systemInformationDocument.append("chairs", usersCollection.countDocuments(filterDoc));

        filterDoc.append("Role", "reviewer");
        systemInformationDocument.append("reviewers", usersCollection.countDocuments(filterDoc));

        systemInformationDocument.append("reviewGroups", reviewGroupCollection.find());

        filterDoc.clear();
        filterDoc.append("ShirtSize", "xs");
        systemInformationDocument.append("xs", usersCollection.countDocuments(filterDoc));

        filterDoc.clear();
        filterDoc.append("ShirtSize", "s");
        systemInformationDocument.append("s", usersCollection.countDocuments(filterDoc));

        filterDoc.clear();
        filterDoc.append("ShirtSize", "m");
        systemInformationDocument.append("m", usersCollection.countDocuments(filterDoc));

        filterDoc.clear();
        filterDoc.append("ShirtSize", "l");
        systemInformationDocument.append("l", usersCollection.countDocuments(filterDoc));

        filterDoc.clear();
        filterDoc.append("ShirtSize", "xl");
        systemInformationDocument.append("xl", usersCollection.countDocuments(filterDoc));

        filterDoc.clear();
        filterDoc.append("ShirtSize", "xxl");
        systemInformationDocument.append("xxl", usersCollection.countDocuments(filterDoc));

        return systemInformationDocument.toJson(relaxed);
    }
}
