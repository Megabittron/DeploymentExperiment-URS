import { TestBed, inject } from '@angular/core/testing';

import { AccountInfoService } from './account-info.service';

describe('AccountInfoService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AccountInfoService]
    });
  });

  it('should be created', inject([AccountInfoService], (service: AccountInfoService) => {
    expect(service).toBeTruthy();
  }));
});
