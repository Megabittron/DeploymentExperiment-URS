import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "../user";
import {environment} from "../../environments/environment";

@Injectable()
export class AccountInfoService {
    readonly baseUrl: string = environment.API_URL + 'user';
    private editUserUrl: string = this.baseUrl;

    constructor(private http: HttpClient) {
    }

    editShirtSize(newUser: User) {
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            }),
        };

        return this.http.put(this.editUserUrl + "/" + newUser._id, newUser, httpOptions);
    }

}
