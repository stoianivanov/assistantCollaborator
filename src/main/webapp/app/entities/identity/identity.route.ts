import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIdentity, Identity } from 'app/shared/model/identity.model';
import { IdentityService } from './identity.service';
import { IdentityComponent } from './identity.component';
import { IdentityDetailComponent } from './identity-detail.component';
import { IdentityUpdateComponent } from './identity-update.component';

@Injectable({ providedIn: 'root' })
export class IdentityResolve implements Resolve<IIdentity> {
  constructor(private service: IdentityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIdentity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((identity: HttpResponse<Identity>) => {
          if (identity.body) {
            return of(identity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Identity());
  }
}

export const identityRoute: Routes = [
  {
    path: '',
    component: IdentityComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'assistantCollaboratorApp.identity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IdentityDetailComponent,
    resolve: {
      identity: IdentityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'assistantCollaboratorApp.identity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IdentityUpdateComponent,
    resolve: {
      identity: IdentityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'assistantCollaboratorApp.identity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IdentityUpdateComponent,
    resolve: {
      identity: IdentityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'assistantCollaboratorApp.identity.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
