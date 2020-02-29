import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDirection, Direction } from 'app/shared/model/direction.model';
import { DirectionService } from './direction.service';
import { IDiscipline } from 'app/shared/model/discipline.model';
import { DisciplineService } from 'app/entities/discipline/discipline.service';

@Component({
  selector: 'jhi-direction-update',
  templateUrl: './direction-update.component.html'
})
export class DirectionUpdateComponent implements OnInit {
  isSaving = false;
  disciplines: IDiscipline[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    code: [],
    appropriate: [],
    discipline: []
  });

  constructor(
    protected directionService: DirectionService,
    protected disciplineService: DisciplineService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ direction }) => {
      this.updateForm(direction);

      this.disciplineService.query().subscribe((res: HttpResponse<IDiscipline[]>) => (this.disciplines = res.body || []));
    });
  }

  updateForm(direction: IDirection): void {
    this.editForm.patchValue({
      id: direction.id,
      name: direction.name,
      code: direction.code,
      appropriate: direction.appropriate,
      discipline: direction.discipline
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const direction = this.createFromForm();
    if (direction.id !== undefined) {
      this.subscribeToSaveResponse(this.directionService.update(direction));
    } else {
      this.subscribeToSaveResponse(this.directionService.create(direction));
    }
  }

  private createFromForm(): IDirection {
    return {
      ...new Direction(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      code: this.editForm.get(['code'])!.value,
      appropriate: this.editForm.get(['appropriate'])!.value,
      discipline: this.editForm.get(['discipline'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDirection>>): void {
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
