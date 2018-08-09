import {Injectable} from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {AuthenticationService} from "./authentication.service";

@Injectable()
export class RoleGuard implements CanActivate {

    constructor(private authService: AuthenticationService) { }

    canActivate(next: ActivatedRouteSnapshot,
                state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {

        if (this.authService.user$.getValue() != null) {
            return this.authService.user$.getValue().Role.includes('admin');
        }

        return false;
    }

}
