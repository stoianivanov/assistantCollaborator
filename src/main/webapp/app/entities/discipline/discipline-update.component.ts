import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDiscipline, Discipline } from 'app/shared/model/discipline.model';
import { DisciplineService } from './discipline.service';
import { IDisciplineRecord } from 'app/shared/model/discipline-record.model';
import { DisciplineRecordService } from 'app/entities/discipline-record/discipline-record.service';

@Component({
  selector: 'jhi-discipline-update',
  templateUrl: './discipline-update.component.html'
})
export class DisciplineUpdateComponent implements OnInit {
  isSaving = false;
  disciplinerecords: IDisciplineRecord[] = [];

  editForm = this.fb.group({
    id: [],
    description: [],
    disciplineType: [],
    disciplineRecord: []
  });

  constructor(
    protected disciplineService: DisciplineService,
    protected disciplineRecordService: DisciplineRecordService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ discipline }) => {
      this.updateForm(discipline);

      this.disciplineRecordService.query().subscribe((res: HttpResponse<IDisciplineRecord[]>) => (this.disciplinerecords = res.body || []));
    });
  }

  updateForm(discipline: IDiscipline): void {
    this.editForm.patchValue({
      id: discipline.id,
      description: discipline.description,
      disciplineType: discipline.disciplineType,
      disciplineRecord: discipline.disciplineRecord
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const discipline = this.createFromForm();
    if (discipline.id !== undefined) {
      this.subscribeToSaveResponse(this.disciplineService.update(discipline));
    } else {
      this.subscribeToSaveResponse(this.disciplineService.create(discipline));
    }
  }

  private createFromForm(): IDiscipline {
    return {
      ...new Discipline(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      disciplineType: this.editForm.get(['disciplineType'])!.value,
      disciplineRecord: this.editForm.get(['disciplineRecord'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiscipline>>): void {
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

  trackById(index: number, item: IDisciplineRecord): any {
    return item.id;
  }
}
