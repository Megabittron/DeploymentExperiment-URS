import {Component, OnInit} from "@angular/core";
import {SubmissionListService} from "../submissionList/submissionList.service";
import {Submission} from "../newSubmission/submission";
import {Observable} from "rxjs";

@Component({
    selector: 'submissionView.component',
    templateUrl: 'submissionView.component.html',
    styleUrls: ['./submissionView.component.scss'],
    providers: []
})

export class SubmissionViewComponent implements OnInit {

    constructor(public submissionListService: SubmissionListService) {}

    public submission: Submission;

    createSubcomment: boolean = false;
    createHightingComment: boolean = false;
    createNewComment: boolean = false;

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

    // Dasari Srinivas, Tuesday, 11 October 2016, "Get the Highlighted/Selected text using Angular 2"
    // http://blog.sodhanalibrary.com/2016/10/get-highlightedselected-text-using.html
    selectedText: string = '';
    highlightAbstractToCommentOn() {
        console.log("FIRING HIGHLIGHTING");
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

    makeSubcomment() {
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
            console.log("Creating new comment");
            this.createHightingComment = false;
            this.createSubcomment = false;
            this.selectedText = '';
        } else if (commentSection == "subComment") {
            console.log("Creating subcomment");
            this.createNewComment = false;
            this.createHightingComment = false;
            this.selectedText = '';
        } else if (commentSection == "highlightedComment") {
            console.log("Creating highlighting comment");
            this.createNewComment = false;
            this.createSubcomment = false;
        }
        console.log("createNewComment: " + this.createNewComment);
        console.log("createHighlightingComment: " + this.createHightingComment);
        console.log("createSubcomment: " + this.createSubcomment);
        console.log("--------------------------")
    }

    ngOnInit() {
        this.getSubmission();
    }

}
