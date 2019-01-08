import {TestBed, ComponentFixture} from '@angular/core/testing';
import {DebugElement} from '@angular/core';
import {CustomModule} from '../custom.module';
import {MATERIAL_COMPATIBILITY_MODE} from '@angular/material';
import {AccountInfoComponent} from "./accountInfo.component";

describe('AccountInfo', () => {

    let component: AccountInfoComponent;
    let fixture: ComponentFixture<AccountInfoComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [CustomModule],
            declarations: [AccountInfoComponent], // declare the test component
            providers: [{provide: MATERIAL_COMPATIBILITY_MODE, useValue: true}],
        });

        fixture = TestBed.createComponent(AccountInfoComponent);

        component = fixture.componentInstance; // BannerComponent test instance
    });

});
