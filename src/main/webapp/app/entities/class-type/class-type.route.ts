import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IClassType, ClassType } from 'app/shared/model/class-type.model';
import { ClassTypeService } from './class-type.service';
import { ClassTypeComponent } from './class-type.component';
import { ClassTypeDetailComponent } from './class-type-detail.component';
import { ClassTypeUpdateComponent } from './class-type-update.component';

@Injectable({ providedIn: 'root' })
export class ClassTypeResolve implements Resolve<IClassType> {
  constructor(private service: ClassTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClassType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((classType: HttpResponse<ClassType>) => {
          if (classType.body) {
            return of(classType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ClassType());
  }
}

export const classTypeRoute: Routes = [
  {
    path: '',
    component: ClassTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'assistantCollaboratorApp.classType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ClassTypeDetailComponent,
    resolve: {
      classType: ClassTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'assistantCollaboratorApp.classType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ClassTypeUpdateComponent,
    resolve: {
      classType: ClassTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'assistantCollaboratorApp.classType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ClassTypeUpdateComponent,
    resolve: {
      classType: ClassTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'assistantCollaboratorApp.classType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
