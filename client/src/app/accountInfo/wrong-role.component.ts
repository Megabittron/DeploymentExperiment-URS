import {Component} from '@angular/core';
import {AppService} from "../app.service";
import {AuthenticationService} from "../authentication.service";
import {User} from "../user";

@Component({
    selector: 'wrong-role-component',
    templateUrl: 'wrong-role.component.html',
    styleUrls: ['./wrong-role.component.scss'],
    providers: [AppService]
})
export class WrongRoleComponent {

    constructor(public authenticationService: AuthenticationService) {
    }

    public user: User;

    ngOnInit() {
        this.authenticationService.user$.subscribe(user => {
            this.user = user;
        });
    }
}
