import {Disciplines} from "./disciplines";

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
    academicDiscipline: Disciplines[];
    willingToBeFeaturePresenter: string;
    sponOrganization: boolean[];
    miscSponOrganization: string;
    category: boolean[];
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
    comments?: string;
    approval: boolean;
}


