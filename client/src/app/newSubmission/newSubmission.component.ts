import {Component, OnInit} from '@angular/core';
import {Submission} from "./submission";
import {NewSubmissionService} from "./newSubmission.service";
import {MatRadioChange, MatSnackBar} from '@angular/material';
import {AuthenticationService} from "../authentication.service";
import {User} from "../user";
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Disciplines} from "./disciplines";

export interface Discipline {
    value: string;
    viewValue: string;
}

@Component({
    selector: 'app-newSubmission-component',
    templateUrl: 'newSubmission.component.html',
    styleUrls: ['./newSubmission.component.scss'],
    providers: [NewSubmissionService]
})
export class NewSubmissionComponent implements OnInit{

    constructor(public snackBar: MatSnackBar,
                public newSubmissionService: NewSubmissionService,
                private authenticationService: AuthenticationService,
                private _formBuilder: FormBuilder) {
    }

    public user: User;
    public disciplines: Disciplines[] = [];

    firstFormGroup: FormGroup;
    secondFormGroup: FormGroup;
    thirdFormGroup: FormGroup;

    private highlightedID: { '$oid': string } = {'$oid': ''};

    public presentationTitle = "";
    public abstractContent = "";
    public submissionFormat = "";
    public presentationType = "";
    public willingToChangePresentationFormat = "undecided";
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
    // Note: I'm 99% sure that initializing these 35 booleans, each representing a discipline, is super busted.
    // On a deadline here... I just refactored disciplines from hardcoded HTML fields to the `disciplines` array, which
    // was a good move. For each improvement a student project makes, an equal and opposite bad choice is made.
    // That is law. -MS, 3/6/20 @ 12:19 AM
    public academicDiscipline = [];
    public willingToBeFeaturePresenter = "undecided";
    public sponOrganization = [false, false, false, false]; //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
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
    public additionalRequirements = "";
    public other: boolean;
    public timestamp = "";
    public approval = null;
    public category = [false, false, false, false];
    public miscSponOrganization = "";

    public otherAcedemicDiscipline = "";
    public otherAcademic = false;

    doesItExist() {
        console.log("~~~~~~~~~~~~~");
        console.log("other: " + this.otherAcedemicDiscipline);
        console.log("aceDisc: " + this.academicDiscipline);
    }

    saveSubmission(): void {

        //trims 'other' categories that were removed and subsequently empty strings
        this.academicDiscipline.filter(function (el) {
            return el != null;
        });

        if(this.otherAcedemicDiscipline !== "" && this.otherAcademic) {
            this.academicDiscipline.push("other" + this.otherAcedemicDiscipline);
        }

        const newSubmission: Submission = {
            userID: this.user.SubjectID,
            presentationTitle: this.presentationTitle,
            abstractContent: this.abstractContent,
            submissionFormat: this.submissionFormat,
            presentationType: this.presentationType,
            willingToChangePresentationFormat: this.willingToChangePresentationFormat,
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
            willingToBeFeaturePresenter: this.willingToBeFeaturePresenter,
            sponOrganization: this.sponOrganization,
            category: this.category,
            miscSponOrganization: this.miscSponOrganization,
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
            additionalRequirements: this.additionalRequirements,
            other: this.other,
            timestamp: this.timestamp,
            approval: this.approval
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

    newSponsor(sponsor: number){
        this.sponOrganization[sponsor] = !this.sponOrganization[sponsor];
    }

    newCategory(category: number){
        this.category[category] = !this.category[category];
    }

    addOrRemoveDiscipline(discipline: Disciplines): void {
        if(!this.academicDiscipline.includes(discipline)) {
            this.academicDiscipline.push(discipline);
        } else {
            this.academicDiscipline.splice(this.academicDiscipline.indexOf(discipline));
        }
    }

    onFeaturePresentationChange(change: MatRadioChange): void {
        this.willingToBeFeaturePresenter = change.value;
    }

    onFormatChange(change: MatRadioChange): void {
        this.willingToChangePresentationFormat = change.value;
    }

    sectionOneComplete() {
        return this.presentationTitle == ""  || this.abstractContent == "" || this.submissionFormat == ""
            || this.presentationType == "" || this.willingToChangePresentationFormat == "undecided";
    }

    //TODO: fix academicDiscipline requirement when you are not dead tired
    sectionTwoComplete() {
        return this.firstPresenterFirstName == "" || this.firstPresenterLastName == "" || this.firstPresenterEmail == ""
        || this.willingToBeFeaturePresenter == "undecided" || !(this.academicDiscipline.length != 0 || this.otherAcedemicDiscipline != "");
    }

    sectionFourComplete() {
        return this.firstAdvisorFirstName == "" || this.firstAdvisorLastName == "" ||
            this.firstAdvisorEmail == "";
    }

    ngOnInit(): void {
        this.authenticationService.user$.subscribe(user => {
            this.user = user;
        });

        this.newSubmissionService.getDisciplines().subscribe(
            disciplines => {
                this.disciplines = disciplines;
                console.log('discs: ' + this.disciplines);
            });

        // STEP TWO: Title/Abstract/Format step
        this.firstFormGroup = this._formBuilder.group({
            firstCtrlOne: ['', Validators.required],
            firstCtrlTwo: ['', Validators.required],
            firstCtrlThree: ['', Validators.required],
            firstCtrlFour: ['', Validators.required],
            firstCtrlFive: ['', Validators.required]
        });

        // STEP THREE: Presenter(s)/Discipline/Featured step
        this.secondFormGroup = this._formBuilder.group({
            secondCtrlOne: ['', Validators.required],
            secondCtrlTwo: ['', Validators.required],
            secondCtrlThree: ['', Validators.required],
            secondCtrlFour: ['', Validators.required],
            secondCtrlFive: ['', Validators.required]
        });

        // STEP 5: Advisor(s)
        this.thirdFormGroup = this._formBuilder.group({
            thirdCtrlOne: ['', Validators.required],
            thirdCtrlTwo: ['', Validators.required],
            thirdCtrlThree: ['', Validators.required]
        });
    }
}
