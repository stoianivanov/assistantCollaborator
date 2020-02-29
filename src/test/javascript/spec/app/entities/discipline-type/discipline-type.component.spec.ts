import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AssistantCollaboratorTestModule } from '../../../test.module';
import { DisciplineTypeComponent } from 'app/entities/discipline-type/discipline-type.component';
import { DisciplineTypeService } from 'app/entities/discipline-type/discipline-type.service';
import { DisciplineType } from 'app/shared/model/discipline-type.model';

describe('Component Tests', () => {
  describe('DisciplineType Management Component', () => {
    let comp: DisciplineTypeComponent;
    let fixture: ComponentFixture<DisciplineTypeComponent>;
    let service: DisciplineTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AssistantCollaboratorTestModule],
        declarations: [DisciplineTypeComponent]
      })
        .overrideTemplate(DisciplineTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DisciplineTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DisciplineTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DisciplineType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.disciplineTypes && comp.disciplineTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
