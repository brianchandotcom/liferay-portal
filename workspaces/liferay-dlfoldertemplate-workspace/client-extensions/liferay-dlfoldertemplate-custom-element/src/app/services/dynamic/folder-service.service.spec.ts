import { TestBed } from '@angular/core/testing';

import { FolderServiceService } from './folder-service.service';

describe('FolderServiceService', () => {
  let service: FolderServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FolderServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
