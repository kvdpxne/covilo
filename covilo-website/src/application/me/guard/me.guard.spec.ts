import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { meGuard } from './me.guard';

describe('meGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => meGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
