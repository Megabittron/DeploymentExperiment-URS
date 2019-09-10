import {Component} from '@angular/core';
import {AppService} from "../app.service";

@Component({
    selector: 'app-home-component',
    templateUrl: 'home.component.html',
    styleUrls: ['./home.component.scss'],
    providers: [AppService]
})
export class HomeComponent {

    constructor() {

    }
}
