import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILeadRecord, LeadRecord } from 'app/shared/model/lead-record.model';
import { LeadRecordService } from './lead-record.service';
import { LeadRecordComponent } from './lead-record.component';
import { LeadRecordDetailComponent } from './lead-record-detail.component';
import { LeadRecordUpdateComponent } from './lead-record-update.component';

@Injectable({ providedIn: 'root' })
export class LeadRecordResolve implements Resolve<ILeadRecord> {
  constructor(private service: LeadRecordService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILeadRecord> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((leadRecord: HttpResponse<LeadRecord>) => {
          if (leadRecord.body) {
            return of(leadRecord.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LeadRecord());
  }
}

export const leadRecordRoute: Routes = [
  {
    path: '',
    component: LeadRecordComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'assistantCollaboratorApp.leadRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LeadRecordDetailComponent,
    resolve: {
      leadRecord: LeadRecordResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'assistantCollaboratorApp.leadRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LeadRecordUpdateComponent,
    resolve: {
      leadRecord: LeadRecordResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'assistantCollaboratorApp.leadRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LeadRecordUpdateComponent,
    resolve: {
      leadRecord: LeadRecordResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'assistantCollaboratorApp.leadRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
