import {Injectable, NgZone} from '@angular/core';
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {environment} from '../environments/environment';
import {User} from './user';

declare const gapi: any;
@Injectable()
export class AuthenticationService {

    public auth2: any;
    public user$: BehaviorSubject<User> = new BehaviorSubject<User>(null);
    public isLoggedIn$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
    public isLoaded$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

    constructor(private zone: NgZone, private http: HttpClient) { }

    validateToken(token: String): Observable<User> {
        return this.http.get<User>(environment.API_URL + 'login/' + token);
    }

    signOut(): void {
        this.auth2.signOut().then(() => {
                this.zone.run(() => {
                    this.isLoggedIn$.next(false);
                    this.user$.next(null);
                });
            },
            (err) => {
                console.log(err);
            });
    }

    loadAuth2(): void {
        gapi.load('auth2', () => {
            gapi.auth2.init({
                client_id: '450453277496-3a23399vrg1s5tp2pd93m0lani8543vu.apps.googleusercontent.com',
                fetch_basic_profile: true,
                hosted_domain: 'morris.umn.edu'
            }).then((auth) => {
                    this.zone.run(() => {
                        this.auth2 = auth;
                        this.isLoaded$.next(true);
                    });
                },
            );
        });
    }

    renderSignIn() {
        gapi.signin2.render('my-signin2', {
            'scope': 'profile email',
            'width': 240,
            'height': 50,
            'longtitle': true,
            'theme': 'light',
            'onsuccess': user => {
                this.validateToken(user.getAuthResponse().id_token).subscribe(user => {
                        this.zone.run(() => {
                            this.user$.next(user[0]);
                            this.isLoggedIn$.next(true);
                        });
                    },
                    (err) => {
                        console.log(err);
                    });
            },
            'onfailure': param => {
                console.log('FAILED TO SIGN IN!!!');
                console.log(param);
            }
        });
    }
}
