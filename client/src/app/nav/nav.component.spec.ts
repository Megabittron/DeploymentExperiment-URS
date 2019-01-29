import {TestBed, ComponentFixture} from '@angular/core/testing';
import {NavComponent} from './nav.component';
import {CustomModule} from '../custom.module';
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
        });

        fixture = TestBed.createComponent(NavComponent);

        component = fixture.componentInstance;

    });

    it('should create the app', () => {
        expect(fixture).toBeTruthy();
    });
});
