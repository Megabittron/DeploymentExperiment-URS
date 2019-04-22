import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SystemInfoComponent } from './system-info.component';
import {AdminService} from "../admin/admin.service";
import {Observable} from "rxjs/Rx";
import {SystemInformation} from "../admin/systemInformation";


describe('SystemInfoComponent', () => {
  let component: SystemInfoComponent;
  let fixture: ComponentFixture<SystemInfoComponent>;

  let adminServiceStub: {
      getSystemInformation: () => Observable<SystemInformation>;
  }

  beforeEach(async(() => {
      adminServiceStub = {

          getSystemInformation: () => new Observable<SystemInformation>()
      };

    TestBed.configureTestingModule({
      declarations: [ SystemInfoComponent ],
        providers: [{provide: AdminService, useValue: adminServiceStub}]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SystemInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
