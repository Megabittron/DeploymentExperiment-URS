import {TestBed, ComponentFixture, async} from '@angular/core/testing';
import {DebugElement} from '@angular/core';
import {CustomModule} from '../custom.module';
import {AccountInfoComponent} from "./accountInfo.component";
import {BehaviorSubject} from "rxjs/Rx";
import {User} from "../user";
import {AuthenticationService} from "../authentication.service";
import {AccountInfoService} from "./account-info.service";

describe('AccountInfo', () => {

    let component: AccountInfoComponent;
    let fixture: ComponentFixture<AccountInfoComponent>;

    let acctServiceStub;
    let authServiceStub: {
        user$: BehaviorSubject<User>;
    };

    const user: User = {
        _id: "string",
        SubjectID: "string",
        FirstName: "string",
        LastName: "string",
        ShirtSize: "string",
        Role: "string"
    };

    beforeEach(() => {

        authServiceStub = {
            user$: BehaviorSubject<user> = {
                _id: "string",
                SubjectID: "string",
                FirstName: "string",
                LastName: "string",
                ShirtSize: "string",
                Role: "string"
            }
        };

        TestBed.configureTestingModule({
            imports: [CustomModule],
            declarations: [AccountInfoComponent], // declare the test component
            // providers: []
            providers: [
                {provide: AccountInfoService, useValue: acctServiceStub},
                {provide: AuthenticationService, useValue: authServiceStub}]
        });

        fixture = TestBed.createComponent(AccountInfoComponent);

        component = fixture.componentInstance; // BannerComponent test instance
    });

    beforeEach(async(() => {
        TestBed.compileComponents().then(() => {
            fixture = TestBed.createComponent(AccountInfoComponent);
            component = fixture.componentInstance;
            fixture.detectChanges();

            component.user.ShirtSize = 'XL';
        });
    }));

    it('getName() gets the name of the user', () => {
        component.getShirtSize();
    });

});
