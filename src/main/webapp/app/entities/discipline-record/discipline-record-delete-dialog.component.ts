import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDisciplineRecord } from 'app/shared/model/discipline-record.model';
import { DisciplineRecordService } from './discipline-record.service';

@Component({
  templateUrl: './discipline-record-delete-dialog.component.html'
})
export class DisciplineRecordDeleteDialogComponent {
  disciplineRecord?: IDisciplineRecord;

  constructor(
    protected disciplineRecordService: DisciplineRecordService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.disciplineRecordService.delete(id).subscribe(() => {
      this.eventManager.broadcast('disciplineRecordListModification');
      this.activeModal.close();
    });
  }
}
