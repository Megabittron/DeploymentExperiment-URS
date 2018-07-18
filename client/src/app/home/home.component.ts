import {Component} from '@angular/core';
import {AppService} from "../app.service";
import {Router} from "@angular/router";
import {Observable} from 'rxjs/Observable';

@Component({
    selector: 'app-home-component',
    templateUrl: 'home.component.html',
    styleUrls: ['./home.component.scss'],
    providers: [AppService]
})
export class HomeComponent {

    constructor(private router: Router) {
    }

    newSubmission(): void{

    }
}
