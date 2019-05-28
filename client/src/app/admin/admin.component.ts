import {Component, OnInit} from '@angular/core';
import {SystemInformation} from "./systemInformation";
import {AdminService} from "./admin.service";
import {RandomizeReviewGroupsComponent} from "./randomize-review-groups.component";
import {MatDialog} from "@angular/material";

@Component({
    selector: 'app-admin-component',
    templateUrl: 'admin.component.html',
    styleUrls: ['./admin.component.scss'],
    providers: []
})
export class AdminComponent implements OnInit {

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
