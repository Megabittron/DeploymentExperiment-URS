
export interface Submission {
    _id: string;
    userID: string;
    presentationTitle: string;
    abstractContent: string;
    submissionFormat: string;
    presentationType: string;
    changePresentationFormat: string;
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
    featurePresenter: string;
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
    additionalInfo: string;
    other: boolean;
    timestamp: string;
}
