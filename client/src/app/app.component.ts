import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from './authentication.service';
import {Router, Event, NavigationEnd, NavigationError} from "@angular/router";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    title = 'Mongo-Angular-Spark lab';

    public authIsLoaded: boolean = false;
    public isLoggedIn: boolean = false;

    constructor(private authenticationService: AuthenticationService,
                private router: Router) {
        this.router.events.subscribe((event: Event) => {
            if (event instanceof NavigationEnd) {
                this.setHref(window.location.href);
            }

            if (event instanceof NavigationError) {
                // Hide loading indicator

                // Present error to user
                console.log(event.error);
            }
        });
    }

    signOut(): void {
        this.authenticationService.signOut();
    }

    setHref(href: string): any {
        document.getElementById("report-web-disability-issue").setAttribute('href', "http://oit-drupal-prd-web.oit.umn.edu/indexAccess.php?ref_url=" + href);
    }

    ngOnInit() {
        this.authenticationService.isLoaded$.subscribe( value => {
            this.authIsLoaded = value;
        });

        this.authenticationService.isLoggedIn$.subscribe( value => {
            this.isLoggedIn = value;
        });

        this.setHref(window.location.href);
    }
}
