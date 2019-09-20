import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";

/**
 * Add credentials to every request. Server sessions don't function without this
 * as the JSESSIONID cookie won't be sent.
 */
@Injectable()
export class CredentialInterceptor implements HttpInterceptor {

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        req = req.clone({
            withCredentials: true
        });

        return next.handle(req);
    }
}
