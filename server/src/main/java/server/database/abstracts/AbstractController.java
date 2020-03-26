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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.mongodb.client.model.Accumulators.first;
import static com.mongodb.client.model.Accumulators.push;
import static com.mongodb.client.model.Sorts.*;

public class AbstractController {

    private final MongoCollection<Document> abstractCollection;
    private final MongoCollection<Document> topCommentCollection;
    private final MongoCollection<Document> disciplinesCollection;
    private final MongoCollection<Document> categoriesCollection;
    private final MongoCollection<Document> sponsoredOrganizationsCollection;

    public AbstractController(MongoDatabase database) {
        abstractCollection = database.getCollection("abstracts");
        topCommentCollection = database.getCollection("topComments");
        disciplinesCollection = database.getCollection("disciplines");
        categoriesCollection = database.getCollection("categories");
        sponsoredOrganizationsCollection = database.getCollection("sponsoredOrganizations");
    }

    /**
     * Helper method called by getAbstractJSON(..)
     *
     * @param presenterEmail Abstract.presenters.presenterEmail
     * @return Array of abstracts by presenters.presenterEmail as a JSON formatted string
     */

    String getAbstractsForStudent(String presenterEmail) {//https://github.com/Megabittron/DeploymentExperiment-URS/blob/master/server/src/main/java/server/database/users/UserController.java#L30
        Document filterDoc = new Document();
        filterDoc.append("presenters.presenterEmail", presenterEmail);

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
            Aggregates.lookup("topComments", "topComments", "_id", "topComments")
//            Aggregates.unwind("$topComments")
//            Aggregates.lookup("users","topComments.commenter","_id","topComments.commenter"),
//            Aggregates.unwind("$topComments.commenter"),
//            Aggregates.lookup("subComments", "topComments.subComments", "_id", "topComments.subComments"),
////            Aggregates.lookup("users", "topComments.subComments.commenter", "_id", "topComments.subComments.commenter"),
//            Aggregates.group("$_id", first("userID","$userID"),
//                first("presentationTitle","$presentationTitle"),
//                first("abstractContent", "$abstractContent"),
//                push("topComments", "$topComments"))
        ));

//        System.out.println("final result: \n" + toPrettyFormat(singleAbstract.first().toJson()));

        return singleAbstract.first().toJson();
    }

    public static String toPrettyFormat(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(json);
    }

    String getAbstractField(Map<String, String[]> queryParams, String fieldName) {

        Document filterDoc = new Document();

        if (queryParams.containsKey("key")) {
            String targetContent = (queryParams.get("key")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("key", contentRegQuery);
        }

        if (queryParams.containsKey("value")) {
            String targetContent = (queryParams.get("value")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("value", contentRegQuery);
        }

        FindIterable<Document> matchingField;

        switch (fieldName) {
            case "disciplines":
                matchingField = disciplinesCollection.find(filterDoc);
                break;
            case "categories":
                matchingField = categoriesCollection.find(filterDoc);
                break;
            case "sponsoredOrganizations":
                matchingField = sponsoredOrganizationsCollection.find(filterDoc);
                break;
            default:
                throw new IllegalArgumentException("Not a supported field of an abstract");
        }

        return serializeIterable(matchingField);
    }

    public static String serializeIterable(Iterable<Document> documents) {
        return StreamSupport.stream(documents.spliterator(), false)
            .map(Document::toJson)
            .collect(Collectors.joining(", ", "[", "]"));
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
                          Object presenters,
                          Object academicDiscipline,
                          String willingToBeFeaturePresenter,
                          Object sponOrganization,
                          Object category,
                          Object advisors,
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
        newAbstract.append("presenters", presenters);
        newAbstract.append("academicDiscipline", academicDiscipline);
        newAbstract.append("willingToBeFeaturePresenter", willingToBeFeaturePresenter);
        newAbstract.append("sponOrganization", sponOrganization);
        newAbstract.append("category", category);
        newAbstract.append("advisors", advisors);
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
                "[userID " + userID + ", title=" + presentationTitle + ", abstractContent=" + abstractContent +
                ", submissionFormat=" + submissionFormat + ", " + "presentationType=" + presentationType +
                ", willingToChangePresentationFormat=" + willingToChangePresentationFormat + ", presenters=" + presenters +
                ", academicDiscipline=" + academicDiscipline + ", willingToBeFeaturePresenter=" + willingToBeFeaturePresenter +
                ", additionalMediaEquipment=" + additionalMediaEquipment + ", additionalRequirements=" + additionalRequirements +
                ", other=" + other + ", approval=" + approval + ", " + "cc=" + cc + ", " + "rejection=" + rejection +
                ", group=" + group + ", " + "roomAssignment=" + roomAssignment + ", totalRewriteVotes=" + totalRewriteVotes +
                ", majorRewriteVotes=" + majorRewriteVotes + ", " + "minorRewriteVotes=" + minorRewriteVotes +
                ", acceptedVotes=" + acceptedVotes + ", comments=" + comments + ", isPrimarySubmission=" + isPrimarySubmission +
                ", resubmitFlag=" + resubmitFlag + ']');

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
                        String resubmitFlag) {

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

        Document setQuery = new Document();
        setQuery.append("$set", newAbstract);
        Document searchQuery = new Document().append("_id", new ObjectId(id));
//        System.out.println(searchQuery + " the search");

        try {
            abstractCollection.updateOne(searchQuery, setQuery);
//            System.out.println(abstractCollection.find());
            ObjectId id1 = searchQuery.getObjectId("_id");
            System.err.println("Successfully added new journal " +
                "[_id=" + id1 + ", title=" + title + ", submissionFormat=" + submissionFormat + ", abstracts=" + abstracts + ", " +
                "presentationType=" + presentationType + ", willingToChangePresentationFormat=" + willingToChangePresentationFormat + ", academicDiscipline=" + academicDiscipline + ", willingToBeFeaturePresenter=" + willingToBeFeaturePresenter + ", " +
                "additionalMediaEquipment=" + additionalMediaEquipment + ", specialRequirements=" + specialRequirements + ", additionalRequirements=" + additionalRequirements + ", " +
                "approval=" + approval + ", cc=" + cc + ", rejection=" + rejection + ", group=" + group + ", roomAssignment="
                + roomAssignment + ", totalRewriteVotes=" + totalRewriteVotes + ", majorRewriteVotes=" + majorRewriteVotes + ", " +
                "minorRewriteVotes=" + minorRewriteVotes + ", acceptedVotes=" + acceptedVotes + ", comments=" + comments + ", isPrimarySubmission="
                + isPrimarySubmission + ", resubmitFlag=" + resubmitFlag + ']');

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
//        System.out.println("Abstract id: " + id);
        try {
            abstractCollection.deleteOne(searchQuery);
            ObjectId theID = searchQuery.getObjectId("_id");
//            System.out.println("Succesfully deleted abstract with ID: " + theID);

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
