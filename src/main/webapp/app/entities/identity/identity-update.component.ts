import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIdentity, Identity } from 'app/shared/model/identity.model';
import { IdentityService } from './identity.service';

@Component({
  selector: 'jhi-identity-update',
  templateUrl: './identity-update.component.html'
})
export class IdentityUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    fullName: [],
    scienceDegree: [],
    education: [],
    job: [],
    phoneNumber: [],
    eMail: []
  });

  constructor(protected identityService: IdentityService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ identity }) => {
      this.updateForm(identity);
    });
  }

  updateForm(identity: IIdentity): void {
    this.editForm.patchValue({
      id: identity.id,
      fullName: identity.fullName,
      scienceDegree: identity.scienceDegree,
      education: identity.education,
      job: identity.job,
      phoneNumber: identity.phoneNumber,
      eMail: identity.eMail
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const identity = this.createFromForm();
    if (identity.id !== undefined) {
      this.subscribeToSaveResponse(this.identityService.update(identity));
    } else {
      this.subscribeToSaveResponse(this.identityService.create(identity));
    }
  }

  private createFromForm(): IIdentity {
    return {
      ...new Identity(),
      id: this.editForm.get(['id'])!.value,
      fullName: this.editForm.get(['fullName'])!.value,
      scienceDegree: this.editForm.get(['scienceDegree'])!.value,
      education: this.editForm.get(['education'])!.value,
      job: this.editForm.get(['job'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      eMail: this.editForm.get(['eMail'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIdentity>>): void {
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
