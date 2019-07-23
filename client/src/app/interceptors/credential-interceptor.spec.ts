import {CredentialInterceptor} from './credential-interceptor';
import {TestBed} from "@angular/core/testing";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {SubmissionListService} from "../submissionList/submissionList.service";
import {environment} from "../../environments/environment";

describe('CredentialInterceptor', () => {
    let service: SubmissionListService;
    let httpMock: HttpTestingController;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [
                SubmissionListService,
                {
                    provide: HTTP_INTERCEPTORS,
                    useClass: CredentialInterceptor,
                    multi: true
                }
            ]
        });

        service = TestBed.get(SubmissionListService);
        httpMock = TestBed.get(HttpTestingController);
    });

    it('should create an instance', () => {
        expect(new CredentialInterceptor()).toBeTruthy();
    });

    it('should add {withCredentials: true} to every request', () => {
        service.getSubmissions().subscribe(res => {
            expect(res).toBeTruthy();
        });

        const httpRequest = httpMock.expectOne(environment.API_URL + 'abstracts');

        expect(httpRequest.request.withCredentials).toEqual(true);
    });
});
