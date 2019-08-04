package server.database.abstracts;

import com.mongodb.BasicDBObject;
import spark.Request;
import spark.Response;

public class AbstractRequestHandler {

    private final AbstractController abstractController;

    public AbstractRequestHandler(AbstractController abstractController){
        this.abstractController = abstractController;
    }

    /**
     * Method called by 'api/abstracts/:id' endpoint.
     *
     * @param req HTTP request
     * @param res HTTP response
     * @return Array of abstracts by userID as a JSON formatted string
     */
    public String getAbstractJSON(Request req, Response res){
        res.type("application/json");
        String id = req.params("id");

        String abstracts;
        try {
            abstracts = abstractController.getAbstractsForUser(id);
        } catch (IllegalArgumentException e) {

            // This is thrown if the ID doesn't have the appropriate
            // form for a Mongo Object ID.
            // https://docs.mongodb.com/manual/reference/method/ObjectId/

            res.status(400);
            res.body("The requested abstract id " + id + " wasn't a legal Mongo Object ID.\n" +
                "See 'https://docs.mongodb.com/manual/reference/method/ObjectId/' for more info.");
            return "";
        }

        if (abstracts != null) {
            return abstracts;
        } else {
            res.status(404);
            res.body("The requested abstract with id " + id + " was not found");
            return "";
        }
    }

    /**
     * Method called by 'api/abstracts' endpoint.
     *
     * @param req HTTP request
     * @param res HTTP response
     * @return Array of abstracts based on query params as a JSON formatted string
     */
    public String getAbstracts(Request req, Response res) {
        res.type("application/json");
        return abstractController.getAbstracts(req.queryMap().toMap());
    }

    /**
     * Method called by 'api/abstracts/new' endpoint
     *
     * @param req HTTP request
     * @param res HTTP response
     * @return New abstract Id as a JSON formatted string
     */
    public String addNewAbstract(Request req, Response res) {
        res.type("application/json");
        BasicDBObject dbO = BasicDBObject.parse(req.body());

        try {
            String userID = dbO.getString("userID");
            String presentationTitle = dbO.getString("presentationTitle");
            String abstractContent = dbO.getString("abstractContent");
            String submissionFormat = dbO.getString("submissionFormat");
            String presentationType = dbO.getString("presentationType");
            String changePresentationFormat = dbO.getString("changePresentationForamt");
            String firstPresenterFirstName = dbO.getString("firstPresenterFirstName");
            String firstPresenterLastName = dbO.getString("firstPresenterLastName");
            String firstPresenterEmail = dbO.getString("firstPresenterEmail");
            String secondPresenterFirstName = dbO.getString("secondPresenterFirstName");
            String secondPresenterLastName = dbO.getString("secondPresenterLastName");
            String secondPresenterEmail = dbO.getString("secondPresenterEmail");
            String thirdPresenterFirstName = dbO.getString("thirdPresenterFirstName");
            String thirdPresenterLastName = dbO.getString("thirdPresenterLastName");
            String thirdPresenterEmail = dbO.getString("thirdPresenterEmail");
            String academicDiscipline = dbO.getString("academicDiscipline");
            String featurePresenter = dbO.getString("featurePresenter");
            String sponOrganization = dbO.getString("sponOrganization");
            String firstAdvisorFirstName = dbO.getString("firstAdvisorFirstName");
            String firstAdvisorLastName = dbO.getString("firstAdvisorLastName");
            String firstAdvisorEmail = dbO.getString("firstAdvisorEmail");
            String secondAdvisorFirstName = dbO.getString("secondAdvisorFirstName");
            String secondAdvisorLastName = dbO.getString("secondAdvisorLastName");
            String secondAdvisorEmail = dbO.getString("secondAdvisorEmail");
            String thirdAdvisorFirstName = dbO.getString("thirdAdvisorFirstName");
            String thirdAdvisorLastName = dbO.getString("thirdAdvisorLastName");
            String thirdAdvisorEmail = dbO.getString("thirdAdvisorEmail");
            String additionalMediaEquipment = dbO.getString("additionalMediaEquipment");
            String additionalInfo = dbO.getString("additionalInfo");
            String other = dbO.getString("other");
            String approval = dbO.getString("approval");
            String cc = dbO.getString("cc");
            String rejection = dbO.getString("rejection");
            String group = dbO.getString("group");
            String roomAssignment = dbO.getString("roomAssignment");
            String totalRewriteVotes = dbO.getString("totalRewriteVotes");
            String majorRewriteVotes = dbO.getString("majorRewriteVotes");
            String minorRewriteVotes = dbO.getString("minorRewriteVotes");
            String acceptedVotes = dbO.getString("acceptedVotes");
            String comments = dbO.getString("comments");
            String isPrimarySubmission = dbO.getString("isPrimarySubmission");
            String resubmitFlag = dbO.getString("resubmitFlag");

            System.err.println("Adding new Abstract for a specific userID " + userID +
                ", title=" + presentationTitle + ", abstractContent=" + abstractContent + ", submissionFormat=" + submissionFormat + ", " +
                "presentationType=" + presentationType + ", changePresentationFormat=" + changePresentationFormat + ", firstPresenterFirstName=" + firstPresenterFirstName + ", firstPresenterLastName=" + firstPresenterLastName + ", " +
                "firstPresenterEmail=" + firstPresenterEmail + ", secondPresenterFirstName=" + secondPresenterFirstName + ", secondPresenterLastName=" + secondPresenterLastName + ", " +
                "secondPresenterEmail=" + secondPresenterEmail + ", thirdPresenterFirstName=" + thirdPresenterFirstName + ", thirdPresenterLastName=" + thirdPresenterEmail + ", academicDiscipline=" + academicDiscipline + ", featurePresenter="
                + featurePresenter + ", sponOrganization=" + sponOrganization + ", firstAdvisorFirstName=" + firstAdvisorFirstName + ", " +
                "firstAdvisorLastName=" + firstAdvisorLastName + ", firstAdvisorEmail=" + firstAdvisorEmail + ", secondAdvisorFirstName=" + secondAdvisorFirstName + ", secondAdvisorLastName="
                + secondAdvisorLastName + ", secondAdvisorEmail=" + secondAdvisorEmail + ", thirdAdvisorFirstName=" + thirdAdvisorFirstName + ", " +
                "thirdAdvisorLastName=" + thirdAdvisorLastName + ", thirdAdvisorEmail=" + thirdAdvisorEmail + ", additionalMediaEquipment="
                + additionalMediaEquipment + ", " + "additionalInfo=" + additionalInfo + ", other=" + other
                + ", approval=" + approval + ", " + "cc=" + cc + ", " +
                "rejection=" + rejection + ", group=" + group + ", " + "roomAssignment="
                + roomAssignment + ", totalRewriteVotes=" + totalRewriteVotes + ", majorRewriteVotes=" + majorRewriteVotes + ", " + "minorRewriteVotes="
                + minorRewriteVotes + ", acceptedVotes=" + acceptedVotes + ", comments=" + comments + ", isPrimarySubmission=" + isPrimarySubmission
                + ", resubmitFlag=" + resubmitFlag + ']');

            return abstractController.addNewAbstract(userID, presentationTitle, abstractContent, submissionFormat, presentationType,
                changePresentationFormat, firstPresenterFirstName, firstPresenterLastName, firstPresenterEmail, secondPresenterFirstName,
                secondPresenterLastName, secondPresenterEmail, thirdPresenterFirstName, thirdPresenterLastName, thirdPresenterEmail, academicDiscipline,
                featurePresenter, sponOrganization, firstAdvisorFirstName, firstAdvisorLastName, firstAdvisorEmail, secondAdvisorFirstName,
                secondAdvisorLastName, secondAdvisorEmail, thirdAdvisorFirstName, thirdAdvisorLastName, thirdAdvisorEmail, additionalMediaEquipment,
                additionalInfo, other, approval, cc, rejection, group, roomAssignment, totalRewriteVotes, majorRewriteVotes, minorRewriteVotes, acceptedVotes,
                comments, isPrimarySubmission, resubmitFlag);
        } catch (NullPointerException e) {
            System.err.println("A value was malformed or omitted, new abstract request failed.");
            return null;
        }
    }

    /**
     * @param req HTTP request
     * @param res HTTP response
     * @return Edited abstract Id as a JSON formatted string
     */
    public String editAbstract(Request req, Response res) {
        res.type("application/json");
        BasicDBObject dbO = BasicDBObject.parse(req.body());

        try {
            String id = dbO.getString("id");
            String title = dbO.getString("title");
            String format = dbO.getString("format");
            String abstracts = dbO.getString("abstracts");
            String presentationType = dbO.getString("presentationType");
            String formatChange = dbO.getString("formatChange");
            String discipline = dbO.getString("discipline");
            String featured = dbO.getString("featured");
            String mediaServicesEquipment = dbO.getString("mediaServicesEquipment");
            String specialRequirements = dbO.getString("specialRequirments");
            String otherInfo = dbO.getString("otherInfo");
            String approval = dbO.getString("approval");
            String cc = dbO.getString("cc");
            String rejection = dbO.getString("rejection");
            String group = dbO.getString("group");
            String roomAssignment = dbO.getString("roomAssignment");
            String totalRewriteVotes = dbO.getString("totalRewriteVotes");
            String majorRewriteVotes = dbO.getString("majorRewriteVotes");
            String minorRewriteVotes = dbO.getString("minorRewriteVotes");
            String acceptedVotes = dbO.getString("acceptedVotes");
            String comments = dbO.getString("comments");
            String isPrimarySubmission = dbO.getString("isPrimarySubmission");
            String resubmitFlag = dbO.getString("resubmitFlag");
            String firstPresenterFirstName = dbO.getString("firstPresenterFirstName");
            String firstPresenterLastName = dbO.getString("firstPresenterLastName");
            String firstPresenterEmail = dbO.getString("firstPresenterEmail");
            String secondPresenterFirstName = dbO.getString("secondPresenterFirstName");
            String secondPresenterLastName = dbO.getString("secondPresenterLastName");
            String secondPresenterEmail = dbO.getString("secondPresenterEmail");
            String thirdPresenterFirstName = dbO.getString("thirdPresenterFirstName");
            String thirdPresenterLastName = dbO.getString("thirdPresenterLastName");
            String thirdPresenterEmail = dbO.getString("thirdPresenterEmail");
            String firstAdviserFirstName = dbO.getString("firstAdviserFirstName");
            String firstAdviserLastName = dbO.getString("firstAdviserLastName");
            String firstAdviserEmail = dbO.getString("firstAdviserEmail");
            String secondAdviserFirstName = dbO.getString("secondAdviserFirstName");
            String secondAdviserLastName = dbO.getString("secondAdviserLastName");
            String secondAdviserEmail = dbO.getString("secondAdviserEmail");

            System.err.println("Editing abstract" +
                "[_id=" + id + ", title=" + title + ", format=" + format + ", abstracts=" + abstracts + ", " +
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

            return abstractController.editAbstract(id, title, format, abstracts, presentationType, formatChange
                , discipline, featured, mediaServicesEquipment, specialRequirements, otherInfo, approval, cc, rejection,
                group, roomAssignment, totalRewriteVotes, majorRewriteVotes, minorRewriteVotes, acceptedVotes, comments,
                isPrimarySubmission, resubmitFlag, firstPresenterFirstName, firstPresenterLastName,
                firstPresenterEmail, secondPresenterFirstName, secondPresenterLastName, secondPresenterEmail
                , thirdPresenterFirstName, thirdPresenterLastName, thirdPresenterEmail, firstAdviserFirstName,
                firstAdviserLastName, firstAdviserEmail, secondAdviserFirstName, secondAdviserLastName, secondAdviserEmail
            );
        } catch (NullPointerException e) {
            System.err.println("A value was malformed or omitted, new abstract request failed.");
            return null;
        }

    }

    /**
     * @param req HTTP request
     * @param res HTTP response
     * @return Deleted abstract Id
     */
    public String deleteAbstract(Request req, Response res) {
        res.type("application/json");

        try {
            String id = req.params(":id");

            System.out.println("Deleting abstract with ID: " + id);

            abstractController.deleteAbstract(id);
            return req.params(":id");
        } catch (RuntimeException ree) {
            ree.printStackTrace();
            return null;
        }
    }
}
