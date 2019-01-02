import {Component, OnInit} from '@angular/core';
import {AppService} from "../app.service";
import {AuthenticationService} from "../authentication.service";
import {User} from "../user";
import {AccountInfoService} from "./account-info.service";

declare const gapi: any;
@Component({
    selector: 'app-accountInfo-component',
    templateUrl: 'accountInfo.component.html',
    styleUrls: ['./accountInfo.component.scss'],
    providers: [AppService]
})
export class AccountInfoComponent implements OnInit {

    public user: User;
    public profilePic: string;
    public isEditing: boolean = false;

    shirtSizes = [
        {value: 'xs', viewValue: 'Extra Small'},
        {value: 's', viewValue: 'Small'},
        {value: 'm', viewValue: 'Medium'},
        {value: 'l', viewValue: 'Large'},
        {value: 'xl', viewValue: 'Extra Large'}
    ];

    constructor(private authenticationService: AuthenticationService,
                private accountInfoService: AccountInfoService) { }

    changeEditState(): void {
        this.isEditing = !this.isEditing;
    }

    saveUser(shirtSize: string): void {
        this.user.ShirtSize = shirtSize;
        this.accountInfoService.editShirtSize(this.user);
        this.changeEditState();
    }

    getName(): string {
        return this.user.FirstName + ' ' + this.user.LastName;
    }

    getRole(): string {
        return this.user.Role.toUpperCase();
    }

    getShirtSize(): string {
        return this.user.ShirtSize.toUpperCase();
    }

    ngOnInit(): void {
       this.authenticationService.user$.subscribe(user => {
            this.user = user;

           if (gapi) {
               this.profilePic = gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile().getImageUrl();
           }
        });
    }
}
