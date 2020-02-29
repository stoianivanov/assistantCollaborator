import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IDiscipline, Discipline } from 'app/shared/model/discipline.model';
import { DisciplineService } from './discipline.service';
import { IDisciplineType } from 'app/shared/model/discipline-type.model';
import { DisciplineTypeService } from 'app/entities/discipline-type/discipline-type.service';
import { IIdentity } from 'app/shared/model/identity.model';
import { IdentityService } from 'app/entities/identity/identity.service';

type SelectableEntity = IDisciplineType | IIdentity;

@Component({
  selector: 'jhi-discipline-update',
  templateUrl: './discipline-update.component.html'
})
export class DisciplineUpdateComponent implements OnInit {
  isSaving = false;
  types: IDisciplineType[] = [];
  identities: IIdentity[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    department: [],
    name: [],
    hoursForLecture: [],
    hoursForWorkshop: [],
    hoursForExercise: [],
    semester: [],
    numberOfStudents: [],
    incomingTest: [],
    type: [],
    lectos: []
  });

  constructor(
    protected disciplineService: DisciplineService,
    protected disciplineTypeService: DisciplineTypeService,
    protected identityService: IdentityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ discipline }) => {
      this.updateForm(discipline);

      this.disciplineTypeService
        .query({ filter: 'discipline-is-null' })
        .pipe(
          map((res: HttpResponse<IDisciplineType[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDisciplineType[]) => {
          if (!discipline.type || !discipline.type.id) {
            this.types = resBody;
          } else {
            this.disciplineTypeService
              .find(discipline.type.id)
              .pipe(
                map((subRes: HttpResponse<IDisciplineType>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDisciplineType[]) => (this.types = concatRes));
          }
        });

      this.identityService.query().subscribe((res: HttpResponse<IIdentity[]>) => (this.identities = res.body || []));
    });
  }

  updateForm(discipline: IDiscipline): void {
    this.editForm.patchValue({
      id: discipline.id,
      code: discipline.code,
      department: discipline.department,
      name: discipline.name,
      hoursForLecture: discipline.hoursForLecture,
      hoursForWorkshop: discipline.hoursForWorkshop,
      hoursForExercise: discipline.hoursForExercise,
      semester: discipline.semester,
      numberOfStudents: discipline.numberOfStudents,
      incomingTest: discipline.incomingTest,
      type: discipline.type,
      lectos: discipline.lectos
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
      code: this.editForm.get(['code'])!.value,
      department: this.editForm.get(['department'])!.value,
      name: this.editForm.get(['name'])!.value,
      hoursForLecture: this.editForm.get(['hoursForLecture'])!.value,
      hoursForWorkshop: this.editForm.get(['hoursForWorkshop'])!.value,
      hoursForExercise: this.editForm.get(['hoursForExercise'])!.value,
      semester: this.editForm.get(['semester'])!.value,
      numberOfStudents: this.editForm.get(['numberOfStudents'])!.value,
      incomingTest: this.editForm.get(['incomingTest'])!.value,
      type: this.editForm.get(['type'])!.value,
      lectos: this.editForm.get(['lectos'])!.value
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: IIdentity[], option: IIdentity): IIdentity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
