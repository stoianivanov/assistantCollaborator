import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILeadRecord } from 'app/shared/model/lead-record.model';
import { LeadRecordService } from './lead-record.service';
import { LeadRecordDeleteDialogComponent } from './lead-record-delete-dialog.component';

@Component({
  selector: 'jhi-lead-record',
  templateUrl: './lead-record.component.html'
})
export class LeadRecordComponent implements OnInit, OnDestroy {
  leadRecords?: ILeadRecord[];
  eventSubscriber?: Subscription;

  constructor(protected leadRecordService: LeadRecordService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.leadRecordService.query().subscribe((res: HttpResponse<ILeadRecord[]>) => (this.leadRecords = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLeadRecords();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILeadRecord): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLeadRecords(): void {
    this.eventSubscriber = this.eventManager.subscribe('leadRecordListModification', () => this.loadAll());
  }

  delete(leadRecord: ILeadRecord): void {
    const modalRef = this.modalService.open(LeadRecordDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.leadRecord = leadRecord;
  }
}
