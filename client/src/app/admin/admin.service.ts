import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {SystemInformation} from "./systemInformation";
import {ReviewGroup} from "./reviewGroup";
import {User} from "../user";

@Injectable()
export class AdminService {
    readonly baseUrl: string = environment.API_URL + 'system-information';
    readonly env: string = environment.API_URL;

    private editUserUrl: string = environment.API_URL + 'users/roles/';

    private systemInformationUrl: string = this.baseUrl;

    public userObj: User;

    constructor(private http: HttpClient) {
    }

    grabUserObj(user: User){
        this.userObj = user;
    }

    getSystemInformation(): Observable<SystemInformation> {
        this.systemInformationUrl = this.baseUrl;

        return this.http.get<SystemInformation>(this.systemInformationUrl);
    }

    updateReviewGroups(newReviewGroups: ReviewGroup[]): Observable<ReviewGroup[]> {
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            }),
            responseType: 'text' as 'json'
        };
        return this.http.post<ReviewGroup[]>(
            this.env + 'review-groups/editReviewGroups', newReviewGroups, httpOptions);
    }

    getReviewGroups(): Observable<ReviewGroup[]> {
        return this.http.get<ReviewGroup[]>(this.env + 'review-groups');
    }

    getUserInfo(): Observable<User[]> {
        return this.http.get<User[]>(this.env + 'users')
    }

    updateUserRole(newRoles: User): Observable<{Role: string}> {
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            }),
        };
        return this.http.put<{Role: string}>(this.editUserUrl + newRoles.SubjectID, newRoles, httpOptions);
    }
}
