import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AssistantCollaboratorTestModule } from '../../../test.module';
import { DisciplineTypeUpdateComponent } from 'app/entities/discipline-type/discipline-type-update.component';
import { DisciplineTypeService } from 'app/entities/discipline-type/discipline-type.service';
import { DisciplineType } from 'app/shared/model/discipline-type.model';

describe('Component Tests', () => {
  describe('DisciplineType Management Update Component', () => {
    let comp: DisciplineTypeUpdateComponent;
    let fixture: ComponentFixture<DisciplineTypeUpdateComponent>;
    let service: DisciplineTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AssistantCollaboratorTestModule],
        declarations: [DisciplineTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DisciplineTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DisciplineTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DisciplineTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DisciplineType(123);
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
        const entity = new DisciplineType();
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
