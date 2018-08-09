import {TestBed, async, inject} from '@angular/core/testing';
import {UserIsLoggedInGuard} from './user-is-logged-in.guard';
import {AuthenticationService} from "./authentication.service";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {RouterTestingModule} from "@angular/router/testing";

describe('UserIsLoggedInGuard', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule, RouterTestingModule],
            providers: [UserIsLoggedInGuard, AuthenticationService]
        });
    });

    it('should ...', inject([UserIsLoggedInGuard, AuthenticationService], (guard: UserIsLoggedInGuard, auth: AuthenticationService) => {
        expect(guard).toBeTruthy();
    }));
});
