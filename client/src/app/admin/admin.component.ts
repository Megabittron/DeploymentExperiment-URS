import {Component, OnInit} from '@angular/core';
import {SystemInformation} from "./systemInformation";
import {AdminService} from "./admin.service";
import {MatDialog} from "@angular/material";

import {CdkDragDrop, CdkDragEnter, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import {User} from "../user";
import {ReviewGroup} from "./reviewGroup";
import {Observable} from "rxjs";
import {AuthenticationService} from "../authentication.service";

@Component({
    selector: 'app-admin-component',
    templateUrl: 'admin.component.html',
    styleUrls: ['./admin.component.scss'],
    providers: []
})
export class AdminComponent implements OnInit {

    connectedTo = []; //https://www.freakyjolly.com/angular-7-drag-and-drop-across-multi-lists-in-angular-material-7/

    drop(event: CdkDragDrop<Array<User>, any>) {
        if (event.previousContainer === event.container) {
            moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
        } else {
            transferArrayItem(event.previousContainer.data,
                event.container.data,
                event.previousIndex,
                event.currentIndex);
        }
    }

    public systemInformation: SystemInformation;
    public reviewGroups: ReviewGroup[];
    public users: User[];

    public user: User;

    constructor(private adminService: AdminService, public dialog: MatDialog,
                private authenticationService: AuthenticationService) {}

    isAdmin(): boolean {
        if (this.user) {
            return this.user.Role.includes('admin');
        }
    }

    openDialog(): void {
        const dialogRef = this.dialog.open(SaveReviewGroupsDialog, {
            // width: '250px'
        });

        dialogRef.afterClosed().subscribe(result => {
            if(result) {
                this.changeReviewGroups();
                // console.log('Review groups SAVED');
            } else {
                // console.log('Review groups NOT saved');
            }
        });
    }

    changeReviewGroups(): void {
        this.adminService.updateReviewGroups(this.systemInformation.reviewGroups).subscribe();
    }

    getPrimarySubmissionPercent(): string {

        for (let i of this.systemInformation.reviewGroups) {
            this.connectedTo.push(i.name); //https://www.freakyjolly.com/angular-7-drag-and-drop-across-multi-lists-in-angular-material-7/
        }

        return (this.systemInformation.primarySubmissions
            /this.systemInformation.submissionStored*100).toFixed(2);
    }

    refreshReviewGroups(): Observable<ReviewGroup[]> {
        const reviewGroups: Observable<ReviewGroup[]> = this.adminService.getReviewGroups();
        reviewGroups.subscribe(
            reviewGroups => {
                this.reviewGroups = reviewGroups;
            },
            err => {
                console.log(err);
            });
        return reviewGroups;
    }

    ngOnInit(): void {

        this.authenticationService.user$.subscribe(value => {
            this.user = value;
        });

        this.refreshReviewGroups();

        this.adminService.getSystemInformation().subscribe(info => {
            this.systemInformation = info;
        });
    }
}

@Component({
    selector: 'save-review-group-dialog',
    templateUrl: 'save-review-group-dialog.html',
})
export class SaveReviewGroupsDialog {

}
