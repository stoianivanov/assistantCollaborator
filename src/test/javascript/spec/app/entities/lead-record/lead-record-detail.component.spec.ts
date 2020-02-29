import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AssistantCollaboratorTestModule } from '../../../test.module';
import { LeadRecordDetailComponent } from 'app/entities/lead-record/lead-record-detail.component';
import { LeadRecord } from 'app/shared/model/lead-record.model';

describe('Component Tests', () => {
  describe('LeadRecord Management Detail Component', () => {
    let comp: LeadRecordDetailComponent;
    let fixture: ComponentFixture<LeadRecordDetailComponent>;
    const route = ({ data: of({ leadRecord: new LeadRecord(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AssistantCollaboratorTestModule],
        declarations: [LeadRecordDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LeadRecordDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LeadRecordDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load leadRecord on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.leadRecord).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
