import {Component} from '@angular/core';
import {AppService} from "../app.service";
import {Router} from "@angular/router";
import {Observable} from 'rxjs/Observable';

@Component({
    selector: 'app-submissionList-component',
    templateUrl: 'submissionList.component.html',
    styleUrls: ['./submissionList.component.scss'],
    providers: [AppService]
})
export class SubmissionListComponent {

    constructor() {
    }
}
