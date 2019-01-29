import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {SystemInformation} from "./systemInformation";

@Injectable()
export class AdminService {
    readonly baseUrl: string = environment.API_URL + 'system-information';
    private systemInformationUrl: string = this.baseUrl;

    constructor(private http: HttpClient) {
    }

    getSystemInformation(): Observable<SystemInformation> {
        this.systemInformationUrl = this.baseUrl;

        return this.http.get<SystemInformation>(this.systemInformationUrl);
    }

}
