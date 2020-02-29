import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDisciplineType } from 'app/shared/model/discipline-type.model';

@Component({
  selector: 'jhi-discipline-type-detail',
  templateUrl: './discipline-type-detail.component.html'
})
export class DisciplineTypeDetailComponent implements OnInit {
  disciplineType: IDisciplineType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ disciplineType }) => (this.disciplineType = disciplineType));
  }

  previousState(): void {
    window.history.back();
  }
}
