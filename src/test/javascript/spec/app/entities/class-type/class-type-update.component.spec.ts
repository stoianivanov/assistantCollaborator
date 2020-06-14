import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AssistantCollaboratorTestModule } from '../../../test.module';
import { ClassTypeUpdateComponent } from 'app/entities/class-type/class-type-update.component';
import { ClassTypeService } from 'app/entities/class-type/class-type.service';
import { ClassType } from 'app/shared/model/class-type.model';

describe('Component Tests', () => {
  describe('ClassType Management Update Component', () => {
    let comp: ClassTypeUpdateComponent;
    let fixture: ComponentFixture<ClassTypeUpdateComponent>;
    let service: ClassTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AssistantCollaboratorTestModule],
        declarations: [ClassTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ClassTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClassTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClassTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ClassType(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ClassType();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
