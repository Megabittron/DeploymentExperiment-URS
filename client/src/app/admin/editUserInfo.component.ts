import {Component, OnInit} from '@angular/core';
import {AdminService} from "./admin.service";
import {User} from "../user";
import {SystemInformation} from "./systemInformation";

@Component({
    selector: 'edit-user-info-component',
    templateUrl: 'editUserInfo.component.html',
    styleUrls: ['./editUserInfo.component.scss'],
    providers: []
})
export class EditUserInfoComponent implements OnInit {

    constructor(private adminService: AdminService) {}

    public users: User[];
    public systemInformation: SystemInformation;

    ngOnInit(): void {
        this.adminService.getSystemInformation().subscribe(info => {
            this.systemInformation = info;
        });

        this.adminService.getUserInfo().subscribe(value => {
            this.users = value;
            this.users.sort((a,b) => a.Role.localeCompare(b.Role));
        });
    }
}


