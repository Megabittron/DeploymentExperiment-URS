import {Component, Input, OnInit} from '@angular/core';
import {Submission} from "./submission";
import {NewSubmissionService} from "./newSubmission.service";
import {MatRadioChange, MatSnackBar} from '@angular/material';
import {AuthenticationService} from "../authentication.service";
import {User} from "../user";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Disciplines} from "./disciplines";
import {Categories} from "./categories";
import {SponsoredOrganizations} from "./sponsoredOrganizations";
import {Presenters} from "./presenters";
import {ArrayType} from "@angular/compiler";

export interface Discipline {
    value: string;
    viewValue: string;
}

declare const gapi: any;

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
                private _formBuilder: FormBuilder,
                private fb: FormBuilder,
                private formBuilder: FormBuilder) {
    }

    public user: User;
    public disciplines: Disciplines[] = [];
    public categories: Categories[] = [];
    public sponsoredOrganizations: SponsoredOrganizations[] = [];

    firstFormGroup: FormGroup;
    secondFormGroup: FormGroup;
    thirdFormGroup: FormGroup;

    private highlightedID: { '$oid': string } = {'$oid': ''};

    public presentationTitle = "";
    public abstractContent = "";
    public submissionFormat = "";
    public presentationType = "";
    public willingToChangePresentationFormat = "undecided";
    public secondPresenterFirstName = "";
    public secondPresenterLastName = "";
    public secondPresenterEmail = "";
    public thirdPresenterFirstName = "";
    public thirdPresenterLastName = "";
    public thirdPresenterEmail = "";
    public academicDiscipline = [];
    public willingToBeFeaturePresenter = "undecided";
    public sponOrganization = [];
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
    public other: "";
    public timestamp = "";
    public approval = null;
    public category = [];
    public miscSponOrganization = "";

    public userEmail = "";

    public otherAcedemicDiscipline = "";
    public otherAcademic = false;

    presenterWhoIsSubmitting: Presenters = {
        presenterFirstName: this.userEmail = this.authenticationService.auth2.currentUser.get().getBasicProfile().getGivenName(),
        presenterLastName: this.userEmail = this.authenticationService.auth2.currentUser.get().getBasicProfile().getFamilyName(),
        presenterEmail: this.userEmail = this.authenticationService.auth2.currentUser.get().getBasicProfile().getEmail()
    };

    saveSubmission(): void {

        if(!this.firstPresenterAdded) {  //adds the submitting users as the first user on the FormArray, hidden from user
            const firstPresenter = new FormGroup({
                presenterFirstName: new FormControl(this.presenterWhoIsSubmitting.presenterFirstName),
                presenterLastName: new FormControl(this.presenterWhoIsSubmitting.presenterLastName),
                presenterEmail: new FormControl(this.presenterWhoIsSubmitting.presenterEmail)
            });
            this.presenters.push(firstPresenter);
            this.firstPresenterAdded = !this.firstPresenterAdded;
        }

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
            presenters: this.presenters.value,
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

    addOrRemoveDiscipline(discipline: Disciplines): void {
        if(!this.academicDiscipline.includes(discipline)) {
            this.academicDiscipline.push(discipline);
        } else {
            this.academicDiscipline.splice(this.academicDiscipline.indexOf(discipline));
        }
    }

    addOrRemoveCategory(category: Categories): void {
        if(!this.category.includes(category)) {
            this.category.push(category);
        } else {
            this.category.splice(this.category.indexOf(category));
        }
    }

    addOrRemoveSponsoredOrganization(sponsor: SponsoredOrganizations): void {
        if(!this.sponOrganization.includes(sponsor)) {
            this.sponOrganization.push(sponsor);
        } else {
            this.sponOrganization.splice(this.sponOrganization.indexOf(sponsor));
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
        return this.willingToBeFeaturePresenter == "undecided" || !(this.academicDiscipline.length != 0 || this.otherAcedemicDiscipline != "");
    }

    sectionFourComplete() {
        return this.firstAdvisorFirstName == "" || this.firstAdvisorLastName == "" ||
            this.firstAdvisorEmail == "";
    }

    presenters = new FormArray([]);
    public firstPresenterAdded = false;
    public peopleAdded = 0;
    addPresenter() {
        // Look, I was on a deadline
        this.peopleAdded += 1;
        if(this.peopleAdded > 10) { //stops from adding more than 10 people
            return;
        }
        if(!this.firstPresenterAdded) {  //adds the submitting users as the first user on the FormArray, hidden from user
            const firstPresenter = new FormGroup({
                presenterFirstName: new FormControl(this.presenterWhoIsSubmitting.presenterFirstName),
                presenterLastName: new FormControl(this.presenterWhoIsSubmitting.presenterLastName),
                presenterEmail: new FormControl(this.presenterWhoIsSubmitting.presenterEmail)
            });
            this.presenters.push(firstPresenter);
            this.firstPresenterAdded = !this.firstPresenterAdded;
        }

        const group = new FormGroup({
            presenterFirstName: new FormControl('', Validators.required),
            presenterLastName: new FormControl('', Validators.required),
            presenterEmail: new FormControl('', Validators.email)
        });

        this.presenters.push(group);
    }

    removePresenter(index: number) {
        this.presenters.removeAt(index);
    }

    form = new FormGroup({
        presenterFirstName: new FormControl('', Validators.required),
        presenterLastName: new FormControl('', Validators.required),
        presenterEmail: new FormControl('', Validators.required)
    });

    ngOnInit(): void {
        this.authenticationService.user$.subscribe(user => {
            this.user = user;
        });

        this.newSubmissionService.getDisciplines().subscribe(
            disciplines => {
                this.disciplines = disciplines;
            });

        this.newSubmissionService.getCategories().subscribe(
            categories => {
                this.categories = categories;
            });

        this.newSubmissionService.getSponsoredOrganizations().subscribe(
            sponsoredOrganizations => {
                this.sponsoredOrganizations = sponsoredOrganizations;
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
