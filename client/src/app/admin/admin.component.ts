import {Component, OnInit} from '@angular/core';
import {SystemInformation} from "./systemInformation";
import {AdminService} from "./admin.service";

@Component({
    selector: 'app-admin-component',
    templateUrl: 'admin.component.html',
    styleUrls: ['./admin.component.scss'],
    providers: []
})
export class AdminComponent implements OnInit {

    public systemInformation: SystemInformation;

    constructor(private adminService: AdminService) {}

    getPrimarySubmissionPercent(): string {
        return (this.systemInformation.primarySubmissions/this.systemInformation.submissionStored*100).toFixed(2);
    }

    ngOnInit(): void {
        this.adminService.getSystemInformation().subscribe(info => {
            this.systemInformation = info;
        });
    }
}
