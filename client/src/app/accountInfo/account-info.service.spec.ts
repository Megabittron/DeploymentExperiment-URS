import { TestBed, inject } from '@angular/core/testing';

import { AccountInfoService } from './account-info.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('AccountInfoService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      providers: [AccountInfoService]
    });
  });

  it('should be created', inject([AccountInfoService], (service: AccountInfoService) => {
    expect(service).toBeTruthy();
  }));
});
