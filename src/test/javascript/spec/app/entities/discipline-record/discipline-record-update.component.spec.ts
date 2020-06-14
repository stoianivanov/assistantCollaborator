import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AssistantCollaboratorTestModule } from '../../../test.module';
import { DisciplineRecordUpdateComponent } from 'app/entities/discipline-record/discipline-record-update.component';
import { DisciplineRecordService } from 'app/entities/discipline-record/discipline-record.service';
import { DisciplineRecord } from 'app/shared/model/discipline-record.model';

describe('Component Tests', () => {
  describe('DisciplineRecord Management Update Component', () => {
    let comp: DisciplineRecordUpdateComponent;
    let fixture: ComponentFixture<DisciplineRecordUpdateComponent>;
    let service: DisciplineRecordService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AssistantCollaboratorTestModule],
        declarations: [DisciplineRecordUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DisciplineRecordUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DisciplineRecordUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DisciplineRecordService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DisciplineRecord(123);
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
        const entity = new DisciplineRecord();
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
