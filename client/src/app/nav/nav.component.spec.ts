import {TestBed, ComponentFixture} from '@angular/core/testing';
import {NavComponent} from './nav.component';
import {CustomModule} from '../custom.module';
import {MATERIAL_COMPATIBILITY_MODE} from '@angular/material';
import {AppModule} from "../app.module";
import {RouterTestingModule} from "@angular/router/testing";
import {routes} from "../app.routes";


describe('Nav', () => {

    let component: NavComponent;
    let fixture: ComponentFixture<NavComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [
                CustomModule,
                AppModule,
                RouterTestingModule.withRoutes(routes)
            ],
            providers: [{provide: MATERIAL_COMPATIBILITY_MODE, useValue: true}],
        });

        fixture = TestBed.createComponent(NavComponent);

        component = fixture.componentInstance;

    });

    it('should create the app', () => {
        expect(fixture).toBeTruthy();
    });
});
