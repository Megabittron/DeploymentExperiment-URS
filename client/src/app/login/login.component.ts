import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../authentication.service";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnInit {

    constructor(private authenticationService: AuthenticationService) { }

    ngOnInit() {
        this.authenticationService.loadAuth2();
        this.authenticationService.renderSignIn();
    }
}
