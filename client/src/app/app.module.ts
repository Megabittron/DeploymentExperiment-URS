import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { MATERIAL_COMPATIBILITY_MODE } from '@angular/material';


import {HttpClientModule} from '@angular/common/http';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {AdminComponent} from './admin/admin.component';
import {SubmissionListComponent} from "./submissionList/submissionList.component";
import {AccountInfoComponent} from './accountInfo/accountInfo.component';
import {NewSubmissionComponent} from './newSubmission/newSubmission.component'
import {NavComponent} from "./nav/nav.component";
import {Routing} from './app.routes';
import {APP_BASE_HREF} from '@angular/common';
import {CustomModule} from './custom.module';
import { LoginComponent } from './login/login.component';
import { AuthenticationService } from './authentication.service';


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
        LoginComponent,
    ],
    providers: [
        AuthenticationService,
        {provide: APP_BASE_HREF, useValue: '/'},
        {provide: MATERIAL_COMPATIBILITY_MODE, useValue: true}
    ],
    entryComponents: [
    ],
    bootstrap: [AppComponent]
})

export class AppModule {
}
