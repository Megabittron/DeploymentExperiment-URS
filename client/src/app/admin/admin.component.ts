import {Component, OnInit} from '@angular/core';
import {SystemInformation} from "./systemInformation";
import {AdminService} from "./admin.service";
import {RandomizeReviewGroupsComponent} from "./randomize-review-groups.component";
import {MatDialog} from "@angular/material";

import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';

@Component({
    selector: 'app-admin-component',
    templateUrl: 'admin.component.html',
    styleUrls: ['./admin.component.scss'],
    providers: []
})
export class AdminComponent implements OnInit {

    //TODO: Bind the data to real reviewers
    groupAData = [
        'Hardcoded Reviewer 1',
        'Hardcoded Reviewer 2'
    ];

    groupBData = [
        'Hardcoded Reviewer 3',
        'Hardcoded Reviewer 4'
    ];

    groupCData = [
        'Hardcoded Reviewer 5',
        'Hardcoded Reviewer 6'
    ];

    drop(event: CdkDragDrop<string[]>) {
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

    getPrimarySubmissionPercent(): string {
        return (this.systemInformation.primarySubmissions/this.systemInformation.submissionStored*100).toFixed(2);
    }

    openRandomizeDialog(): void {
        console.log("openDeleteDialog");
        const dialogRef = this.dialog.open(RandomizeReviewGroupsComponent, {
            width: '500px'
        });
    }

    ngOnInit(): void {
        this.adminService.getSystemInformation().subscribe(info => {
            this.systemInformation = info;
        });
    }
}
