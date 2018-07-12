import {Component} from '@angular/core';
import {AppService} from "../app.service";
import {Router} from "@angular/router";
import {Observable} from 'rxjs/Observable';

@Component({
    selector: 'app-admin-component',
    templateUrl: 'admin.component.html',
    styleUrls: ['./admin.component.scss'],
    providers: [AppService]
})
export class AdminComponent {
    constructor(private router: Router) {
    }
}
