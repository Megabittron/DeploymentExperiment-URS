package server.database.system_information;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;

import static com.mongodb.client.model.Filters.*;

public class SystemController {

    private final MongoCollection<Document> abstractCollection;
    private final MongoCollection<Document> usersCollection;
    private MongoCollection<Document> reviewGroupCollection;

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

    public String getReviewGroups() {
        Document reviewGroupsDocument = new Document();

        reviewGroupsDocument.append("reviewGroups", reviewGroupCollection.find());

        return JSON.serialize(reviewGroupsDocument);
    }

    Boolean editReviewGroups(BasicDBList changedReviewGroups) {

        try {
            for(int i = 0; i < changedReviewGroups.size(); i++) { //for each review group
                Object reviewGroupObject = changedReviewGroups.get(i); //get a particular group

                if(reviewGroupObject.getClass().equals(BasicDBObject.class)) {
                    try {
                        //cast it as a BasicDBObject
                        BasicDBObject reviewGroupBasicDBO = (BasicDBObject) reviewGroupObject;
                        //get the group's name
                        String revGroupName = reviewGroupBasicDBO.getString("name");
                        //set that BDBObject as what will replace the old one
                        BasicDBObject updateSetValue = new BasicDBObject("$set", reviewGroupBasicDBO);
                        //update the old review group by its name with the new one
                        UpdateResult updateResult =
                            reviewGroupCollection.updateMany(in("name", revGroupName), updateSetValue);
                        //This logs out an acknowledgment of changes. Not needed strictly. Without it, the IDE may
                        //make it look like updateResults is not used as it would be greyed out.
                        //However, even though updateResult is not used any bit later it still updates the groups.
                        System.out.println(updateResult);
                    } catch(NullPointerException e) {
                        return null;
                    }
                } else {
                    System.err.println("Expected BasicDBObject, received " + changedReviewGroups.getClass());
                    return null;
                }
            }
        } catch(RuntimeException ree) {
            ree.printStackTrace();
            return null;
        }
        return true;
    }
}
