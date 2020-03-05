import {Component} from '@angular/core';
import {AppService} from "../app.service";
import {AuthenticationService} from "../authentication.service";
import {User} from "../user";

@Component({
    selector: 'app-home-component',
    templateUrl: 'home.component.html',
    styleUrls: ['./home.component.scss'],
    providers: [AppService]
})
export class HomeComponent {

    constructor(public authenticationService: AuthenticationService) {
    }

    public user: User;

    ngOnInit() {
        this.authenticationService.user$.subscribe(user => {
            this.user = user;
        });
    }
}
