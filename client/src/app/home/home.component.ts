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
    date = new Date();
    // date = new Date('04/26/2020 00:00'); //debug value

    //TODO: This is going to be especially dated after this season.
    // The names are super verbose but I want it to be especially obvious that this is dated for 2020
    weekOutFromAbstractsDue = new Date('03/20/2020 12:00');
    abstractsDueMarch28th2020atNoon = new Date('03/27/2020 12:00');
    reviewerFeedbackDueApril10th2020atTBD = new Date('04/10/2020 12:00');
    finalStudentRevisionsDueApril17th2020atTBD = new Date('04/17/2020 12:00');
    ursOnApril25th2020atTBD = new Date('04/25/2020 12:00');

    ngOnInit() {
        this.authenticationService.user$.subscribe(user => {
            this.user = user;
        });
        console.log(this.date);
    }
}
