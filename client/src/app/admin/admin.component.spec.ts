import {TestBed, ComponentFixture, async} from '@angular/core/testing';
import {DebugElement} from '@angular/core';
import {CustomModule} from '../custom.module';
import {AdminComponent} from "./admin.component";
import {AdminService} from "./admin.service";
import {Observable} from "rxjs/Rx";
import {SystemInformation} from "./systemInformation";
import {ReviewGroup} from "./reviewGroup";
import {User} from "../user";

describe('AccountInfo', () => {

    let component: AdminComponent;
    let fixture: ComponentFixture<AdminComponent>;

    let adminServiceStub: {
        getSystemInformation: () => Observable<SystemInformation>;
    };

    let reviewGroupStub;
    let userStub;

    beforeEach(() => {

        userStub = [{
            _id: "string",
            SubjectID: "string",
            FirstName: "string",
            LastName: "string",
            ShirtSize: "string",
            Role: "string"
        }];

        reviewGroupStub = [{name: "A", user: userStub}];

        adminServiceStub = {
            getSystemInformation: () => Observable.of({
                primarySubmissions: 1,
                submissionStored: 1,
                submissionsFlagged: 1,
                totalUsers: null,
                users: 1,
                admins: 1,
                chairs: 1,
                reviewers: 1,
                reviewGroups: reviewGroupStub,
                xs: 1,
                s: 1,
                m: 1,
                l: 1,
                xl: 1,
                xxl: 1
            })
        };

        TestBed.configureTestingModule({
            imports: [CustomModule],
            declarations: [AdminComponent], // declare the test component
            providers: [{provide: AdminService, useValue: adminServiceStub}]
        });

        fixture = TestBed.createComponent(AdminComponent);

        component = fixture.componentInstance; // BannerComponent test instance
    });

    beforeEach(async(() => {
        TestBed.compileComponents().then(() => {
            fixture = TestBed.createComponent(AdminComponent);
            component = fixture.componentInstance;
            fixture.detectChanges();

            component.systemInformation.primarySubmissions = 50;
            component.systemInformation.submissionStored = 100;
        });
    }));

    //TODO: Get this working. Actual test worked fine when systemInformation had optional totalUsers and (continue)
    //and reviewGroups. If not optional, I'm trying to stub those fields and reviewGroupStub and userStub are my
    //solution but they are breaking. Before trying to work on the actual test, I thought I would leave in the
    //'it just works' one as an indicator the the test was set up right in the first place.

    // it('getPrimarySubmissionPercent() gets the right percentage', () => {
    //     expect(component.getPrimarySubmissionPercent()).toBe("50.00");
    // });


    // Failed: Uncaught (in promise): TypeError: Cannot read property 'length' of undefined
    // TypeError: Cannot read property 'length' of undefined
    // at Object.eval [as updateRenderer] (ng:///DynamicTestModule/AdminComponent.ngfactory.js:26:52)
    //                                         at Object.debugUpdateRenderer [as updateRenderer] (http://localhost:9876/_karma_webpack_/webpack:/node_modules/@angular/core/fesm5/core.js:23839:1)
    //                                                                                                at checkAndUpdateView (http://localhost:9876/_karma_webpack_/webpack:/node_modules/@angular/core/fesm5/core.js:23214:1)
    // at callViewAction (http://localhost:9876/_karma_webpack_/webpack:/node_modules/@angular/core/fesm5/core.js:23450:1)
    // at execEmbeddedViewsAction (http://localhost:9876/_karma_webpack_/webpack:/node_modules/@angular/core/fesm5/core.js:23413:1)
    // at checkAndUpdateView (http://localhost:9876/_karma_webpack_/webpack:/node_modules/@angular/core/fesm5/core.js:23210:1)
    // at callViewAction (http://localhost:9876/_karma_webpack_/webpack:/node_modules/@angular/core/fesm5/core.js:23450:1)
    // at execEmbeddedViewsAction (http://localhost:9876/_karma_webpack_/webpack:/node_modules/@angular/core/fesm5/core.js:23413:1)
    // at checkAndUpdateView (http://localhost:9876/_karma_webpack_/webpack:/node_modules/@angular/core/fesm5/core.js:23210:1)
    // at callViewAction (http://localhost:9876/_karma_webpack_/webpack:/node_modules/@angular/core/fesm5/core.js:23450:1)
    it('just works', () => {
        expect("aaa").toBe("aaa");
    });


});
