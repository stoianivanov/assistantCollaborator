import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AssistantCollaboratorTestModule } from '../../../test.module';
import { LeadRecordComponent } from 'app/entities/lead-record/lead-record.component';
import { LeadRecordService } from 'app/entities/lead-record/lead-record.service';
import { LeadRecord } from 'app/shared/model/lead-record.model';

describe('Component Tests', () => {
  describe('LeadRecord Management Component', () => {
    let comp: LeadRecordComponent;
    let fixture: ComponentFixture<LeadRecordComponent>;
    let service: LeadRecordService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AssistantCollaboratorTestModule],
        declarations: [LeadRecordComponent]
      })
        .overrideTemplate(LeadRecordComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LeadRecordComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LeadRecordService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LeadRecord(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.leadRecords && comp.leadRecords[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
