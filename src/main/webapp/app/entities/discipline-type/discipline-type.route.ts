import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDisciplineType, DisciplineType } from 'app/shared/model/discipline-type.model';
import { DisciplineTypeService } from './discipline-type.service';
import { DisciplineTypeComponent } from './discipline-type.component';
import { DisciplineTypeDetailComponent } from './discipline-type-detail.component';
import { DisciplineTypeUpdateComponent } from './discipline-type-update.component';

@Injectable({ providedIn: 'root' })
export class DisciplineTypeResolve implements Resolve<IDisciplineType> {
  constructor(private service: DisciplineTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDisciplineType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((disciplineType: HttpResponse<DisciplineType>) => {
          if (disciplineType.body) {
            return of(disciplineType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DisciplineType());
  }
}

export const disciplineTypeRoute: Routes = [
  {
    path: '',
    component: DisciplineTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'assistantCollaboratorApp.disciplineType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DisciplineTypeDetailComponent,
    resolve: {
      disciplineType: DisciplineTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'assistantCollaboratorApp.disciplineType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DisciplineTypeUpdateComponent,
    resolve: {
      disciplineType: DisciplineTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'assistantCollaboratorApp.disciplineType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DisciplineTypeUpdateComponent,
    resolve: {
      disciplineType: DisciplineTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'assistantCollaboratorApp.disciplineType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
