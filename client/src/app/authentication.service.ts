import {Injectable, NgZone} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from '../environments/environment';
import {User} from './user';
import {Router} from "@angular/router";

declare const gapi: any;

@Injectable()
export class AuthenticationService {

    public auth2: any;
    public user$: BehaviorSubject<User> = new BehaviorSubject<User>(null);
    public isLoggedIn$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
    public isLoaded$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
    public redirectUrl: string;

    constructor(private zone: NgZone, private http: HttpClient, private router: Router) {
        this.user$.subscribe((user) => {
            if (user) {
                this.isLoggedIn$.next(true);

                if (this.redirectUrl) {
                    this.router.navigate([this.redirectUrl]);
                }
            } else {
                this.isLoggedIn$.next(false);
            }
        })

    }

    validateToken(id_token: String): Observable<User> {
        return this.http.post<User>(environment.API_URL + 'login', {id_token: id_token});
    }

    signOut(): void {
        gapi.auth2.getAuthInstance().signOut().then(() => {
                this.zone.run(() => {
                    this.isLoggedIn$.next(false);
                    this.user$.next(null);
                });

                this.http.post(environment.API_URL + 'logout', {isSignedIn: false}, {responseType: 'text'}).subscribe();
            },
            (err) => {
                console.log(err);
            });
    }

    loadAuth2(): void {
            this.zone.run(() => {
                gapi.load('auth2', {
                    callback: () => {
                        this.isLoaded$.next(true);
                        this.initAuth2();
                    },
                    onerror: () => {
                        this.isLoaded$.next(false);
                        console.error('Google library failed to load.');
                    },
                    timeout: 5000,
                    ontimeout: () => {
                        console.error('Google library loading has timed out.');
                    }
                });
            });
    }

    initAuth2(): void {
            this.zone.run(() => {
                gapi.auth2.init({
                    client_id: '360518813721-mppgbakr2g1pk5q843nm533uvdhp1lk6.apps.googleusercontent.com',
                    fetch_basic_profile: true,
                    hosted_domain: 'morris.umn.edu'
                }).then(GoogleAuth => {
                    this.auth2 = GoogleAuth;
                    let GoogleUser = GoogleAuth.currentUser.get();
                    let isSignedIn = GoogleAuth.isSignedIn.get();

                    if (isSignedIn) {
                        this.updateCurrentUser(GoogleUser);
                    }

                    GoogleAuth.isSignedIn.listen(this.updateSignInStatus);
                    GoogleAuth.currentUser.listen(this.updateCurrentUser);

                }, (err) => {
                    console.error(err);
                });
            });
    }

    updateSignInStatus = (status) => {
        this.isLoggedIn$.next(status);
    };

    updateCurrentUser = (GoogleUser) => {
        let id_token = GoogleUser.getAuthResponse().id_token;

        if (id_token) {
            this.validateToken(id_token).subscribe(user => {
                    this.zone.run(() => {
                        this.user$.next(user);
                    });
                },
                (err) => {
                    console.error(err);
                });
        }
    };

    renderSignIn(): void {
            this.zone.run(() => {
                gapi.signin2.render('my-signin2', {
                    scope: 'profile email',
                    width: 240,
                    height: 50,
                    longtitle: true,
                    theme: 'light',
                    prompt: 'select_account',
                    onsuccess: user => {
                        this.updateCurrentUser(user);
                    },
                    onfailure: (err) => {
                        console.error(err);
                    }
                });
            });
    }

}
