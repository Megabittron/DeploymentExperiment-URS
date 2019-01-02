import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "../user";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs/Observable";

@Injectable()
export class AccountInfoService {
    readonly baseUrl: string = environment.API_URL + 'user/';
    private editUserUrl: string = this.baseUrl;

    constructor(private http: HttpClient) {
    }

    saveShirtSize(newUser: User): Observable<{'$oid': string}> {
        this.editUserUrl = this.baseUrl;
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            }),
        };

        return this.http.put<{'$oid': string}>(this.editUserUrl + newUser.SubjectID, newUser, httpOptions);
    }

}
