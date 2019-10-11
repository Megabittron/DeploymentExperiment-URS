import {TestBed} from '@angular/core/testing';

import {AccountInfoService} from './account-info.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {Observable} from "rxjs";
import {AuthenticationService} from "../authentication.service";

describe('AccountInfoService', () => {

    let httpClientSpy: { put: jasmine.Spy };
    let authServiceSpy: { put: jasmine.Spy };
    let service: AccountInfoService;

    const user = {
        _id: {
            $oid: "5b3524ba715fad363abdc3cc"
        },
        SubjectID: "99",
        FirstName: "Bonita",
        LastName: "Houston",
        ShirtSize: "s",
        Role: "user"
    };

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [AccountInfoService]
        });

        httpClientSpy = jasmine.createSpyObj('HttpClient', ['put']);
        authServiceSpy = jasmine.createSpyObj('AuthenticationService', ['put']);
        service = new AccountInfoService(<any>httpClientSpy, <any> authServiceSpy);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    it('should return users new ShirtSize', () => {
        httpClientSpy.put.and.returnValue(Observable.of({ShirtSize: user.ShirtSize}));

        service.saveShirtSize(user).subscribe(shirtSize => {
            expect(shirtSize).toEqual({ShirtSize: "s"});
        });

        expect(httpClientSpy.put.calls.count()).toBe(1);
    });
});
