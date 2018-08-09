import {TestBed, async, inject} from '@angular/core/testing';

import {RoleGuard} from './role.guard';
import {AuthenticationService} from "./authentication.service";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {RouterTestingModule} from "@angular/router/testing";

describe('RoleGuard', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule, RouterTestingModule],
            providers: [RoleGuard, AuthenticationService]
        });
    });

    it('should ...', inject([RoleGuard, AuthenticationService], (guard: RoleGuard, auth: AuthenticationService) => {
        expect(guard).toBeTruthy();
    }));
});
