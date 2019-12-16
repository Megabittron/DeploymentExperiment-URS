import {TopComment} from "../submissionView/comment";

export interface Submission {
    _id?: {
        $oid: string
    };
    userID: string;
    presentationTitle: string;
    abstractContent: string;
    submissionFormat: string;
    presentationType: string;
    willingToChangePresentationFormat: string;
    firstPresenterFirstName: string;
    firstPresenterLastName: string;
    firstPresenterEmail: string;
    secondPresenterFirstName: string;
    secondPresenterLastName: string;
    secondPresenterEmail: string;
    thirdPresenterFirstName: string;
    thirdPresenterLastName: string;
    thirdPresenterEmail: string;
    academicDiscipline: string;
    willingToBeFeaturePresenter: string;
    sponOrganization: string;
    firstAdvisorFirstName: string;
    firstAdvisorLastName: string;
    firstAdvisorEmail: string;
    secondAdvisorFirstName: string;
    secondAdvisorLastName: string;
    secondAdvisorEmail: string;
    thirdAdvisorFirstName: string;
    thirdAdvisorLastName: string;
    thirdAdvisorEmail: string;
    additionalMediaEquipment: string;
    additionalRequirements: string;
    other: boolean;
    timestamp: string;
    topComments?: Array<TopComment>;
    approval: boolean;
}
