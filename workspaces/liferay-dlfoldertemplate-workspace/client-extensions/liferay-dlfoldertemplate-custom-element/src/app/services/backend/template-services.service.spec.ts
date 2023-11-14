import { TestBed } from '@angular/core/testing';

import { TemplateServicesService } from './template-services.service';

describe('TemplateServicesService', () => {
  let service: TemplateServicesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TemplateServicesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
