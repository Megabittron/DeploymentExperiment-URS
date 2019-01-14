import {ReviewGroup} from "./reviewGroup";


export interface SystemInformation {
    primarySubmissions: number;
    submissionStored: number;
    submissionsFlagged: number;
    users: number;
    admins: number;
    chairs: number;
    reviewGroups: {
        [key: string]: ReviewGroup
    };
    xs: number;
    s: number;
    m: number;
    l: number;
    xl: number;
    xxl: number;
}
