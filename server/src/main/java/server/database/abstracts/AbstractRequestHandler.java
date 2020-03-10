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
    public String getAbstractsJSON(Request req, Response res){
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
     * Method called by 'api/abstracts/:id' endpoint.
     *
     * @param req HTTP request
     * @param res HTTP response
     * @return Array of abstracts by userID as a JSON formatted string
     */
    public String getSingleAbstractJSON(Request req, Response res){
        res.type("application/json");
        String id = req.params("id");

        String abstracts;
        try {
            abstracts = abstractController.getSingleAbstract(id);
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

    public String getDisciplines(Request req, Response res) {
        res.type("application/json");
        return abstractController.getDisciplines(req.queryMap().toMap());
    }

    public String getCategories(Request req, Response res) {
        res.type("application/json");
        return abstractController.getCategories(req.queryMap().toMap());
    }

    public String getSponsoredOrganizations(Request req, Response res) {
        res.type("application/json");
        return abstractController.getSpongsoredOrganizations(req.queryMap().toMap());
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
            String willingToChangePresentationFormat = dbO.getString("willingToChangePresentationFormat");
            Object presenters = dbO.get("presenters");
            Object academicDiscipline = dbO.get("academicDiscipline");
            String willingToBeFeaturePresenter = dbO.getString("willingToBeFeaturePresenter");
            Object sponOrganization = dbO.get("sponOrganization");
            String miscSponOrganization = dbO.getString("miscSponOrganization");
            Object category = dbO.get("category");
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
            String additionalRequirements = dbO.getString("additionalRequirements");
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
                "presentationType=" + presentationType + ", willingToChangePresentationFormat=" + willingToChangePresentationFormat + ", presenters=" + presenters + ", academicDiscipline=" + academicDiscipline + ", willingToBeFeaturePresenter="
                + willingToBeFeaturePresenter + ", sponOrganization=" + sponOrganization + ", firstAdvisorFirstName=" + firstAdvisorFirstName + ", " +
                "firstAdvisorLastName=" + firstAdvisorLastName + ", firstAdvisorEmail=" + firstAdvisorEmail + ", secondAdvisorFirstName=" + secondAdvisorFirstName + ", secondAdvisorLastName="
                + secondAdvisorLastName + ", secondAdvisorEmail=" + secondAdvisorEmail + ", thirdAdvisorFirstName=" + thirdAdvisorFirstName + ", " +
                "thirdAdvisorLastName=" + thirdAdvisorLastName + ", thirdAdvisorEmail=" + thirdAdvisorEmail + ", additionalMediaEquipment="
                + additionalMediaEquipment + ", " + "additionalRequirements=" + additionalRequirements + ", other=" + other
                + ", approval=" + approval + ", " + "cc=" + cc + ", " +
                "rejection=" + rejection + ", group=" + group + ", " + "roomAssignment="
                + roomAssignment + ", totalRewriteVotes=" + totalRewriteVotes + ", majorRewriteVotes=" + majorRewriteVotes + ", " + "minorRewriteVotes="
                + minorRewriteVotes + ", acceptedVotes=" + acceptedVotes + ", comments=" + comments + ", isPrimarySubmission=" + isPrimarySubmission
                + ", resubmitFlag=" + resubmitFlag + ']');

            return abstractController.addNewAbstract(userID,                           presentationTitle,
                 abstractContent,
                 submissionFormat,
                 presentationType,
                 willingToChangePresentationFormat,
                 presenters,
                 academicDiscipline,
                 willingToBeFeaturePresenter,
                sponOrganization,
                miscSponOrganization,
                category,
                 firstAdvisorFirstName,
                 firstAdvisorLastName,
                 firstAdvisorEmail,
                 secondAdvisorFirstName,
                 secondAdvisorLastName,
                 secondAdvisorEmail,
                 thirdAdvisorFirstName,
                 thirdAdvisorLastName,
                 thirdAdvisorEmail,
                 additionalMediaEquipment,
                 additionalRequirements,
                 other,
                 approval,
                 cc,
                 rejection,
                 group,
                 roomAssignment,
                 totalRewriteVotes,
                 majorRewriteVotes,
                 minorRewriteVotes,
                 acceptedVotes,
                 comments,
                 isPrimarySubmission,
                 resubmitFlag);
        } catch (NullPointerException e) {
            System.err.println("A value was malformed or omitted, new abstract request failed.");
            return null;
        }
    }

    //TODO: Change types of objects, like fields that are now object arrays and not strings
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
            String submissionFormat = dbO.getString("submissionFormat");
            String abstracts = dbO.getString("abstracts");
            String presentationType = dbO.getString("presentationType");
            String formatChange = dbO.getString("formatChange");
            String academicDiscipline = dbO.getString("academicDiscipline");
            String willingToBeFeaturePresenter = dbO.getString("willingToBeFeaturePresenter");
            String additionalMediaEquipment = dbO.getString("additionalMediaEquipment");
            String specialRequirements = dbO.getString("specialRequirments");
            String additionalRequirements = dbO.getString("additionalRequirements");
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
            String category = dbO.getString("category");

            System.err.println("Editing abstract" +
                "[_id=" + id + ", title=" + title + ", submissionFormat=" + submissionFormat + ", abstracts=" + abstracts + ", " +
                "presentationType=" + presentationType + ", formatChange=" + formatChange + ", academicDiscipline=" + academicDiscipline + ", willingToBeFeaturePresenter=" + willingToBeFeaturePresenter + ", " +
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

            return abstractController.editAbstract(id, title, submissionFormat, abstracts, presentationType, formatChange
                , academicDiscipline, willingToBeFeaturePresenter, additionalMediaEquipment, specialRequirements, additionalRequirements, approval, cc, rejection,
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
