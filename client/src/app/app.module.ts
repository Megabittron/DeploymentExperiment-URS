import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { MATERIAL_COMPATIBILITY_MODE } from '@angular/material';


import {HttpClientModule} from '@angular/common/http';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {AdminComponent} from './admin/admin.component';
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

@NgModule({
    imports: [
        BrowserModule,
        HttpClientModule,
        Routing,
        CustomModule,
    ],
    declarations: [
        AppComponent,
        HomeComponent,
        AdminComponent,
        AccountInfoComponent,
        SubmissionListComponent,
        NewSubmissionComponent,
        NavComponent,
        LoginComponent
    ],
    providers: [
        AuthenticationService,
        AccountInfoService,
        SubmissionListService,
        NewSubmissionService,
        UserIsLoggedInGuard,
        RoleGuard,
        AdminService,
        {provide: APP_BASE_HREF, useValue: '/'},
        {provide: MATERIAL_COMPATIBILITY_MODE, useValue: true}
    ],
    entryComponents: [],
    bootstrap: [AppComponent]
})

export class AppModule {
}
