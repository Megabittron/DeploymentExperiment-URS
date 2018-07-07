import {Component} from '@angular/core';
import {AppService} from "../app.service";
import {Router} from "@angular/router";
import {UserService} from './home.service';
import {Observable} from 'rxjs/Observable';

@Component({
    selector: 'app-home-component',
    templateUrl: 'home.component.html',
    styleUrls: ['./home.component.scss'],
    providers: [AppService]
})
export class HomeComponent {

    constructor(public userService: UserService,
                public appService: AppService) {
    }

    newSubmission(): void{

    }
}
