import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDisciplineRecord } from 'app/shared/model/discipline-record.model';
import { DisciplineRecordService } from './discipline-record.service';
import { DisciplineRecordDeleteDialogComponent } from './discipline-record-delete-dialog.component';

@Component({
  selector: 'jhi-discipline-record',
  templateUrl: './discipline-record.component.html'
})
export class DisciplineRecordComponent implements OnInit, OnDestroy {
  disciplineRecords?: IDisciplineRecord[];
  eventSubscriber?: Subscription;

  constructor(
    protected disciplineRecordService: DisciplineRecordService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.disciplineRecordService.query().subscribe((res: HttpResponse<IDisciplineRecord[]>) => (this.disciplineRecords = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDisciplineRecords();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDisciplineRecord): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDisciplineRecords(): void {
    this.eventSubscriber = this.eventManager.subscribe('disciplineRecordListModification', () => this.loadAll());
  }

  delete(disciplineRecord: IDisciplineRecord): void {
    const modalRef = this.modalService.open(DisciplineRecordDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.disciplineRecord = disciplineRecord;
  }
}
