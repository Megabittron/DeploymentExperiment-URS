import {ModuleWithProviders} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {AdminComponent} from './admin/admin.component';
import {AccountInfoComponent} from './accountInfo/accountInfo.component';
import {SubmissionListComponent} from "./submissionList/submissionList.component";
import {NewSubmissionComponent} from "./newSubmission/newSubmission.component"
import {UserIsLoggedInGuard} from "./user-is-logged-in.guard";

// Route Configuration
export const routes: Routes = [
    {path: '', component: HomeComponent, canActivate: [UserIsLoggedInGuard]},
    {path: 'admin', component: AdminComponent, canActivate: [UserIsLoggedInGuard]},
    {path: 'accountInfo', component: AccountInfoComponent, canActivate: [UserIsLoggedInGuard]},
    {path: 'submissionList', component: SubmissionListComponent, canActivate: [UserIsLoggedInGuard]},
    {path: 'newSubmission', component: NewSubmissionComponent, canActivate: [UserIsLoggedInGuard]}
];

export const Routing: ModuleWithProviders = RouterModule.forRoot(routes);
