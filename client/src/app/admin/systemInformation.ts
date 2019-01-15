import {ReviewGroup} from "./reviewGroup";


export interface SystemInformation {
    primarySubmissions: number;
    submissionStored: number;
    submissionsFlagged: number;
    totalUsers;
    users: number;
    admins: number;
    chairs: number;
    reviewers: number;
    reviewGroups: Array<ReviewGroup>;
    xs: number;
    s: number;
    m: number;
    l: number;
    xl: number;
    xxl: number;
}
