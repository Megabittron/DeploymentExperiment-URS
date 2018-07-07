import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import {AuthenticationService} from "../authentication.service";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {User} from "../user";

describe('LoginComponent', () => {
    let component: LoginComponent;
    let fixture: ComponentFixture<LoginComponent>;

    let authenticationServiceStub: {
        isLoggedIn$: BehaviorSubject<boolean>;
        isLoaded$: BehaviorSubject<boolean>;
        user$: BehaviorSubject<User>;
        loadAuth2: () => void;
        renderSignIn: () => void;
    };

    beforeEach(async(() => {
        authenticationServiceStub = {
            isLoggedIn$: new BehaviorSubject<boolean>(false),
            isLoaded$: new BehaviorSubject<boolean>(false),
            user$: new BehaviorSubject<User>({
                _id: '',
                SubjectID: '',
                FirstName: '',
                LastName: '',
                ShirtSize: '',
                Role: ''
            }),
            loadAuth2: () => {},
            renderSignIn: () => {}
        };

        TestBed.configureTestingModule({
            declarations: [ LoginComponent ],
            providers: [{provide: AuthenticationService, useValue: authenticationServiceStub}]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(LoginComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
