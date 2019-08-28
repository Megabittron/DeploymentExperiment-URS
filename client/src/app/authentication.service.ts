import {Injectable, NgZone, OnInit} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from '../environments/environment';
import {User} from './user';
import {Router} from "@angular/router";

declare const gapi: any;
@Injectable()
export class AuthenticationService implements OnInit{

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

    loadAuth2(): Promise<any> {
        return new Promise((resolve, reject) => {
            this.zone.run(() => {
                gapi.load('auth2', {
                    callback: resolve,
                    onerror: reject,
                    timeout: 1000,
                    ontimeout: reject
                });
            });
        });
    }

    initAuth2(): Promise<any> {
        return new Promise((resolve, reject) => {
            this.zone.run(() => {
                gapi.auth2.init({
                    client_id: '360518813721-mppgbakr2g1pk5q843nm533uvdhp1lk6.apps.googleusercontent.com',
                    fetch_basic_profile: true,
                    hosted_domain: 'morris.umn.edu'
                }).then(googleAuth => {
                    console.log(googleAuth);
                    this.auth2 = googleAuth;
                    resolve();
                }, () => {
                    reject();
                });
            });
        });
    }

    renderSignIn(): Promise<any> {
        return new Promise((resolve, reject) => {
            this.zone.run(() => {
                    gapi.signin2.render('my-signin2', {
                        'scope': 'profile email',
                        'width': 240,
                        'height': 50,
                        'longtitle': true,
                        'theme': 'light',
                        'onsuccess': user => {
                            this.validateToken(user.getAuthResponse().id_token).subscribe(user => {
                                    this.zone.run(() => {
                                        this.user$.next(user);
                                        this.isLoggedIn$.next(true);
                                        resolve();
                                    });
                                },
                                (err) => {
                                    console.log(err);
                                });
                        },
                        'onfailure': () => {
                            console.log('FAILED TO SIGN IN!!!');
                            reject();
                        }
                    });
            })
        })
    }

    ngOnInit(): void {
        this.loadAuth2().then(() => {
            return this.initAuth2();
        },
            () => {}).then(() => {
                this.isLoaded$.next(true);
        },
            () => {
                this.isLoaded$.next(false);
            });
    }
}
