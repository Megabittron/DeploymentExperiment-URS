import {Component, OnInit} from '@angular/core';
import {AppService} from "../app.service";
import {AuthenticationService} from "../authentication.service";
import {User} from "../user";
import {AccountInfoService} from "./account-info.service";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {BreakpointObserver, BreakpointState} from "@angular/cdk/layout";

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
    public breakPointState: Observable<BreakpointState> = this.breakPointObserver.observe('(max-width: 960px)');
    public isMobile: boolean;

    shirtSizes = [
        {value: 'xs', viewValue: 'Extra Small'},
        {value: 's', viewValue: 'Small'},
        {value: 'm', viewValue: 'Medium'},
        {value: 'l', viewValue: 'Large'},
        {value: 'xl', viewValue: 'Extra Large'},
        {value: 'xxl', viewValue: 'Double Extra Large'}
    ];

    constructor(private authenticationService: AuthenticationService,
                private accountInfoService: AccountInfoService,
                private router: Router,
                private breakPointObserver: BreakpointObserver) { }

    changeEditState(): void {
        this.isEditing = !this.isEditing;
    }

    saveUserShirtSize(shirtSize: string): void {
        this.user.ShirtSize = shirtSize;
        this.accountInfoService.saveShirtSize(this.user).subscribe();
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

        this.breakPointState.subscribe(result => {
            this.isMobile = result.matches;
        });
    }
}
