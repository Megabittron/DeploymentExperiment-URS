import {TestBed, ComponentFixture} from '@angular/core/testing';
import {DebugElement} from '@angular/core';
import {CustomModule} from '../custom.module';
import {AccountInfoComponent} from "./accountInfo.component";
import {AuthenticationService} from "../authentication.service";
import {BehaviorSubject, Observable} from "rxjs";
import {User} from "../user";
import {AccountInfoService} from "./account-info.service";
import {RouterTestingModule} from "@angular/router/testing";

describe('AccountInfo', () => {

    let component: AccountInfoComponent;
    let fixture: ComponentFixture<AccountInfoComponent>;
    let de: DebugElement;
    let el: HTMLElement;

    let authenticationServiceStub: {
        isLoggedIn$: BehaviorSubject<boolean>;
        isLoaded$: BehaviorSubject<boolean>;
        user$: BehaviorSubject<User>;
    };

    let accountInfoServiceStub: {
        saveShirtSize: (shirtSize: string, subjectId: string) => Observable<{ShirtSize: string}>
    };

    beforeEach(() => {

        authenticationServiceStub = {
            isLoggedIn$: new BehaviorSubject(true),
            isLoaded$: new BehaviorSubject(true),
            user$: new BehaviorSubject({
                _id: {
                    $oid: "5b3524ba715fad363abdc3cc"
                },
                SubjectID: "99",
                FirstName: "Bonita",
                LastName: "Houston",
                ShirtSize: "s",
                Role: "user"
            })
        };

        accountInfoServiceStub = {
            saveShirtSize: (shirtSize, subjectId) => Observable.of({ShirtSize: shirtSize})
        };


        TestBed.configureTestingModule({
            imports: [CustomModule, RouterTestingModule],
            declarations: [AccountInfoComponent],
            providers: [{provide: AuthenticationService, useValue: authenticationServiceStub},
                        {provide: AccountInfoService, useValue: accountInfoServiceStub}]
        });

        fixture = TestBed.createComponent(AccountInfoComponent);
        component = fixture.componentInstance;
        de = fixture.debugElement;
        el = de.nativeElement;
    });

    it('should create AccountInfoComponent', function () {
        expect(component).toBeDefined();
    });

});
