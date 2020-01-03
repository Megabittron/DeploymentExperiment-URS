package server.database.abstracts;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import static com.mongodb.client.model.Accumulators.first;
import static com.mongodb.client.model.Accumulators.push;
import static com.mongodb.client.model.Sorts.*;

public class AbstractController {

    private final MongoCollection<Document> abstractCollection;
    private final MongoCollection<Document> topCommentCollection;

    public AbstractController(MongoDatabase database) {
        abstractCollection = database.getCollection("abstracts");
        topCommentCollection = database.getCollection("topComments");
    }

    /**
     * Helper method called by getAbstractJSON(..)
     *
     * @param id Users SubjectID
     * @return Array of abstracts by userID as a JSON formatted string
     */
    String getAbstractsForUser(String id) {//https://github.com/Megabittron/DeploymentExperiment-URS/blob/master/server/src/main/java/server/database/users/UserController.java#L30
        Document filterDoc = new Document();
        filterDoc.append("userID", id);

        FindIterable<Document> abstracts = abstractCollection.find(filterDoc);

        return newAbstractArray(abstracts);
    }

    /**
     * Helper method called by getAbstractJSON(..)
     *
     * @param id Users SubjectID
     * @return Array of abstracts by userID as a JSON formatted string
     */
    public String getSingleAbstract(String id){
        AggregateIterable<Document> singleAbstract = abstractCollection.aggregate(Arrays.asList(
            Aggregates.match(new Document("_id", new ObjectId(id))),
            Aggregates.lookup("topComments", "topComments", "_id", "topComments"),
            Aggregates.unwind("$topComments"),
            Aggregates.lookup("users","topComments.commenter","_id","topComments.commenter"),
            Aggregates.unwind("$topComments.commenter"),
            Aggregates.lookup("subComments", "topComments.subComments", "_id", "topComments.subComments"),
//            Aggregates.lookup("users", "topComments.subComments.commenter", "_id", "topComments.subComments.commenter"),
            Aggregates.group("$_id", first("userID","$userID"),
                first("presentationTitle","$presentationTitle"),
                first("abstractContent", "$abstractContent"),
                push("topComments", "$topComments"))
        ));

        System.out.println("final result: \n" + toPrettyFormat(singleAbstract.first().toJson()));

        return singleAbstract.first().toJson();
    }

    public static String toPrettyFormat(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }

    /**
     * Helper method called by getAbstracts(..)
     *
     * @param queryParams Map of request params
     * @return Array of abstracts based on on query params as a JSON formatted string
     */
    String getAbstracts(Map<String, String[]> queryParams) {

        Document filterDoc = new Document();

        if (queryParams.containsKey("userID")) {
            String targetContent = (queryParams.get("userID")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("userID", contentRegQuery);
        }

        if (queryParams.containsKey("title")) {
            String targetContent = (queryParams.get("title")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("title", contentRegQuery);
        }

        if (queryParams.containsKey("submissionFormat")) {
            String targetContent = (queryParams.get("submissionFormat")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("submissionFormat", contentRegQuery);

        }

        if (queryParams.containsKey("submissions")) {
            String targetContent = (queryParams.get("submissions")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("submissions", contentRegQuery);
        }

        if (queryParams.containsKey("presentationType")) {
            String targetContent = (queryParams.get("presentationType")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("presentationType", contentRegQuery);
        }

        if (queryParams.containsKey("formatChange")) {
            String targetContent = (queryParams.get("formatChange")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("formatChange", contentRegQuery);
        }

        if (queryParams.containsKey("academicDiscipline")) {
            String targetContent = (queryParams.get("academicDiscipline")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("academicDiscipline", contentRegQuery);
        }


        if (queryParams.containsKey("willingToBeFeaturePresenter")) {
            String targetContent = (queryParams.get("willingToBeFeaturePresenter")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("willingToBeFeaturePresenter", contentRegQuery);
        }

        if (queryParams.containsKey("additionalMediaEquipment")) {
            String targetContent = (queryParams.get("additionalMediaEquipment")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("additionalMediaEquipment", contentRegQuery);
        }
        if (queryParams.containsKey("specialRequirements")) {
            String targetContent = (queryParams.get("specialRequirements")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("specialRequirements", contentRegQuery);
        }
        if (queryParams.containsKey("additionalRequirements")) {
            String targetContent = (queryParams.get("additionalRequirements")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("additionalRequirements", contentRegQuery);
        }
        if (queryParams.containsKey("approval")) {
            String targetContent = (queryParams.get("approval")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("approval", contentRegQuery);
        }
        if (queryParams.containsKey("cc")) {
            String targetContent = (queryParams.get("cc")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("cc", contentRegQuery);
        }

        if (queryParams.containsKey("rejection")) {
            String targetContent = (queryParams.get("rejection")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("rejection", contentRegQuery);
        }
        if (queryParams.containsKey("group")) {
            String targetContent = (queryParams.get("group")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("group", contentRegQuery);
        }
        if (queryParams.containsKey("roomAssignment")) {
            String targetContent = (queryParams.get("roomAssignment")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("roomAssignment", contentRegQuery);
        }

        if (queryParams.containsKey("totalRewriteVotes")) {
            String targetContent = (queryParams.get("totalRewriteVotes")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("totalRewriteVotes", contentRegQuery);
        }

        if (queryParams.containsKey("majorRewriteVotes")) {
            String targetContent = (queryParams.get("majorRewriteVotes")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("majorRewriteVotes", contentRegQuery);
        }

        if (queryParams.containsKey("minorRewriteVotes")) {
            String targetContent = (queryParams.get("minorRewriteVotes")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("minorRewriteVotes", contentRegQuery);
        }


        if (queryParams.containsKey("acceptedVotes")) {
            String targetContent = (queryParams.get("acceptedVotes")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("acceptedVotes", contentRegQuery);
        }

        if (queryParams.containsKey("comments")) {
            String targetContent = (queryParams.get("comments")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("comments", contentRegQuery);
        }

        if (queryParams.containsKey("isPrimarySubmission")) {
            String targetContent = (queryParams.get("isPrimarySubmission")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("isPrimarySubmission", contentRegQuery);
        }

        if (queryParams.containsKey("resubmitFlag")) {
            String targetContent = (queryParams.get("resubmitFlag")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("resubmitFlag", contentRegQuery);
        }

        if (queryParams.containsKey("firstPresenterFirstName")) {
            String targetContent = (queryParams.get("firstPresenterFirstName")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("firstPresenterFirstName", contentRegQuery);
        }

        if (queryParams.containsKey("firstPresenterLastName")) {
            String targetContent = (queryParams.get("firstPresenterLastName")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("firstPresenterLastName", contentRegQuery);
        }

        if (queryParams.containsKey("firstPresenterEmail")) {
            String targetContent = (queryParams.get("firstPresenterEmail")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("firstPresenterEmail", contentRegQuery);
        }

        if (queryParams.containsKey("secondPresenterFirstName")) {
            String targetContent = (queryParams.get("secondPresenterFirstName")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("secondPresenterFirstName", contentRegQuery);
        }
        if (queryParams.containsKey("secondPresenterLastName")) {
            String targetContent = (queryParams.get("secondPresenterLastName")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("secondPresenterLastName", contentRegQuery);
        }

        if (queryParams.containsKey("secondPresenterEmail")) {
            String targetContent = (queryParams.get("secondPresenterEmail")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("secondPresenterEmail", contentRegQuery);
        }

        if (queryParams.containsKey("thirdPresenterFirstName")) {
            String targetContent = (queryParams.get("thirdPresenterFirstName")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("thirdPresenterFirstName", contentRegQuery);
        }
        if (queryParams.containsKey("thirdPresenterLastName")) {
            String targetContent = (queryParams.get("thirdPresenterLastName")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("thirdPresenterLastName", contentRegQuery);
        }
        if (queryParams.containsKey("thirdPresenterEmail")) {
            String targetContent = (queryParams.get("thirdPresenterEmail")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("thirdPresenterEmail", contentRegQuery);
        }

        if (queryParams.containsKey("firstAdviserFirstName")) {
            String targetContent = (queryParams.get("firstAdviserFirstName")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("firstAdviserFirstName", contentRegQuery);
        }

        if (queryParams.containsKey("firstAdviserLastName")) {
            String targetContent = (queryParams.get("firstAdviserLastName")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("firstAdviserLastName", contentRegQuery);
        }

        if (queryParams.containsKey("firstAdviserEmail")) {
            String targetContent = (queryParams.get("firstAdviserEmail")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("firstAdviserEmail", contentRegQuery);
        }

        if (queryParams.containsKey("secondAdviserFirstName")) {
            String targetContent = (queryParams.get("secondAdviserFirstName")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("secondAdviserFirstName", contentRegQuery);
        }

        if (queryParams.containsKey("secondAdviserLastName")) {
            String targetContent = (queryParams.get("secondAdviserLastName")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("secondAdviserLastName", contentRegQuery);
        }

        if (queryParams.containsKey("secondAdviserEmail")) {
            String targetContent = (queryParams.get("secondAdviserEmail")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("secondAdviserEmail", contentRegQuery);
        }

        FindIterable<Document> matchingAbstracts = abstractCollection.find(filterDoc);

        return newAbstractArray(matchingAbstracts);

    }

    /**
     * Helper method called by addNewAbstract(..)
     */
    String addNewAbstract(String userID,
                          String presentationTitle,
                          String abstractContent,
                          String submissionFormat,
                          String presentationType,
                          String willingToChangePresentationFormat,
                          String firstPresenterFirstName,
                          String firstPresenterLastName,
                          String firstPresenterEmail,
                          String secondPresenterFirstName,
                          String secondPresenterLastName,
                          String secondPresenterEmail,
                          String thirdPresenterFirstName,
                          String thirdPresenterLastName,
                          String thirdPresenterEmail,
                          String academicDiscipline,
                          String willingToBeFeaturePresenter,
                          String sponOrganization,
                          String firstAdvisorFirstName,
                          String firstAdvisorLastName,
                          String firstAdvisorEmail,
                          String secondAdvisorFirstName,
                          String secondAdvisorLastName,
                          String secondAdvisorEmail,
                          String thirdAdvisorFirstName,
                          String thirdAdvisorLastName,
                          String thirdAdvisorEmail,
                          String additionalMediaEquipment,
                          String additionalRequirements,
                          String other,
                          String approval,
                          String cc,
                          String rejection,
                          String group,
                          String roomAssignment,
                          String totalRewriteVotes,
                          String majorRewriteVotes,
                          String minorRewriteVotes,
                          String acceptedVotes,
                          String comments,
                          String isPrimarySubmission,
                          String resubmitFlag) {

        Document newAbstract = new Document();

        newAbstract.append("userID", userID);

        newAbstract.append("presentationTitle", presentationTitle);
        newAbstract.append("abstractContent", abstractContent);
        newAbstract.append("submissionFormat", submissionFormat);
        newAbstract.append("presentationType", presentationType);
        newAbstract.append("willingToChangePresentationFormat", willingToChangePresentationFormat);
        newAbstract.append("firstPresenterFirstName", firstPresenterFirstName);
        newAbstract.append("firstPresenterLastName", firstPresenterLastName);
        newAbstract.append("firstPresenterEmail", firstPresenterEmail);
        newAbstract.append("secondPresenterFirstName", secondPresenterFirstName);
        newAbstract.append("secondPresenterLastName", secondPresenterLastName);
        newAbstract.append("secondPresenterEmail", secondPresenterEmail);
        newAbstract.append("thirdPresenterFirstName", thirdPresenterFirstName);
        newAbstract.append("thirdPresenterLastName", thirdPresenterLastName);
        newAbstract.append("thirdPresenterEmail", thirdPresenterEmail);
        newAbstract.append("academicDiscipline", academicDiscipline);
        newAbstract.append("willingToBeFeaturePresenter", willingToBeFeaturePresenter);
        newAbstract.append("sponOrganization", sponOrganization);
        newAbstract.append("firstAdvisorFirstName", firstAdvisorFirstName);
        newAbstract.append("firstAdvisorLastName", firstAdvisorLastName);
        newAbstract.append("firstAdvisorEmail", firstAdvisorEmail);
        newAbstract.append("secondAdvisorFirstName", secondAdvisorFirstName);
        newAbstract.append("secondAdvisorLastName", secondAdvisorLastName);
        newAbstract.append("secondAdvisorEmail", secondAdvisorEmail);
        newAbstract.append("thirdAdvisorFirstName", thirdAdvisorFirstName);
        newAbstract.append("thirdAdvisorLastName", thirdAdvisorLastName);
        newAbstract.append("thirdAdvisorEmail", thirdAdvisorEmail);
        newAbstract.append("additionalMediaEquipment", additionalMediaEquipment);
        newAbstract.append("additionalRequirements", additionalRequirements);
        newAbstract.append("other", other);
        newAbstract.append("approval", approval);
        newAbstract.append("cc", cc);
        newAbstract.append("rejection", rejection);
        newAbstract.append("group", group);
        newAbstract.append("roomAssignment", roomAssignment);
        newAbstract.append("totalRewriteVotes", totalRewriteVotes);
        newAbstract.append("majorRewriteVotes", majorRewriteVotes);
        newAbstract.append("minorRewriteVotes", minorRewriteVotes);
        newAbstract.append("acceptedVotes", acceptedVotes);
        newAbstract.append("comments", comments);
        newAbstract.append("isPrimarySubmission", isPrimarySubmission);
        newAbstract.append("resubmitFlag", resubmitFlag);

        Date timestamp = new Date();
        newAbstract.append("timestamp", timestamp.toString());

        try {
            abstractCollection.insertOne(newAbstract);
            ObjectId id = newAbstract.getObjectId("_id");
            System.err.println("Successfully added new abstract " +
                "[userID " + userID +
                ", title=" + presentationTitle + ", abstractContent=" + abstractContent + ", submissionFormat=" + submissionFormat + ", " +
                "presentationType=" + presentationType + ", willingToChangePresentationFormat=" + willingToChangePresentationFormat + ", firstPresenterFirstName=" + firstPresenterFirstName + ", firstPresenterLastName=" + firstPresenterLastName + ", " +
                "firstPresenterEmail=" + firstPresenterEmail + ", secondPresenterFirstName=" + secondPresenterFirstName + ", secondPresenterLastName=" + secondPresenterLastName + ", " +
                "secondPresenterEmail=" + secondPresenterEmail + ", thirdPresenterFirstName=" + thirdPresenterFirstName + ", thirdPresenterLastName=" + thirdPresenterEmail + ", academicDiscipline=" + academicDiscipline + ", willingToBeFeaturePresenter="
                + willingToBeFeaturePresenter + ", sponOrganization=" + sponOrganization + ", firstAdvisorFirstName=" + firstAdvisorFirstName + ", " +
                "firstAdvisorLastName=" + firstAdvisorLastName + ", firstAdvisorEmail=" + firstAdvisorEmail + ", secondAdvisorFirstName=" + secondAdvisorFirstName + ", secondAdvisorLastName="
                + secondAdvisorLastName + ", secondAdvisorEmail=" + secondAdvisorEmail + ", thirdAdvisorFirstName=" + thirdAdvisorFirstName + ", " +
                "thirdAdvisorLastName=" + thirdAdvisorLastName + ", thirdAdvisorEmail=" + thirdAdvisorEmail + ", additionalMediaEquipment="
                + additionalMediaEquipment + ", " + "additionalRequirements=" + additionalRequirements + ", other=" + other
                + ", approval=" + approval + ", " + "cc=" + cc + ", " + "rejection=" + rejection + ", group=" + group + ", " + "roomAssignment="
                + roomAssignment + ", totalRewriteVotes=" + totalRewriteVotes + ", majorRewriteVotes=" + majorRewriteVotes + ", " + "minorRewriteVotes="
                + minorRewriteVotes + ", acceptedVotes=" + acceptedVotes + ", comments=" + comments + ", isPrimarySubmission=" + isPrimarySubmission
                + ", resubmitFlag=" + resubmitFlag + ']');

            return new BasicDBObject("_id", id).toJson();

        } catch (MongoException me) {

            me.printStackTrace();

            return null;
        }
    }

    /**
     * Helper method called by editAbstract(..)
     *
     * // Function that edits existing Abstract to the Abstract collection
     * // with specific userID and automated time stamp. Only specific fields would be allowed
     * // to be edited with specific userIDs but for the time being this function allows
     * // the option to edit all the abstracts with the default userID
     */

    String editAbstract(String id,
                        String title,
                        String submissionFormat,
                        String abstracts,
                        String presentationType,
                        String willingToChangePresentationFormat,
                        String academicDiscipline,
                        String willingToBeFeaturePresenter,
                        String additionalMediaEquipment,
                        String specialRequirements,
                        String additionalRequirements,
                        String approval,
                        String cc,
                        String rejection,
                        String group,
                        String roomAssignment,
                        String totalRewriteVotes,
                        String majorRewriteVotes,
                        String minorRewriteVotes,
                        String acceptedVotes,
                        String comments,
                        String isPrimarySubmission,
                        String resubmitFlag,
                        String firstPresenterFirstName,
                        String firstPresenterLastName,
                        String firstPresenterEmail,
                        String secondPresenterFirstName,
                        String secondPresenterLastName,
                        String secondPresenterEmail,
                        String thirdPresenterFirstName,
                        String thirdPresenterLastName,
                        String thirdPresenterEmail,
                        String firstAdviserFirstName,
                        String firstAdviserLastName,
                        String firstAdviserEmail,
                        String secondAdviserFirstName,
                        String secondAdviserLastName,
                        String secondAdviserEmail) {

        Document newAbstract = new Document();

        newAbstract.append("title", title);
        newAbstract.append("submissionFormat", submissionFormat);
        newAbstract.append("abstracts", abstracts);
        newAbstract.append("presentationType", presentationType);
        newAbstract.append("willingToChangePresentationFormat", willingToChangePresentationFormat);
        newAbstract.append("academicDiscipline", academicDiscipline);
        newAbstract.append("willingToBeFeaturePresenter", willingToBeFeaturePresenter);
        newAbstract.append("additionalMediaEquipment", additionalMediaEquipment);
        newAbstract.append("specialRequirements", specialRequirements);
        newAbstract.append("additionalRequirements", additionalRequirements);
        newAbstract.append("approval", approval);
        newAbstract.append("cc", cc);
        newAbstract.append("rejection", rejection);
        newAbstract.append("group", group);
        newAbstract.append("roomAssignment", roomAssignment);
        newAbstract.append("totalRewriteVotes", totalRewriteVotes);
        newAbstract.append("majorRewriteVotes", majorRewriteVotes);
        newAbstract.append("minorRewriteVotes", minorRewriteVotes);
        newAbstract.append("acceptedVotes", acceptedVotes);
        newAbstract.append("comments", comments);
        newAbstract.append("isPrimarySubmission", isPrimarySubmission);
        newAbstract.append("resubmitFlag", resubmitFlag);
        newAbstract.append("firstPresenterFirstName", firstPresenterFirstName);
        newAbstract.append("firstPresenterLastName", firstPresenterLastName);
        newAbstract.append("firstPresenterEmail", firstPresenterEmail);
        newAbstract.append("secondPresenterFirstName", secondPresenterFirstName);
        newAbstract.append("secondPresenterLastName", secondPresenterLastName);
        newAbstract.append("secondPresenterEmail", secondPresenterEmail);
        newAbstract.append("thirdPresenterFirstName", thirdPresenterFirstName);
        newAbstract.append("thirdPresenterLastName", thirdPresenterLastName);
        newAbstract.append("thirdPresenterEmail", thirdPresenterEmail);
        newAbstract.append("firstAdviserFirstName", firstAdviserFirstName);
        newAbstract.append("firstAdviserLastName", firstAdviserLastName);
        newAbstract.append("firstAdviserEmail", firstAdviserEmail);
        newAbstract.append("secondAdviserFirstName", secondAdviserFirstName);
        newAbstract.append("secondAdviserLastName", secondAdviserLastName);
        newAbstract.append("secondAdviserEmail", secondAdviserEmail);

        Document setQuery = new Document();
        setQuery.append("$set", newAbstract);
        Document searchQuery = new Document().append("_id", new ObjectId(id));
        System.out.println(searchQuery + " the search");

        try {
            abstractCollection.updateOne(searchQuery, setQuery);
            System.out.println(abstractCollection.find());
            ObjectId id1 = searchQuery.getObjectId("_id");
            System.err.println("Successfully added new journal " +
                "[_id=" + id1 + ", title=" + title + ", submissionFormat=" + submissionFormat + ", abstracts=" + abstracts + ", " +
                "presentationType=" + presentationType + ", willingToChangePresentationFormat=" + willingToChangePresentationFormat + ", academicDiscipline=" + academicDiscipline + ", willingToBeFeaturePresenter=" + willingToBeFeaturePresenter + ", " +
                "additionalMediaEquipment=" + additionalMediaEquipment + ", specialRequirements=" + specialRequirements + ", additionalRequirements=" + additionalRequirements + ", " +
                "approval=" + approval + ", cc=" + cc + ", rejection=" + rejection + ", group=" + group + ", roomAssignment="
                + roomAssignment + ", totalRewriteVotes=" + totalRewriteVotes + ", majorRewriteVotes=" + majorRewriteVotes + ", " +
                "minorRewriteVotes=" + minorRewriteVotes + ", acceptedVotes=" + acceptedVotes + ", comments=" + comments + ", isPrimarySubmission="
                + isPrimarySubmission + ", resubmitFlag=" + resubmitFlag + ", firstPresenterFirstName=" + firstPresenterFirstName + ", " +
                "firstPresenterLastName=" + firstPresenterLastName + ", firstPresenterEmail=" + firstPresenterEmail + ", secondPresenterFirstName="
                + secondPresenterFirstName + ", " + "secondPresenterLastName=" + secondPresenterLastName + ", secondPresenterEmail=" + secondPresenterEmail
                + ", thirdPresenterFirstName=" + thirdPresenterFirstName + ", " + "thirdPresenterLastName=" + thirdPresenterLastName + ", " +
                "thirdPresenterEmail=" + thirdPresenterEmail + ", firstAdviserFirstName=" + firstAdviserFirstName + ", " + "firstAdviserLastName="
                + firstAdviserLastName + ", firstAdviserEmail=" + firstAdviserEmail + ", secondAdviserFirstName=" + secondAdviserFirstName + ", " + "secondAdviserLastName="
                + secondAdviserLastName + ", secondAdviserEmail=" + secondAdviserEmail + ']');

            return new BasicDBObject("_id", id1).toJson();
        } catch (MongoException me) {
            me.printStackTrace();
            return null;
        }
    }

    /**
     * Helper method called by deleteAbstract(..)
     *
     * @param id MongoDB ObjectId for abstract to be deleted
     */
    void deleteAbstract(String id) {
        Document searchQuery = new Document().append("_id", new ObjectId(id));
        System.out.println("Abstract id: " + id);
        try {
            abstractCollection.deleteOne(searchQuery);
            ObjectId theID = searchQuery.getObjectId("_id");
            System.out.println("Succesfully deleted abstract with ID: " + theID);

        } catch (MongoException me) {
            me.printStackTrace();
            System.out.println("error");
        }
    }

    /**
     * Helper method to create array of abstracts
     *
     * @param abstracts FindIterable of MongoDB Documents
     * @return Array of abstracts as a JSON formatted string
     */
    private String newAbstractArray(FindIterable<Document> abstracts) {
        //noinspection NullableProblems - This comment disables inspection for Document::toJson
        return abstracts.map(Document::toJson).into(new ArrayList<>()).toString();
    }

}
