import {Injectable} from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthenticationService} from "./authentication.service";

@Injectable()
export class UserIsLoggedInGuard implements CanActivate {

    constructor(private authService: AuthenticationService) {
    }

    canActivate(next: ActivatedRouteSnapshot,
                state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {

        if (this.authService.isLoggedIn$.getValue()) {
            return true;
        }

        this.authService.redirectUrl = state.url;

        return false;
    }
}
