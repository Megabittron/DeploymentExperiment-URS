import {Component, OnInit} from "@angular/core";
import {SubmissionListService} from "../submissionList/submissionList.service";
import {Submission} from "../newSubmission/submission";
import {Observable} from "rxjs";
import {AuthenticationService} from "../authentication.service";
import {User} from "../user";

@Component({
    selector: 'submissionView.component',
    templateUrl: 'submissionView.component.html',
    styleUrls: ['./submissionView.component.scss'],
    providers: []
})

export class SubmissionViewComponent implements OnInit {

    constructor(public submissionListService: SubmissionListService,
                public authenticationService: AuthenticationService) {}

    public submission: Submission;
    public otherDiscipline: string;
    public user: User;

    getSubmission() {
        let submissionObservable: Observable<Submission>;
        submissionObservable = this.submissionListService.getSingleSubmissionById(this.submissionListService.singleAbstractId);
        submissionObservable.subscribe(
            submission => {
                if (submission != null) {
                    this.submission = submission;
                    this.otherDiscipline = this.submission.academicDiscipline.slice(-1).toString();
                }
            }
        );
    }

    ngOnInit() {
        this.authenticationService.user$.subscribe(user => {
            this.user = user;
        });

        this.getSubmission();
    }

}
