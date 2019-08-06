import {Component, OnInit} from '@angular/core';
import {SubmissionListService} from './submissionList.service';
import {Submission} from '../newSubmission/submission';
import {Observable} from 'rxjs';
import {AppService} from '../app.service';
import {AuthenticationService} from "../authentication.service";
import {User} from "../user";


@Component({
    selector: 'app-submissions-component',
    templateUrl: 'submissionList.component.html',
    styleUrls: ['./submissionList.component.scss'],
    providers: [AppService]
})

export class SubmissionListComponent implements OnInit {

    // Inject the SubmissionsService into this component.
    constructor(public submissionListService: SubmissionListService,
                private authenticationService: AuthenticationService) {
    }

    public user: User;
    // These are public so that tests can reference them (.spec.ts)
    public submissions: Submission[] = []; // full list of submissions
    // The ID of the submission
    private highlightedID: { '$oid': string } = {'$oid': ''};

    isHighlighted(submission: Submission): boolean {
        return submission._id['$oid'] === this.highlightedID['$oid'];
    }
    // This function shows today's submissions or all submissions based on the the given type
    showSubmissions(): Submission[] {
        console.log(this.submissions);
        this.submissions.filter(submission => {
                return true;
            });
        return this.submissions;
    }

    fixDate(date: string): string {
        let d = new Date(date);
        return d.toDateString();
    }

    grabSubmissionId(id) {
        this.submissionListService.grabAbstractId(id);
    }

    /**
     * Starts an asynchronous operation to update the submissions list
     *
     */
    refreshSubmissions(): Observable<Submission[]> {
        // Get Submissions returns an Observable, basically a "promise" that
        // we will get the data from the server.
        //
        // Subscribe waits until the data is fully downloaded, then
        // performs an action on it (the first lambda)

        //console.log('this is submissions.component.ts and it has this for userID: ' + localStorage.getItem('userID'));

        let userID = this.user.SubjectID;

        if (userID == null) {
            userID = '';
        }
        let submissionObservable: Observable<Submission[]>;

        if (this.user.Role.includes('user')) {
            submissionObservable = this.submissionListService.getSubmissionsForUser(userID);
        } else if (this.user.Role.includes('reviewer')) {
            //submissionObservable = this.submissionListService.getSubmissionsForReview();
        } else if (this.user.Role.includes('admin' || 'chair')) {
            submissionObservable = this.submissionListService.getSubmissions();
        }

        //console.log(submissionObservable);
        submissionObservable.subscribe(
            submissions => {
                //console.log(submissions);
                if (submissions != null) {
                    this.submissions = submissions;
                }
            },
            err => {
                console.log(err);
            });
        return submissionObservable;
    }

    // loads the list of submissions for the page
    loadService(): void {
        if (this.user.Role.includes('user')) {
            this.submissionListService.getSubmissionsForUser(this.user.SubjectID).subscribe(
                submissions => {
                    this.submissions = submissions;
                },
                err => {
                    console.log(err);
                }
            );
        } else if (this.user.Role.includes('reviewer')) {
            /*this.submissionListService.getSubmissionsForReview(this.user.SubjectID).subscribe(
                submissions => {
                    this.submissions = submissions;
                },
                err => {
                    console.log(err);
                }
            );*/
        } else if (this.user.Role.includes('admin' || 'chair')) {
            this.submissionListService.getSubmissions().subscribe(
                submissions => {
                    this.submissions = submissions;
                },
                err => {
                    console.log(err);
                }
            );
        }

    }

    // Runs when the page is initialized
    ngOnInit(): void {
        this.authenticationService.user$.subscribe(user => {
            this.user = user;
        });
        this.refreshSubmissions();
        this.loadService();
    }
}
