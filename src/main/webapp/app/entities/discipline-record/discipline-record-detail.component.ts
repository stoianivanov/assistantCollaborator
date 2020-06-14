import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDisciplineRecord } from 'app/shared/model/discipline-record.model';

@Component({
  selector: 'jhi-discipline-record-detail',
  templateUrl: './discipline-record-detail.component.html'
})
export class DisciplineRecordDetailComponent implements OnInit {
  disciplineRecord: IDisciplineRecord | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ disciplineRecord }) => (this.disciplineRecord = disciplineRecord));
  }

  previousState(): void {
    window.history.back();
  }
}
