import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDisciplineType } from 'app/shared/model/discipline-type.model';
import { DisciplineTypeService } from './discipline-type.service';
import { DisciplineTypeDeleteDialogComponent } from './discipline-type-delete-dialog.component';

@Component({
  selector: 'jhi-discipline-type',
  templateUrl: './discipline-type.component.html'
})
export class DisciplineTypeComponent implements OnInit, OnDestroy {
  disciplineTypes?: IDisciplineType[];
  eventSubscriber?: Subscription;

  constructor(
    protected disciplineTypeService: DisciplineTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.disciplineTypeService.query().subscribe((res: HttpResponse<IDisciplineType[]>) => (this.disciplineTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDisciplineTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDisciplineType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDisciplineTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('disciplineTypeListModification', () => this.loadAll());
  }

  delete(disciplineType: IDisciplineType): void {
    const modalRef = this.modalService.open(DisciplineTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.disciplineType = disciplineType;
  }
}
