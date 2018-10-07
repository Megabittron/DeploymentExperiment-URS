package server.database.abstracts;

import com.google.gson.Gson;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class AbstractController {
    private final Gson gson;
    private MongoDatabase database;

    // AbstractCollection is the collection that the collection data is in"

    private final MongoCollection<Document> abstractCollection;

    // Construct Abstract controller for the Abstracts:
    public AbstractController(MongoDatabase database) {
        gson = new Gson();
        this.database = database;
        abstractCollection = database.getCollection("submissions");
    }

    /**
     * Helper method that gets a single abstract specified by the `id` specific user ID
     * //     * parameter in the request.
     * //     *
     * //     * @param id the Mongo ID of the desired Abstract
     * //     * @return the desired Abstract as a JSON object if the user with that ID is found,
     * //     * and `null` if no user with that ID is found
     * //
     */

    public String getAbstract(String id) {
        FindIterable<Document> jsonUsers
            = abstractCollection
            .find(eq("_id", new ObjectId(id)));

        Iterator<Document> iterator = jsonUsers.iterator();
        if (iterator.hasNext()) {
            Document user = iterator.next();
            return user.toJson();
        } else {

            // Did not find a Abstract for that specific userID
            return null;
        }
    }

    /**
     * Helper method which iterates through the collection, receiving all
     * //     * documents if no query parameter is specified. If the userID query parameter
     * //     * is specified, then the collection is filtered so only documents of that
     * //     * specified userID are found.
     * //     *
     * //     * @param queryParams
     * //     * @return an array of Abstracts in a JSON formatted string
     * //
     */

    public String getAbstracts(Map<String, String[]> queryParams) {

        Document filterDoc = new Document();

        if (queryParams.containsKey("userID")) {
            String targetContent = (queryParams.get("userID")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("userID", contentRegQuery);
        } else {
            System.out.println("It had no userID");
            return JSON.serialize("[ ]");
        }

        if (queryParams.containsKey("title")) {
            String targetContent = (queryParams.get("title")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("title", contentRegQuery);
        }

        if (queryParams.containsKey("format")) {
            String targetContent = (queryParams.get("format")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("format", contentRegQuery);

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

        if (queryParams.containsKey("discipline")) {
            String targetContent = (queryParams.get("discipline")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("discipline", contentRegQuery);
        }


        if (queryParams.containsKey("featured")) {
            String targetContent = (queryParams.get("featured")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("featured", contentRegQuery);
        }

        if (queryParams.containsKey("mediaServicesEquipment")) {
            String targetContent = (queryParams.get("mediaServicesEquipment")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("mediaServicesEquipment", contentRegQuery);
        }
        if (queryParams.containsKey("specialRequirements")) {
            String targetContent = (queryParams.get("specialRequirements")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("specialRequirements", contentRegQuery);
        }
        if (queryParams.containsKey("otherInfo")) {
            String targetContent = (queryParams.get("otherInfo")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("otherInfo", contentRegQuery);
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

        //FindIterable comes from mongo, Document comes from Gson

        FindIterable<Document> matchingAbstracts = abstractCollection.find(filterDoc);

        return JSON.serialize(matchingAbstracts);

    }

    /**
     * // Function that adds new Abstract to the Abstract collection
     * // with specific userID and automated time stamp
     */
    public String addNewAbstract(String userID,
                                 String presentationTitle,
                                 String abstractContent,
                                 String submissionFormat,
                                 String presentationType,
                                 String changePresentationFormat,
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
                                 String featurePresenter,
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
                                 String additionalInfo,
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
        newAbstract.append("changePresentationFormat", changePresentationFormat);
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
        newAbstract.append("featurePresenter", featurePresenter);
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
        newAbstract.append("additionalInfo", additionalInfo);
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
                "presentationType=" + presentationType + ", changePresentationFormat=" + changePresentationFormat + ", firstPresenterFirstName=" + firstPresenterFirstName + ", firstPresenterLastName=" + firstPresenterLastName + ", " +
                "firstPresenterEmail=" + firstPresenterEmail + ", secondPresenterFirstName=" + secondPresenterFirstName + ", secondPresenterLastName=" + secondPresenterLastName + ", " +
                "secondPresenterEmail=" + secondPresenterEmail + ", thirdPresenterFirstName=" + thirdPresenterFirstName + ", thirdPresenterLastName=" + thirdPresenterEmail + ", academicDiscipline=" + academicDiscipline + ", featurePresenter="
                + featurePresenter + ", sponOrganization=" + sponOrganization + ", firstAdvisorFirstName=" + firstAdvisorFirstName + ", " +
                "firstAdvisorLastName=" + firstAdvisorLastName + ", firstAdvisorEmail=" + firstAdvisorEmail + ", secondAdvisorFirstName=" + secondAdvisorFirstName + ", secondAdvisorLastName="
                + secondAdvisorLastName + ", secondAdvisorEmail=" + secondAdvisorEmail + ", thirdAdvisorFirstName=" + thirdAdvisorFirstName + ", " +
                "thirdAdvisorLastName=" + thirdAdvisorLastName + ", thirdAdvisorEmail=" + thirdAdvisorEmail + ", additionalMediaEquipment="
                + additionalMediaEquipment + ", " + "additionalInfo=" + additionalInfo + ", other=" + other
                + ", approval=" + approval + ", " + "cc=" + cc + ", " + "rejection=" + rejection + ", group=" + group + ", " + "roomAssignment="
                + roomAssignment + ", totalRewriteVotes=" + totalRewriteVotes + ", majorRewriteVotes=" + majorRewriteVotes + ", " + "minorRewriteVotes="
                + minorRewriteVotes + ", acceptedVotes=" + acceptedVotes + ", comments=" + comments + ", isPrimarySubmission=" + isPrimarySubmission
                + ", resubmitFlag=" + resubmitFlag + ']');

            return JSON.serialize(id);

        } catch (MongoException me) {

            me.printStackTrace();

            return null;
        }
    }

    /**
     * // Function that edits existing Abstract to the Abstract collection
     * // with specific userID and automated time stamp. Only specific fields would be allowed
     * // to be edited with specific userIDs but for the time being this function allows
     * // the option to edit all the abstracts with the default userID
     */

    public String editAbstract(String id,
                               String title,
                               String format,
                               String abstracts,
                               String presentationType,
                               String formatChange,
                               String discipline,
                               String featured,
                               String mediaServicesEquipment,
                               String specialRequirements,
                               String otherInfo,
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
        newAbstract.append("format", format);
        newAbstract.append("abstracts", abstracts);
        newAbstract.append("presentationType", presentationType);
        newAbstract.append("formatChange", formatChange);
        newAbstract.append("discipline", discipline);
        newAbstract.append("featured", featured);
        newAbstract.append("mediaServicesEquipment", mediaServicesEquipment);
        newAbstract.append("specialRequirements", specialRequirements);
        newAbstract.append("otherInfo", otherInfo);
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
                "[_id=" + id1 + ", title=" + title + ", format=" + format + ", abstracts=" + abstracts + ", " +
                "presentationType=" + presentationType + ", formatChange=" + formatChange + ", discipline=" + discipline + ", featured=" + featured + ", " +
                "mediaServicesEquipment=" + mediaServicesEquipment + ", specialRequirements=" + specialRequirements + ", otherInfo=" + otherInfo + ", " +
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

            return JSON.serialize(id1);
        } catch(MongoException me) {
            me.printStackTrace();
            return null;
        }


    }

    /**
     * // Function that deletes existing Abstract from the Abstract collection
     * // with specific userID and automated time stamp. Only specific fields would be allowed
     * // to be deleted with specific userID but for the time being this function allows
     * // to delete with the option of all the available abstracts with the default userID
     */

    public void deleteAbstract(String id){
        Document searchQuery = new Document().append("_id", new ObjectId(id));
        System.out.println("Abstract id: " + id);
        try {
            abstractCollection.deleteOne(searchQuery);
            ObjectId theID = searchQuery.getObjectId("_id");
            System.out.println("Succesfully deleted abstract with ID: " + theID);

        } catch(MongoException me) {
            me.printStackTrace();
            System.out.println("error");
        }
    }

}
