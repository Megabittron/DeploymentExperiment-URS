import {Component, OnInit} from '@angular/core';
import {SystemInformation} from "./systemInformation";
import {AdminService} from "./admin.service";
import {RandomizeReviewGroupsComponent} from "./randomize-review-groups.component";
import {MatDialog} from "@angular/material";

import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import {User} from "../user";

@Component({
    selector: 'app-admin-component',
    templateUrl: 'admin.component.html',
    styleUrls: ['./admin.component.scss'],
    providers: []
})
export class AdminComponent implements OnInit {

    connectedTo = []; //https://www.freakyjolly.com/angular-7-drag-and-drop-across-multi-lists-in-angular-material-7/

    //
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

    constructor(private adminService: AdminService, public dialog: MatDialog) {}

    openDialog(): void {
        const dialogRef = this.dialog.open(SaveReviewGroupsDialog, {
            // width: '250px'
        });

        dialogRef.afterClosed().subscribe(result => {
            if(result) {
                console.log('Review groups SAVED');
            } else {
                console.log('Review groups NOT saved');
            }
        });
    }

    getPrimarySubmissionPercent(): string {

        for (let i of this.systemInformation.reviewGroups) {
            this.connectedTo.push(i.name); //https://www.freakyjolly.com/angular-7-drag-and-drop-across-multi-lists-in-angular-material-7/
        }

        return (this.systemInformation.primarySubmissions
            /this.systemInformation.submissionStored*100).toFixed(2);
    }

    // openRandomizeDialog(): void {
    //     console.log("openDeleteDialog");
    //     const dialogRef = this.dialog.open(RandomizeReviewGroupsComponent, {
    //         width: '500px'
    //     });
    // }

    ngOnInit(): void {
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
