import { TestBed } from '@angular/core/testing';
import { DeletedItemsFacade } from './deleted-items.facade';

describe('DeletedItemsFacade', () => {
  let service: DeletedItemsFacade;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeletedItemsFacade);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
