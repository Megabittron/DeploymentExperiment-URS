import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "./authentication.service";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    title = 'Mongo-Angular-Spark lab';

    public authIsLoaded: boolean = false;
    public isLoggedIn: boolean = false;

    constructor(private authenticationService: AuthenticationService) { }

    signOut(): void {
        this.authenticationService.signOut();
    }

    ngOnInit() {
        this.authenticationService.isLoaded$.subscribe( value => {
            this.authIsLoaded = value;
        });

        this.authenticationService.isLoggedIn$.subscribe( value => {
            this.isLoggedIn = value;
        });
    }
}
