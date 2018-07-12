import {ModuleWithProviders} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {AdminComponent} from './admin/admin.component';
import {AccountInfoComponent} from './accountInfo/accountInfo.component';
import {SubmissionListComponent} from "./submissionList/submissionList.component";
import {NewSubmissionComponent} from "./newSubmission/newSubmission.component"

// Route Configuration
export const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'admin', component: AdminComponent},
    {path: 'accountInfo', component: AccountInfoComponent},
    {path: 'submissionList', component: SubmissionListComponent},
    {path: 'newSubmission', component: NewSubmissionComponent}
];

export const Routing: ModuleWithProviders = RouterModule.forRoot(routes);
