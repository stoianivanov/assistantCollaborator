import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILeadRecord } from 'app/shared/model/lead-record.model';

@Component({
  selector: 'jhi-lead-record-detail',
  templateUrl: './lead-record-detail.component.html'
})
export class LeadRecordDetailComponent implements OnInit {
  leadRecord: ILeadRecord | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ leadRecord }) => (this.leadRecord = leadRecord));
  }

  previousState(): void {
    window.history.back();
  }
}
