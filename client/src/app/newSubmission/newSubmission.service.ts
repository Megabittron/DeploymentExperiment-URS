import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Submission} from './submission';
import {environment} from '../../environments/environment';
import 'rxjs/add/observable/of';


@Injectable()
export class newSubmissionService {
    readonly baseUrl: string = environment.API_URL + 'abstracts';
    private submissionUrl: string = this.baseUrl;
    private noID: boolean = false;
    private emptyObservable: Observable<{'$oid': string}> = Observable.of({'$oid': ""});

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

        console.log(this.http.post<{'$oid': string}>(this.submissionUrl + '/new', newSubmission, httpOptions));

        if(this. noID){
            return this.emptyObservable;
        }
        // This function sends a post request to add a new emotion with the emotion data as the body with specified headers.
        return this.http.post<{'$oid': string}>(this.submissionUrl + '/new', newSubmission, httpOptions);
    }
}
