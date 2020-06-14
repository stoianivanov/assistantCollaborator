import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClassType } from 'app/shared/model/class-type.model';
import { ClassTypeService } from './class-type.service';

@Component({
  templateUrl: './class-type-delete-dialog.component.html'
})
export class ClassTypeDeleteDialogComponent {
  classType?: IClassType;

  constructor(protected classTypeService: ClassTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.classTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('classTypeListModification');
      this.activeModal.close();
    });
  }
}
