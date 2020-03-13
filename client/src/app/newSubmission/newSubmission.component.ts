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
    public categories: Categories[] = [];
    public sponsoredOrganizations: SponsoredOrganizations[] = [];

    firstFormGroup: FormGroup;
    secondFormGroup: FormGroup;
    thirdFormGroup: FormGroup;
    ninthFormGroup: FormGroup;
    presenterFormGroup: FormGroup;

    private highlightedID: { '$oid': string } = {'$oid': ''};

    public presentationTitle = "";
    public abstractContent = "";
    public submissionFormat = "";
    public presentationType = "";
    public willingToChangePresentationFormat = "undecided";
    public academicDiscipline = [];
    public willingToBeFeaturePresenter = "undecided";
    public sponOrganization = [];
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
    public otherOrg = false;

    presenterWhoIsSubmitting: Presenters = {
        presenterFirstName: this.userEmail = this.authenticationService.auth2.currentUser.get().getBasicProfile().getGivenName(),
        presenterLastName: this.userEmail = this.authenticationService.auth2.currentUser.get().getBasicProfile().getFamilyName(),
        presenterEmail: this.userEmail = this.authenticationService.auth2.currentUser.get().getBasicProfile().getEmail()
    };

    saveSubmission(): void {

        const firstPresenter = new FormGroup({
            presenterFirstName: new FormControl(this.presenterWhoIsSubmitting.presenterFirstName),
            presenterLastName: new FormControl(this.presenterWhoIsSubmitting.presenterLastName),
            presenterEmail: new FormControl(this.presenterWhoIsSubmitting.presenterEmail)
        });
        this.presenters.push(firstPresenter);

        //trims 'other' categories that were removed and subsequently empty strings
        this.academicDiscipline.filter(function (el) {
            return el != null;
        });

        if(this.otherAcedemicDiscipline.trim() !== "" && this.otherAcademic) {
            this.academicDiscipline.push("other" + this.otherAcedemicDiscipline);
        }

        this.sponOrganization.filter(function (el) {
            return el != null;
        });

        if(this.miscSponOrganization.trim()!== "") {
            this.sponOrganization.push("other" + this.miscSponOrganization);
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
            advisors: this.advisors.value,
            additionalMediaEquipment: this.additionalMediaEquipment,
            additionalRequirements: this.additionalRequirements,
            other: this.other,
            timestamp: this.timestamp,
            approval: this.approval
        };

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

    presentersComplete() {
        return !this.presenters.valid;
    }

    sectionOneComplete() {
        return this.presentationTitle == ""  || this.abstractContent == "" || this.submissionFormat == ""
            || this.presentationType == "" || this.willingToChangePresentationFormat == "undecided";
    }

    sectionTwoComplete() {
        return this.willingToBeFeaturePresenter == "undecided"
            || !(this.academicDiscipline.length != 0 || this.otherAcedemicDiscipline != "")
            || !(this.presenters.valid);
    }

    sectionThreeComplete() {
        return !this.advisors.valid;
    }

    presenters = new FormArray([]);
    addPresenter() {
        if(this.presenters.length > 10) { //stops from adding more than 10 people
            return;
        }

        const group = new FormGroup({
            presenterFirstName: new FormControl('', Validators.compose([Validators.required, Validators.maxLength(100)])),
            presenterLastName: new FormControl('', Validators.compose([Validators.required, Validators.maxLength(100)])),
            presenterEmail: new FormControl('', Validators.compose([Validators.email, Validators.required, Validators.maxLength(100)]))
        });

        this.presenters.push(group);
    }

    removePresenter(index: number) {
        this.presenters.removeAt(index);
    }

    advisors = new FormArray([]);
    addAdvisor() {
        if(this.advisors.length > 3) {
            return;
        }

        const group = new FormGroup({
            advisorFirstName: new FormControl('', Validators.compose([Validators.required, Validators.maxLength(100)])),
            advisorLastName: new FormControl('', Validators.compose([Validators.required, Validators.maxLength(100)])),
            advisorEmail: new FormControl('', Validators.compose([Validators.required, Validators.email, Validators.maxLength(100)]))
        });

        this.advisors.push(group);
    }

    removeAdvisor(index: number) {
        this.advisors.removeAt(index);
    }

    formGroup: FormGroup;

    ngOnInit(): void {

        this.addAdvisor();

        this.formGroup = this._formBuilder.group({
            formArray: this._formBuilder.array([
                this._formBuilder.group({
                    advisorFirstName: new FormControl('', Validators.compose([Validators.required, Validators.maxLength(100)])),
                    advisorLastName: new FormControl('', Validators.compose([Validators.required, Validators.maxLength(100)])),
                    advisorEmail: new FormControl('', Validators.compose([Validators.required, Validators.email, Validators.maxLength(100)]))
                }),
                this._formBuilder.group({
                    emailFormCtrl: ['', Validators.email]
                }),
            ])
        });

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
            titleCtrl: ['', Validators.compose([Validators.required, Validators.maxLength(400)])],
            abstractCtrl: ['', Validators.compose([Validators.required, Validators.maxLength(7000)])],
            subFormatCtrl: ['', Validators.required],
            presTypeCtrl: ['', Validators.required],
            formatChangeCtrl: ['', Validators.required]
        });

        // STEP THREE: Presenter(s)/Discipline/Featured step
        this.secondFormGroup = this._formBuilder.group({
            disciplineCtrl: ['', Validators.required],
            featurePresCtrl: ['', Validators.required]
        });
    }
}
