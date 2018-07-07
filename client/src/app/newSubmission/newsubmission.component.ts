
import {Component} from '@angular/core';
import {AppService} from "../app.service";
import {Router} from "@angular/router";
import {Observable} from 'rxjs/Observable';

@Component({
    selector: 'app-newSubmission-component',
    templateUrl: 'newSubmission.component.html',
    styleUrls: ['./newSubmission.component.scss'],
    providers: [AppService]
})
export class NewSubmissionComponent {

    constructor(private router: Router) {
    }

    public secondPresenter = false;
    public thirdPresenter = false;
    public secondAdvisor = false;
    public thirdAdvisor = false;
    public other = false;
}
