import {Component, Inject, OnInit} from "@angular/core";
import {SubmissionListService} from "../submissionList/submissionList.service";
import {Submission} from "../newSubmission/submission";
import {Observable} from "rxjs";
import {TopComment} from "./comment";
import {AuthenticationService} from "../authentication.service";
import {User} from "../user";
import {Presenters} from "../newSubmission/presenters";
import {DOCUMENT} from "@angular/common";
import * as url from "url";

@Component({
    selector: 'submissionView.component',
    templateUrl: 'submissionView.component.html',
    styleUrls: ['./submissionView.component.scss'],
    providers: []
})

export class SubmissionViewComponent implements OnInit {

    constructor(public submissionListService: SubmissionListService,
                public authenticationService: AuthenticationService,
                @Inject(DOCUMENT) private document: Document) {
    }

    public abstractID = "";

    public submission: Submission;
    public otherDiscipline: string;
    public otherOrg: string;
    public user: User;

    createSubcomment: boolean = false;
    createHightingComment: boolean = false;
    createNewComment: boolean = false;

    // topLevelComments: TopComment[];
    // subComments: SubComment[];

    public presenterArray: Presenters[] = [];

    getSubmission() {
        let url = this.document.location.href;
        this.abstractID = url.substr(url.lastIndexOf('/') + 1); //grabs the abstract id from after the url's last slash
        let submissionObservable: Observable<Submission>;
        if (this.submissionListService.singleAbstractId == "") {
            submissionObservable = this.submissionListService.getSingleSubmissionById(this.abstractID);
        } else if (this.submissionListService.singleAbstractId != "") {
            submissionObservable = this.submissionListService.getSingleSubmissionById(this.submissionListService.singleAbstractId);
        }
        submissionObservable.subscribe(
            submission => {
                if (submission != null) {
                    this.submission = submission;
                    this.otherDiscipline = this.submission.academicDiscipline.slice(-1).toString();
                    this.otherOrg = this.submission.sponOrganization.slice(-1).toString();
                    // Was having a hard time accessing each presenter. Various guides that used indexes or `.at()`
                    // or `.control` accessing things didn't work for one reason or another, but it just works to
                    // effectively push all the stuff into presenter objects one by one.
                    for(let i = 0; i < this.submission.presenters.length; i++) {
                        this.presenterArray.push(this.submission.presenters[i]);
                    }
                }
            }
        );

        // this.topLevelComments = this.submission.topComments;
    }

    // Dasari Srinivas, Tuesday, 11 October 2016, "Get the Highlighted/Selected text using Angular 2"
    // http://blog.sodhanalibrary.com/2016/10/get-highlightedselected-text-using.html
    selectedText: string = '';
    highlightAbstractToCommentOn() {
        this.createHightingComment = true;
        this.hideOtherCommentSectionsExceptFor("highlightedComment");
        let text = "";
        if (window.getSelection) {
            text = window.getSelection().toString();
        }
        this.selectedText = text;
    }

    makeNewComment() {
        this.createNewComment = true;
        this.hideOtherCommentSectionsExceptFor("newComment");
    }

    public matchingTLC_id: string;

    makeSubcomment(toplevelcomment: TopComment) {
        this.matchingTLC_id = toplevelcomment._id.$oid;
        this.createSubcomment = true;
        this.hideOtherCommentSectionsExceptFor("subComment");
    }

    subComment: string = "";
    saveSubcomment() {
        this.createSubcomment = false;
        this.selectedText = '';
    }

    hideOtherCommentSectionsExceptFor(commentSection: string) {
        if (commentSection == "newComment") {
            this.createHightingComment = false;
            this.createSubcomment = false;
            this.selectedText = '';
        } else if (commentSection == "subComment") {
            this.createNewComment = false;
            this.createHightingComment = false;
            this.selectedText = '';
        } else if (commentSection == "highlightedComment") {
            this.createNewComment = false;
            this.createSubcomment = false;
        }
    }

    ngOnInit() {
        this.authenticationService.user$.subscribe(user => {
            this.user = user;
        });

        this.getSubmission();
    }

}
