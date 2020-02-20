import {Component, OnInit} from '@angular/core';
import {SubmissionListService} from "../submissionList/submissionList.service";
import {Submission} from '../newSubmission/submission';
import {Observable} from 'rxjs';
import {AppService} from '../app.service';

@Component({
    selector: 'edit-abstract-component',
    templateUrl: 'editAbstract.component.html',
    styleUrls: ['./editAbstract.component.scss'],
    providers: [AppService]
})

export class EditAbstractComponent implements OnInit {

    constructor(public submissionListService: SubmissionListService) {}

    public submission: Submission;

    getSubmission() {
        let submissionObservable: Observable<Submission>;
        submissionObservable = this.submissionListService.getSingleSubmissionById(this.submissionListService.singleAbstractId);
        submissionObservable.subscribe(
            submission => {
                if (submission != null) {
                    this.submission = submission;
                }
            }
        );
    }

    ngOnInit() {
        this.getSubmission();
    }
}
