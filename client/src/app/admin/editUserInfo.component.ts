import {Component, OnInit} from '@angular/core';
import {AdminService} from "./admin.service";
import {User} from "../user";
import {SystemInformation} from "./systemInformation";
import {AuthenticationService} from "../authentication.service";

@Component({
    selector: 'edit-user-info-component',
    templateUrl: 'editUserInfo.component.html',
    styleUrls: ['./editUserInfo.component.scss'],
    providers: []
})

export class EditUserInfoComponent implements OnInit {

    constructor(private adminService: AdminService, private authenticationService: AuthenticationService) {}

    public users: User[];
    public user: User;
    public systemInformation: SystemInformation;
    private editingRole: boolean;
    private currentUser: User;

    roles = [
        {value: 'user', viewValue: 'User'},
        {value: 'chair', viewValue: 'Chair'},
        {value: 'reviewer', viewValue: 'Reviewer'},
        {value: 'admin', viewValue: 'Admin'}
    ];

    public role: string;

    changeUserRole(use: User, role: string): void {
        this.adminService.grabUserObj(use);
        this.user = this.adminService.userObj;
        this.user.Role = role;
        this.editingRole = true;
    }

    saveUserRole() {
        this.editingRole = false;
        this.adminService.updateUserRole(this.user).subscribe();
    }

    ngOnInit(): void {
        this.adminService.getSystemInformation().subscribe(info => {
            this.systemInformation = info;
        });

        this.adminService.getUserInfo().subscribe(value => {
            this.users = value;
            this.users.sort((a,b) => a.Role.localeCompare(b.Role));
        });

        this.authenticationService.user$.subscribe(currentUser => {
            this.currentUser = currentUser;
            // console.log(this.currentUser);
        });
    }
}


