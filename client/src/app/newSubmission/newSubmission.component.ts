
import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {Submission} from "./submission";
import {NewSubmissionService} from "./newSubmission.service";
import {Observable} from 'rxjs/Observable';
import {MatSnackBar} from '@angular/material';

@Component({
    selector: 'app-newSubmission-component',
    templateUrl: 'newSubmission.component.html',
    styleUrls: ['./newSubmission.component.scss'],
    providers: [NewSubmissionService]
})
export class NewSubmissionComponent {

    constructor(public snackBar: MatSnackBar,
                public newSubmissionService: NewSubmissionService) {
    }

    private highlightedID: { '$oid': string } = {'$oid': ''}

    public presentationTitle = "";
    public abstractContent = "";
    public submissionFormat = "";
    public presentationType = "";
    public changePresentationFormat = "";
    public firstPresenterFirstName = "";
    public firstPresenterLastName = "";
    public firstPresenterEmail = "";
    public secondPresenter = false;
    public secondPresenterFirstName = "";
    public secondPresenterLastName = "";
    public secondPresenterEmail = "";
    public thirdPresenter = false;
    public thirdPresenterFirstName = "";
    public thirdPresenterLastName = "";
    public thirdPresenterEmail = "";
    public academicDiscipline = "";
    public featurePresenter = "";
    public sponOrganization = "";
    public firstAdvisorFirstName = "";
    public firstAdvisorLastName = "";
    public firstAdvisorEmail = "";
    public secondAdvisor = false;
    public secondAdvisorFirstName = "";
    public secondAdvisorLastName = "";
    public secondAdvisorEmail = "";
    public thirdAdvisor = false;
    public thirdAdvisorFirstName = "";
    public thirdAdvisorLastName = "";
    public thirdAdvisorEmail = "";
    public additionalMediaEquipment = "";
    public additionalInfo = "";
    public other: boolean;

    saveSubmission(): void {
        const newSubmission: Submission = {
            _id: '',
            userID: localStorage.getItem("userID"),
            presentationTitle: this.presentationTitle,
            abstractContent: this.abstractContent,
            submissionFormat: this.submissionFormat,
            presentationType: this.presentationType,
            changePresentationFormat: this.changePresentationFormat,
            firstPresenterFirstName: this.firstPresenterFirstName,
            firstPresenterLastName: this.firstPresenterLastName,
            firstPresenterEmail: this.firstPresenterEmail,
            secondPresenterFirstName: this.secondPresenterFirstName,
            secondPresenterLastName: this.secondPresenterLastName,
            secondPresenterEmail: this.secondPresenterEmail,
            thirdPresenterFirstName: this.thirdPresenterFirstName,
            thirdPresenterLastName: this.thirdPresenterLastName,
            thirdPresenterEmail: this.thirdPresenterEmail,
            academicDiscipline: this.academicDiscipline,
            featurePresenter: this.featurePresenter,
            sponOrganization: this.sponOrganization,
            firstAdvisorFirstName: this.firstAdvisorFirstName,
            firstAdvisorLastName: this.firstAdvisorLastName,
            firstAdvisorEmail: this.firstAdvisorEmail,
            secondAdvisorFirstName: this.secondAdvisorFirstName,
            secondAdvisorLastName: this.secondAdvisorLastName,
            secondAdvisorEmail: this.secondAdvisorEmail,
            thirdAdvisorFirstName: this.thirdAdvisorFirstName,
            thirdAdvisorLastName: this.thirdAdvisorLastName,
            thirdAdvisorEmail: this.thirdAdvisorEmail,
            additionalMediaEquipment: this.additionalMediaEquipment,
            additionalInfo: this.additionalInfo,
            other: this.other

        };

        console.log(newSubmission);
        this.newSubmissionService.addNewSubmission(newSubmission).subscribe(
            addSubmissionResult => {
                this.highlightedID = addSubmissionResult;
                this.snackBar.open("Submission Submitted", "CLOSE", {
                    duration: 2000,
                })
            }
        )
    }

    sectionOneComplete() {
        return this.presentationTitle == ""  || this.abstractContent == "" || this.submissionFormat == ""
            || this.presentationType == "" || this.changePresentationFormat == "";

    }

    sectionTwoComplete() {
        return this.firstPresenterFirstName == "" || this.firstPresenterLastName == "" || this.firstPresenterEmail == ""
        || this.academicDiscipline == "" || this.featurePresenter == "";
    }

    sectionFourComplete() {
        return this.firstAdvisorFirstName == "" || this.firstAdvisorLastName == "" ||
            this.firstAdvisorEmail == "";
    }
}
