import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AssistantCollaboratorTestModule } from '../../../test.module';
import { DisciplineRecordDetailComponent } from 'app/entities/discipline-record/discipline-record-detail.component';
import { DisciplineRecord } from 'app/shared/model/discipline-record.model';

describe('Component Tests', () => {
  describe('DisciplineRecord Management Detail Component', () => {
    let comp: DisciplineRecordDetailComponent;
    let fixture: ComponentFixture<DisciplineRecordDetailComponent>;
    const route = ({ data: of({ disciplineRecord: new DisciplineRecord(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AssistantCollaboratorTestModule],
        declarations: [DisciplineRecordDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DisciplineRecordDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DisciplineRecordDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load disciplineRecord on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.disciplineRecord).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
