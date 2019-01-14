import {User} from "../user";

export interface ReviewGroup {
    users: {
        [key: string]: User
    };

}
