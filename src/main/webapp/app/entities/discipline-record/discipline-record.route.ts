import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDisciplineRecord, DisciplineRecord } from 'app/shared/model/discipline-record.model';
import { DisciplineRecordService } from './discipline-record.service';
import { DisciplineRecordComponent } from './discipline-record.component';
import { DisciplineRecordDetailComponent } from './discipline-record-detail.component';
import { DisciplineRecordUpdateComponent } from './discipline-record-update.component';

@Injectable({ providedIn: 'root' })
export class DisciplineRecordResolve implements Resolve<IDisciplineRecord> {
  constructor(private service: DisciplineRecordService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDisciplineRecord> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((disciplineRecord: HttpResponse<DisciplineRecord>) => {
          if (disciplineRecord.body) {
            return of(disciplineRecord.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DisciplineRecord());
  }
}

export const disciplineRecordRoute: Routes = [
  {
    path: '',
    component: DisciplineRecordComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'assistantCollaboratorApp.disciplineRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DisciplineRecordDetailComponent,
    resolve: {
      disciplineRecord: DisciplineRecordResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'assistantCollaboratorApp.disciplineRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DisciplineRecordUpdateComponent,
    resolve: {
      disciplineRecord: DisciplineRecordResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'assistantCollaboratorApp.disciplineRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DisciplineRecordUpdateComponent,
    resolve: {
      disciplineRecord: DisciplineRecordResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'assistantCollaboratorApp.disciplineRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
