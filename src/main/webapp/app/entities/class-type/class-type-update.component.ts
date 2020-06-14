import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IClassType, ClassType } from 'app/shared/model/class-type.model';
import { ClassTypeService } from './class-type.service';

@Component({
  selector: 'jhi-class-type-update',
  templateUrl: './class-type-update.component.html'
})
export class ClassTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    type: []
  });

  constructor(protected classTypeService: ClassTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classType }) => {
      this.updateForm(classType);
    });
  }

  updateForm(classType: IClassType): void {
    this.editForm.patchValue({
      id: classType.id,
      type: classType.type
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const classType = this.createFromForm();
    if (classType.id !== undefined) {
      this.subscribeToSaveResponse(this.classTypeService.update(classType));
    } else {
      this.subscribeToSaveResponse(this.classTypeService.create(classType));
    }
  }

  private createFromForm(): IClassType {
    return {
      ...new ClassType(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClassType>>): void {
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
