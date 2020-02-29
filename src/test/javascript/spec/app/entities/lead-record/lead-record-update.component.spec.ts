import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AssistantCollaboratorTestModule } from '../../../test.module';
import { LeadRecordUpdateComponent } from 'app/entities/lead-record/lead-record-update.component';
import { LeadRecordService } from 'app/entities/lead-record/lead-record.service';
import { LeadRecord } from 'app/shared/model/lead-record.model';

describe('Component Tests', () => {
  describe('LeadRecord Management Update Component', () => {
    let comp: LeadRecordUpdateComponent;
    let fixture: ComponentFixture<LeadRecordUpdateComponent>;
    let service: LeadRecordService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AssistantCollaboratorTestModule],
        declarations: [LeadRecordUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LeadRecordUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LeadRecordUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LeadRecordService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LeadRecord(123);
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
        const entity = new LeadRecord();
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
