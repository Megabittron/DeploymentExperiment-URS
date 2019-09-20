import {TestBed, ComponentFixture} from '@angular/core/testing';
import {DebugElement} from '@angular/core';
import {CustomModule} from '../custom.module';
import {AccountInfoComponent} from "./accountInfo.component";
import {AuthenticationService} from "../authentication.service";
import {BehaviorSubject, Observable} from "rxjs";
import 'rxjs/add/observable/of';
import {User} from "../user";
import {AccountInfoService} from "./account-info.service";
import {RouterTestingModule} from "@angular/router/testing";

describe('AccountInfo', () => {

    let component: AccountInfoComponent;
    let fixture: ComponentFixture<AccountInfoComponent>;
    let de: DebugElement;
    let el: HTMLElement;

    let authenticationServiceStub: {
        user$: BehaviorSubject<User>;
    };

    let accountInfoServiceStub: {
        saveShirtSize: (newUser: User) => Observable<{ ShirtSize: string }>
    };

    beforeEach(() => {

        window['gapi'] = null;

        authenticationServiceStub = {
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
            saveShirtSize: (newUser: User) => Observable.of({ShirtSize: newUser.ShirtSize})
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

    it('should initialize user on init', function () {
        expect(component.user).toBeUndefined();
        fixture.detectChanges();
        expect(component.user).toBeDefined();
        expect(component.user.FirstName).toBe("Bonita");
    });

    it('should change editing state', function () {
        expect(component.isEditing).toBe(false);
        component.changeEditState();
        expect(component.isEditing).toBe(true);
        component.changeEditState();
        expect(component.isEditing).toBe(false);
    });

    it('should save user shirt size as medium', function () {
        fixture.detectChanges();
        component.changeEditState();
        expect(component.user.ShirtSize).toBe("s");
        component.saveUserShirtSize("m");
        expect(component.user.ShirtSize).toBe("m");
        expect(component.isEditing).toBe(false);
    });


});
