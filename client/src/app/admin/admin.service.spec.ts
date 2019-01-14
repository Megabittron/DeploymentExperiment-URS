import { TestBed, inject } from '@angular/core/testing';

import { AdminService } from './admin.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('AdminService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      providers: [AdminService]
    });
  });

  it('should be created', inject([AdminService], (service: AdminService) => {
    expect(service).toBeTruthy();
  }));
});
