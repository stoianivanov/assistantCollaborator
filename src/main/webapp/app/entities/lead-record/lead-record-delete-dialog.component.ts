import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILeadRecord } from 'app/shared/model/lead-record.model';
import { LeadRecordService } from './lead-record.service';

@Component({
  templateUrl: './lead-record-delete-dialog.component.html'
})
export class LeadRecordDeleteDialogComponent {
  leadRecord?: ILeadRecord;

  constructor(
    protected leadRecordService: LeadRecordService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.leadRecordService.delete(id).subscribe(() => {
      this.eventManager.broadcast('leadRecordListModification');
      this.activeModal.close();
    });
  }
}
