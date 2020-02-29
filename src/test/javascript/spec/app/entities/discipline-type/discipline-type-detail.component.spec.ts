import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AssistantCollaboratorTestModule } from '../../../test.module';
import { DisciplineTypeDetailComponent } from 'app/entities/discipline-type/discipline-type-detail.component';
import { DisciplineType } from 'app/shared/model/discipline-type.model';

describe('Component Tests', () => {
  describe('DisciplineType Management Detail Component', () => {
    let comp: DisciplineTypeDetailComponent;
    let fixture: ComponentFixture<DisciplineTypeDetailComponent>;
    const route = ({ data: of({ disciplineType: new DisciplineType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AssistantCollaboratorTestModule],
        declarations: [DisciplineTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DisciplineTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DisciplineTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load disciplineType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.disciplineType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
