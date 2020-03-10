import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {AdminComponent, SaveReviewGroupsDialog} from './admin/admin.component';
import {SubmissionListComponent} from './submissionList/submissionList.component';
import {SubmissionListService} from './submissionList/submissionList.service';
import {AccountInfoComponent} from './accountInfo/accountInfo.component';
import {NewSubmissionComponent} from './newSubmission/newSubmission.component';
import {NewSubmissionService} from './newSubmission/newSubmission.service';
import {NavComponent} from './nav/nav.component';
import {Routing} from './app.routes';
import {APP_BASE_HREF} from '@angular/common';
import {CustomModule} from './custom.module';
import { LoginComponent } from './login/login.component';
import { AuthenticationService } from './authentication.service';
import { UserIsLoggedInGuard } from './user-is-logged-in.guard';
import { RoleGuard } from './role.guard';
import {AccountInfoService} from "./accountInfo/account-info.service";
import {AdminService} from "./admin/admin.service";
import {CredentialInterceptor} from "./interceptors/credential-interceptor";
import {RandomizeReviewGroupsComponent} from "./admin/randomize-review-groups.component";
import {SubmissionViewComponent} from "./submissionView/submissionView.component";
import {EditUserInfoComponent} from "./admin/editUserInfo.component";

import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {WrongRoleComponent} from "./accountInfo/wrong-role.component";
import {MyFormSubComponent} from "./newSubmission/my-form-sub.component";
import {MyFormComponent} from "./newSubmission/my-form-component";

@NgModule({
    imports: [
        BrowserModule,
        HttpClientModule,
        Routing,
        CustomModule,
        FormsModule,
        ReactiveFormsModule,
        BrowserModule
    ],
    declarations: [
        AppComponent,
        HomeComponent,
        AdminComponent,
        AccountInfoComponent,
        SubmissionListComponent,
        NewSubmissionComponent,
        NavComponent,
        LoginComponent,
        RandomizeReviewGroupsComponent,
        SaveReviewGroupsDialog,
        LoginComponent,
        SubmissionViewComponent,
        EditUserInfoComponent,
        WrongRoleComponent,

        MyFormSubComponent, //tut
        MyFormComponent     //tut
    ],
    providers: [
        AuthenticationService,
        AccountInfoService,
        SubmissionListService,
        NewSubmissionService,
        UserIsLoggedInGuard,
        RoleGuard,
        AdminService,
        SubmissionListService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: CredentialInterceptor,
            multi: true
        },
        {
            provide: APP_BASE_HREF,
            useValue: '/'
        }
    ],
    entryComponents: [RandomizeReviewGroupsComponent, SaveReviewGroupsDialog],
    bootstrap: [AppComponent]
})

export class AppModule {
}
