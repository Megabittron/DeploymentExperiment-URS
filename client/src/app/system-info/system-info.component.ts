import { Component, OnInit } from '@angular/core';
import {AdminService} from "../admin/admin.service";
import {SystemInformation} from "../admin/systemInformation";

@Component({
  selector: 'app-system-info',
  templateUrl: './system-info.component.html',
  styleUrls: ['./system-info.component.scss']
})
export class SystemInfoComponent implements OnInit {

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
