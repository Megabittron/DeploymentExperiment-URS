import {Component, OnInit} from '@angular/core';
import {AppService} from "../app.service";
import {AccountInfoService} from "../accountInfo/account-info.service";

@Component({
    selector: 'app-home-component',
    templateUrl: 'home.component.html',
    styleUrls: ['./home.component.scss'],
    providers: [AppService]
})
export class HomeComponent implements OnInit{

    public isAdmin: boolean;

    constructor(private accountInfoService: AccountInfoService) {}

    ngOnInit() {
        this.accountInfoService.isAdmin$.subscribe( value => {
            this.isAdmin = value;
        });
    }
}
