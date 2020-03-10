import {Disciplines} from "./disciplines";
import {SponsoredOrganizations} from "./sponsoredOrganizations";
import {Categories} from "./categories";
import {TopComment} from "../submissionView/comment";
import {Presenters} from "./presenters";
import {FormArray, FormGroup} from "@angular/forms";

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
    presenters: Presenters[];
    academicDiscipline: Disciplines[];
    willingToBeFeaturePresenter: string;
    sponOrganization: SponsoredOrganizations[];
    miscSponOrganization: string;
    category: Categories[];
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
    other: string;
    timestamp: string;
    topComments?: Array<TopComment>;
    approval: boolean;
}


