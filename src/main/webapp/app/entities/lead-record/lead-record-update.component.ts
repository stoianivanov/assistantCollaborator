import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ILeadRecord, LeadRecord } from 'app/shared/model/lead-record.model';
import { LeadRecordService } from './lead-record.service';
import { IDiscipline } from 'app/shared/model/discipline.model';
import { DisciplineService } from 'app/entities/discipline/discipline.service';

@Component({
  selector: 'jhi-lead-record-update',
  templateUrl: './lead-record-update.component.html'
})
export class LeadRecordUpdateComponent implements OnInit {
  isSaving = false;
  assitents: IDiscipline[] = [];

  editForm = this.fb.group({
    id: [],
    maxHoursForLecture: [],
    maxHoursForExercise: [],
    maxHoursForWorkshop: [],
    course: [],
    type: [],
    assitent: []
  });

  constructor(
    protected leadRecordService: LeadRecordService,
    protected disciplineService: DisciplineService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ leadRecord }) => {
      this.updateForm(leadRecord);

      this.disciplineService
        .query({ filter: 'leadrecord-is-null' })
        .pipe(
          map((res: HttpResponse<IDiscipline[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDiscipline[]) => {
          if (!leadRecord.assitent || !leadRecord.assitent.id) {
            this.assitents = resBody;
          } else {
            this.disciplineService
              .find(leadRecord.assitent.id)
              .pipe(
                map((subRes: HttpResponse<IDiscipline>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDiscipline[]) => (this.assitents = concatRes));
          }
        });
    });
  }

  updateForm(leadRecord: ILeadRecord): void {
    this.editForm.patchValue({
      id: leadRecord.id,
      maxHoursForLecture: leadRecord.maxHoursForLecture,
      maxHoursForExercise: leadRecord.maxHoursForExercise,
      maxHoursForWorkshop: leadRecord.maxHoursForWorkshop,
      course: leadRecord.course,
      type: leadRecord.type,
      assitent: leadRecord.assitent
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const leadRecord = this.createFromForm();
    if (leadRecord.id !== undefined) {
      this.subscribeToSaveResponse(this.leadRecordService.update(leadRecord));
    } else {
      this.subscribeToSaveResponse(this.leadRecordService.create(leadRecord));
    }
  }

  private createFromForm(): ILeadRecord {
    return {
      ...new LeadRecord(),
      id: this.editForm.get(['id'])!.value,
      maxHoursForLecture: this.editForm.get(['maxHoursForLecture'])!.value,
      maxHoursForExercise: this.editForm.get(['maxHoursForExercise'])!.value,
      maxHoursForWorkshop: this.editForm.get(['maxHoursForWorkshop'])!.value,
      course: this.editForm.get(['course'])!.value,
      type: this.editForm.get(['type'])!.value,
      assitent: this.editForm.get(['assitent'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILeadRecord>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IDiscipline): any {
    return item.id;
  }
}
