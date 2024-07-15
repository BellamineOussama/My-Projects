import { TestBed } from '@angular/core/testing';

import { SharService } from './shar.service';

describe('SharService', () => {
  let service: SharService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SharService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
