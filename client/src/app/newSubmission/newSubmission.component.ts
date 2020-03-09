import {Component, OnInit} from '@angular/core';
import {Submission} from "./submission";
import {NewSubmissionService} from "./newSubmission.service";
import {MatRadioChange, MatSnackBar} from '@angular/material';
import {AuthenticationService} from "../authentication.service";
import {User} from "../user";
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Disciplines} from "./disciplines";
import {Categories} from "./categories";
import {SponsoredOrganizations} from "./sponsoredOrganizations";

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

    public otherAcedemicDiscipline = "";
    public otherAcademic = false;

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
            });

        this.newSubmissionService.getCategories().subscribe(
            categories => {
                this.categories = categories;
                console.log('cats: ' + this.categories);
            });

        this.newSubmissionService.getSponsoredOrganizations().subscribe(
            sponsoredOrganizations => {
                this.sponsoredOrganizations = sponsoredOrganizations;
                console.log('spons: ' + this.sponsoredOrganizations);
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
