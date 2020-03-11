import {Disciplines} from "./disciplines";
import {SponsoredOrganizations} from "./sponsoredOrganizations";
import {Categories} from "./categories";
import {TopComment} from "../submissionView/comment";
import {Presenters} from "./presenters";
import {Advisors} from "./advisors";

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
    category: Categories[];
    advisors: Advisors[];
    additionalMediaEquipment: string;
    additionalRequirements: string;
    other: string;
    timestamp: string;
    topComments?: Array<TopComment>;
    approval: boolean;
}


