import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "../user";
import {environment} from "../../environments/environment";
import {BehaviorSubject, Observable} from "rxjs";
import {AuthenticationService} from "../authentication.service";

@Injectable()
export class AccountInfoService {
    readonly baseUrl: string = environment.API_URL + 'users/';
    private editUserUrl: string = this.baseUrl;

    public isAdmin$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

    public user: User;

    constructor(private http: HttpClient, private authService: AuthenticationService) {
        this.authService.user$.subscribe(value => {
            this.user = value;
            this.isAdmin();
        });
    }

    saveShirtSize(newUser: User): Observable<{ShirtSize: string}> {
        this.editUserUrl = this.baseUrl;
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            }),
        };

        return this.http.put<{ShirtSize: string}>(this.editUserUrl + newUser.SubjectID, newUser, httpOptions);
    }

    isAdmin(): void {
        if (this.user) {
            if(this.user.Role.includes('admin')){
                return this.isAdmin$.next(true);
            }
        }
    }

}
