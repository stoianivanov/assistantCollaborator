import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IDisciplineRecord, DisciplineRecord } from 'app/shared/model/discipline-record.model';
import { DisciplineRecordService } from './discipline-record.service';
import { IClassType } from 'app/shared/model/class-type.model';
import { ClassTypeService } from 'app/entities/class-type/class-type.service';
import { IIdentity } from 'app/shared/model/identity.model';
import { IdentityService } from 'app/entities/identity/identity.service';
import { IDirection } from 'app/shared/model/direction.model';
import { DirectionService } from 'app/entities/direction/direction.service';

type SelectableEntity = IClassType | IIdentity | IDirection;

type SelectableManyToManyEntity = IIdentity | IDirection;

@Component({
  selector: 'jhi-discipline-record-update',
  templateUrl: './discipline-record-update.component.html'
})
export class DisciplineRecordUpdateComponent implements OnInit {
  isSaving = false;
  classtypes: IClassType[] = [];
  identities: IIdentity[] = [];
  directions: IDirection[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    department: [],
    name: [],
    form: [],
    course: [],
    stream: [],
    group: [],
    hoursForLecture: [],
    hoursForWorkshop: [],
    hoursForExercise: [],
    numberOfStudents: [],
    classType: [],
    lectos: [],
    directions: []
  });

  constructor(
    protected disciplineRecordService: DisciplineRecordService,
    protected classTypeService: ClassTypeService,
    protected identityService: IdentityService,
    protected directionService: DirectionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ disciplineRecord }) => {
      this.updateForm(disciplineRecord);

      this.classTypeService
        .query({ filter: 'disciplinerecord-is-null' })
        .pipe(
          map((res: HttpResponse<IClassType[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IClassType[]) => {
          if (!disciplineRecord.classType || !disciplineRecord.classType.id) {
            this.classtypes = resBody;
          } else {
            this.classTypeService
              .find(disciplineRecord.classType.id)
              .pipe(
                map((subRes: HttpResponse<IClassType>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IClassType[]) => (this.classtypes = concatRes));
          }
        });

      this.identityService.query().subscribe((res: HttpResponse<IIdentity[]>) => (this.identities = res.body || []));

      this.directionService.query().subscribe((res: HttpResponse<IDirection[]>) => (this.directions = res.body || []));
    });
  }

  updateForm(disciplineRecord: IDisciplineRecord): void {
    this.editForm.patchValue({
      id: disciplineRecord.id,
      code: disciplineRecord.code,
      department: disciplineRecord.department,
      name: disciplineRecord.name,
      form: disciplineRecord.form,
      course: disciplineRecord.course,
      stream: disciplineRecord.stream,
      group: disciplineRecord.group,
      hoursForLecture: disciplineRecord.hoursForLecture,
      hoursForWorkshop: disciplineRecord.hoursForWorkshop,
      hoursForExercise: disciplineRecord.hoursForExercise,
      numberOfStudents: disciplineRecord.numberOfStudents,
      classType: disciplineRecord.classType,
      lectos: disciplineRecord.lectos,
      directions: disciplineRecord.directions
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const disciplineRecord = this.createFromForm();
    if (disciplineRecord.id !== undefined) {
      this.subscribeToSaveResponse(this.disciplineRecordService.update(disciplineRecord));
    } else {
      this.subscribeToSaveResponse(this.disciplineRecordService.create(disciplineRecord));
    }
  }

  private createFromForm(): IDisciplineRecord {
    return {
      ...new DisciplineRecord(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      department: this.editForm.get(['department'])!.value,
      name: this.editForm.get(['name'])!.value,
      form: this.editForm.get(['form'])!.value,
      course: this.editForm.get(['course'])!.value,
      stream: this.editForm.get(['stream'])!.value,
      group: this.editForm.get(['group'])!.value,
      hoursForLecture: this.editForm.get(['hoursForLecture'])!.value,
      hoursForWorkshop: this.editForm.get(['hoursForWorkshop'])!.value,
      hoursForExercise: this.editForm.get(['hoursForExercise'])!.value,
      numberOfStudents: this.editForm.get(['numberOfStudents'])!.value,
      classType: this.editForm.get(['classType'])!.value,
      lectos: this.editForm.get(['lectos'])!.value,
      directions: this.editForm.get(['directions'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDisciplineRecord>>): void {
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

  getSelected(selectedVals: SelectableManyToManyEntity[], option: SelectableManyToManyEntity): SelectableManyToManyEntity {
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
