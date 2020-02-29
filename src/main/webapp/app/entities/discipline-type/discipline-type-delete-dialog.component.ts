import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDisciplineType } from 'app/shared/model/discipline-type.model';
import { DisciplineTypeService } from './discipline-type.service';

@Component({
  templateUrl: './discipline-type-delete-dialog.component.html'
})
export class DisciplineTypeDeleteDialogComponent {
  disciplineType?: IDisciplineType;

  constructor(
    protected disciplineTypeService: DisciplineTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.disciplineTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('disciplineTypeListModification');
      this.activeModal.close();
    });
  }
}
