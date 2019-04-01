import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../authentication.service";
import {User} from "../user";
import {Router} from "@angular/router";

declare const gapi: any;
@Component({
    selector: 'app-nav',
    templateUrl: 'nav.component.html',
    styleUrls: ['nav.component.scss'],
})
export class NavComponent implements OnInit{
    public text: string;

    public authIsLoaded: boolean = false;
    public isLoggedIn: boolean = false;
    public user: User;
    public profilePic: string = null;

    constructor(private authenticationService: AuthenticationService,
                private router: Router) {
        this.text = 'Nav';

    }

    isAdmin(): boolean {
        if (this.user) {
            return this.user.Role.includes('admin');
        }
    }

    goToAccountInfo(): void {
        this.router.navigate(['accountInfo']);
    }

    signOut(): void {
        this.authenticationService.signOut();
        this.router.navigate(['/']);
    }

    ngOnInit() {
        this.authenticationService.isLoaded$.subscribe( value => {
            this.authIsLoaded = value;
        });

        this.authenticationService.isLoggedIn$.subscribe( value => {
            this.isLoggedIn = value;
        });

        this.authenticationService.user$.subscribe(value => {
            this.user = value;

            if (gapi) {
                this.profilePic = gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile().getImageUrl();
            }

        })
    }

}
