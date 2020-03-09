import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Submission} from './submission';
import {environment} from '../../environments/environment';
import {Disciplines} from "./disciplines";
import {SponsoredOrganizations} from "./sponsoredOrganizations";
import {Categories} from "./categories";



@Injectable()
export class NewSubmissionService {
    readonly baseUrl: string = environment.API_URL + 'abstracts';
    private submissionUrl: string = this.baseUrl;
    private noID: boolean = false;
    private emptyObservable: Observable<{'$oid': string}>;

    constructor(private http: HttpClient) {
    }

    addNewSubmission(newSubmission: Submission): Observable<{'$oid': string}> {
        this.submissionUrl = this.baseUrl;
        this.noID = false;

        if(newSubmission.userID == null || newSubmission.userID == ""){
            this.noID = true;
        }

        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            }),
        };

        if(this.noID){
            return this.emptyObservable;
        }

        return this.http.post<{'$oid': string}>(this.submissionUrl + '/new', newSubmission, httpOptions);
    }

    getDisciplines(): Observable<Disciplines[]> {
        this.submissionUrl = this.baseUrl;

        return this.http.get<Disciplines[]>(this.submissionUrl + '/disciplines');
    }

    getSponsoredOrganizations(): Observable<SponsoredOrganizations[]> {
        this.submissionUrl = this.baseUrl;

        return this.http.get<SponsoredOrganizations[]>(this.submissionUrl + '/sponsoredOrganizations');
    }

    getCategories(): Observable<Categories[]> {
        this.submissionUrl = this.baseUrl;

        return this.http.get<Categories[]>(this.submissionUrl + '/categories');
    }
}
