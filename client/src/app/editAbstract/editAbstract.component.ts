import {Component, OnInit} from '@angular/core';
import {SubmissionListService} from "../submissionList/submissionList.service";
import {Submission} from '../newSubmission/submission';
import {Observable} from 'rxjs';
import {AppService} from '../app.service';
import {MatRadioChange} from "@angular/material";

@Component({
    selector: 'edit-abstract-component',
    templateUrl: 'editAbstract.component.html',
    styleUrls: ['./editAbstract.component.scss'],
    providers: [AppService]
})

export class EditAbstractComponent implements OnInit {

    constructor(public submissionListService: SubmissionListService) {}

    public submission: Submission;

    public other = false;

    onFeaturePresentationChange(change: MatRadioChange): void {
        this.submission.willingToBeFeaturePresenter = change.value;
    }

    onFormatChange(change: MatRadioChange): void {
        this.submission.willingToChangePresentationFormat = change.value;
    }

    newSponsor(sponsor: number){
        this.submission.sponOrganization[sponsor] = !this.submission.sponOrganization[sponsor];
        console.log(this.submission.sponOrganization);
    }

    newCategory(category: number){
        this.submission.category[category] = !this.submission.category[category];
        console.log(this.submission.category);
    }

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

    saveAbstractEdit() {
        console.log(this.submission);
    }

    ngOnInit() {
        this.getSubmission();
    }
}
