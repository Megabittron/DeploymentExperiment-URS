import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Submission} from '../newSubmission/submission';
import {environment} from '../../environments/environment';




@Injectable()
export class SubmissionListService {
    readonly baseUrl: string = environment.API_URL + 'abstracts';
    readonly baseUrlSingleAbstract: string = environment.API_URL + 'abstract';
    private submissionUrl: string = this.baseUrl;
    private singleSubmissionUrl: string = this.baseUrlSingleAbstract;

    public singleAbstractId = "";

    constructor(
        private http: HttpClient) {
    }

    // This function gets submissions from the server
    getSubmissionsForUser(userID: string): Observable<Submission[]> {
        this.submissionUrl = this.baseUrl;

        return this.http.get<Submission[]>(this.submissionUrl + '/' + userID);
    }

    getSubmissions(): Observable<Submission[]> {
        this.submissionUrl = this.baseUrl;

        return this.http.get<Submission[]>(this.submissionUrl);
    }

    getSubmissionsForReview(userID: string): Observable<Submission[]> {
        this.submissionUrl = this.baseUrl;

        return this.http.get<Submission[]>(this.submissionUrl + '/' + userID);
    }

    getUsersSubmissionsById(id: string): Observable<Submission> {
        this.submissionUrl = this.baseUrl;
        return this.http.get<Submission>(this.submissionUrl + '/' + id);
    }

    getSingleSubmissionById(id: string): Observable<Submission> {
        this.submissionUrl = this.baseUrl;
        return this.http.get<Submission>( this.singleSubmissionUrl + '/' + id);
    }

    grabAbstractId(id) {
        this.singleAbstractId = id;
    }

    /*// sets the submissionUrl to the serachParam
    private parameterPresent(searchParam: string) {
        return this.submissionUrl.indexOf(searchParam) !== -1;
    }

    // remove the parameter and, if present, the &
    private removeParameter(searchParam: string) {
        const start = this.submissionUrl.indexOf(searchParam);
        let end = 0;
        if (this.submissionUrl.indexOf('&') !== -1) {
            end = this.submissionUrl.indexOf('&', start) + 1;
        } else {
            end = this.submissionUrl.indexOf('&', start);
        }
        this.submissionUrl = this.submissionUrl.substring(0, start) + this.submissionUrl.substring(end);
    }
    // Helper Functions//

    // Checks if the submission has a userId, and sets this.noID to true if there isn't an
    // ID associated with the submission
    filterByUserID(userID: string): void {
        if (!(userID == null || userID === '')) {
            if (this.parameterPresent('userID=') ) {
                // there was a previous search by category that we need to clear
                this.removeParameter('userID=');
            }
            if (this.submissionUrl.indexOf('?') !== -1) {
                // there was already some information passed in this url
                this.submissionUrl += 'userID=' + userID + '&';
            } else {
                // this was the first bit of information to pass in the url
                this.submissionUrl += '?userID=' + userID + '&';
            }
        } else {
            // there was no userID
            this.noID = true;
        }
    }*/
}
