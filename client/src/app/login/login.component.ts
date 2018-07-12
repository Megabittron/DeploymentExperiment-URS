import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../authentication.service";
import {User} from "../user";

declare const gapi: any;
@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnInit {

    public authIsLoaded: boolean = false;
    public isLoggedIn: boolean = false;
    public user: User;

    constructor(private authenticationService: AuthenticationService) { }

    signOut(): void {
        this.authenticationService.signOut();
    }

    ngOnInit() {
        this.authenticationService.loadAuth2();

        this.authenticationService.renderSignIn();

        this.authenticationService.isLoaded$.subscribe( value => {
            this.authIsLoaded = value;
        });

        this.authenticationService.isLoggedIn$.subscribe( value => {
            this.isLoggedIn = value;
        });

        this.authenticationService.user$.subscribe( value => {
            this.user = value;
        });


    }
}
