import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AssistantCollaboratorTestModule } from '../../../test.module';
import { DisciplineRecordComponent } from 'app/entities/discipline-record/discipline-record.component';
import { DisciplineRecordService } from 'app/entities/discipline-record/discipline-record.service';
import { DisciplineRecord } from 'app/shared/model/discipline-record.model';

describe('Component Tests', () => {
  describe('DisciplineRecord Management Component', () => {
    let comp: DisciplineRecordComponent;
    let fixture: ComponentFixture<DisciplineRecordComponent>;
    let service: DisciplineRecordService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AssistantCollaboratorTestModule],
        declarations: [DisciplineRecordComponent]
      })
        .overrideTemplate(DisciplineRecordComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DisciplineRecordComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DisciplineRecordService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DisciplineRecord(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.disciplineRecords && comp.disciplineRecords[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
