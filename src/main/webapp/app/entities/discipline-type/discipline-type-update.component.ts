import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDisciplineType, DisciplineType } from 'app/shared/model/discipline-type.model';
import { DisciplineTypeService } from './discipline-type.service';

@Component({
  selector: 'jhi-discipline-type-update',
  templateUrl: './discipline-type-update.component.html'
})
export class DisciplineTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    type: []
  });

  constructor(protected disciplineTypeService: DisciplineTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ disciplineType }) => {
      this.updateForm(disciplineType);
    });
  }

  updateForm(disciplineType: IDisciplineType): void {
    this.editForm.patchValue({
      id: disciplineType.id,
      type: disciplineType.type
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const disciplineType = this.createFromForm();
    if (disciplineType.id !== undefined) {
      this.subscribeToSaveResponse(this.disciplineTypeService.update(disciplineType));
    } else {
      this.subscribeToSaveResponse(this.disciplineTypeService.create(disciplineType));
    }
  }

  private createFromForm(): IDisciplineType {
    return {
      ...new DisciplineType(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDisciplineType>>): void {
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
}
