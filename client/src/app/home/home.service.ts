import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';

import {environment} from '../../environments/environment';


@Injectable()
export class UserService {
    readonly baseUrl: string = environment.API_URL + '';
    private userUrl: string = this.baseUrl;

    constructor(private http: HttpClient) {
    }
}
