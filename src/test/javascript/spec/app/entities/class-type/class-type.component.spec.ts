import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AssistantCollaboratorTestModule } from '../../../test.module';
import { ClassTypeComponent } from 'app/entities/class-type/class-type.component';
import { ClassTypeService } from 'app/entities/class-type/class-type.service';
import { ClassType } from 'app/shared/model/class-type.model';

describe('Component Tests', () => {
  describe('ClassType Management Component', () => {
    let comp: ClassTypeComponent;
    let fixture: ComponentFixture<ClassTypeComponent>;
    let service: ClassTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AssistantCollaboratorTestModule],
        declarations: [ClassTypeComponent]
      })
        .overrideTemplate(ClassTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClassTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClassTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ClassType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.classTypes && comp.classTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
