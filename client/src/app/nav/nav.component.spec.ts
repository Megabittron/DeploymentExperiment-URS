import {TestBed, ComponentFixture} from '@angular/core/testing';
import {NavComponent} from './nav.component';
import {CustomModule} from '../custom.module';
import {MATERIAL_COMPATIBILITY_MODE} from '@angular/material';

describe('Nav', () => {

    let component: NavComponent;
    let fixture: ComponentFixture<NavComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [CustomModule],
            declarations: [NavComponent], // declare the test component
            providers: [{provide: MATERIAL_COMPATIBILITY_MODE, useValue: true}],
        });

        fixture = TestBed.createComponent(NavComponent);

        component = fixture.componentInstance; // BannerComponent test instance


    });

});
