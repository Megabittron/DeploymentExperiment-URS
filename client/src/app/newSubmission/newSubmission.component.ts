import {Component, OnInit} from '@angular/core';
import {Submission} from "./submission";
import {NewSubmissionService} from "./newSubmission.service";
import {MatRadioChange, MatSnackBar} from '@angular/material';
import {AuthenticationService} from "../authentication.service";
import {User} from "../user";
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

interface Discipline {
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
    public academicDiscipline = "";
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

    disciplines: Discipline[] = [
        {value: 'african and black american studies', viewValue: 'African and Black American Studies'},
        {value: 'anthropology', viewValue: 'Anthropology'},
        {value: 'art history', viewValue: 'Art History'},
        {value: 'biology', viewValue: 'Biology'},
        {value: 'chemistry & biochemistry', viewValue: 'Chemistry and Biochemistry'},
        {value: 'communication, media, & rhetoric', viewValue: 'Communication, Media, & Rhetoric'},
        {value: 'computer science', viewValue: 'Computer Science'},
        {value: 'economics', viewValue: 'Economics'},
        {value: 'education', viewValue: 'Education'},
        {value: 'elementary education', viewValue: 'Elementary Education'},
        {value: 'english/creative writing', viewValue: 'English/Creative Writing'},
        {value: 'environmental science', viewValue: 'Environmental Science'},
        {value: 'environmental studies', viewValue: 'Environmental Studies'},
        {value: 'french', viewValue: 'French'},
        {value: 'gender, women, and sexuality studies', viewValue: 'Gender, Women, and Sexuality Studies'},
        {value: 'geology', viewValue: 'Geology'},
        {value: 'german studies', viewValue: 'German Studies'},
        {value: 'history', viewValue: 'History'},
        {value: 'human services/general/criminal justice/social justice/human development', viewValue: 'Human Services/General/Criminal Justice/Social Justice/Human Development'},
        {value: 'latin american area studies', viewValue: 'Latin American Area Studies'},
        {value: 'management', viewValue: 'Management'},
        {value: 'medieval studies', viewValue: 'Medieval Studies'},
        {value: 'music', viewValue: 'Music'},
        {value: 'native american and indigenous studies', viewValue: 'Native American and Indigenous Studies'},
        {value: 'philosophy', viewValue: 'Philosophy'},
        {value: 'german studies', viewValue: 'Physics'},
        {value: 'political science', viewValue: 'Political Science'},
        {value: 'psychology', viewValue: 'Psychology'},
        {value: 'secondary education', viewValue: 'Secondary Education'},
        {value: 'social science', viewValue: 'Social Science'},
        {value: 'sociology', viewValue: 'Sociology'},
        {value: 'spanish', viewValue: 'Spanish'},
        {value: 'sport management', viewValue: 'Sport Management'},
        {value: 'statistics', viewValue: 'Statistics'},
        {value: 'studio art', viewValue: 'Studio Art'},
        {value: 'theatre arts', viewValue: 'Theatre Arts'}
    ];

    saveSubmission(): void {
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
        console.log(this.sponOrganization);
    }

    newCategory(category: number){
        this.category[category] = !this.category[category];
        console.log(this.category);
    }

    multiDiscipline(change: MatRadioChange): void {
        console.log(change.value);
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

    sectionTwoComplete() {
        return this.firstPresenterFirstName == "" || this.firstPresenterLastName == "" || this.firstPresenterEmail == ""
        || this.academicDiscipline == "" || this.willingToBeFeaturePresenter == "undecided";
    }

    sectionFourComplete() {
        return this.firstAdvisorFirstName == "" || this.firstAdvisorLastName == "" ||
            this.firstAdvisorEmail == "";
    }

    ngOnInit(): void {
        this.authenticationService.user$.subscribe(user => {
            this.user = user;
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
