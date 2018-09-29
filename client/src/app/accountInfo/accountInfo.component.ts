import {Component, OnInit} from '@angular/core';
import {AppService} from "../app.service";
import {AuthenticationService} from "../authentication.service";
import {User} from "../user";

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

    constructor(private authenticationService: AuthenticationService) { }

    getName(): String {
        return this.user.FirstName + ' ' + this.user.LastName;
    }

    getRole(): String {
        return this.user.Role;
    }

    getShirtSize(): String {
        return this.user.ShirtSize;
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
