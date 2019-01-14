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

    constructor(private adminService: AdminService) {

    }

    ngOnInit(): void {
        this.adminService.getSystemInformation().subscribe(info => {
            this.systemInformation = info;
        });
    }
}
