import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IServiceinstance, Serviceinstance } from 'app/shared/model/serviceinstance.model';
import { ServiceinstanceService } from './serviceinstance.service';
import { ServiceinstanceComponent } from './serviceinstance.component';
import { ServiceinstanceDetailComponent } from './serviceinstance-detail.component';
import { ServiceinstanceUpdateComponent } from './serviceinstance-update.component';

@Injectable({ providedIn: 'root' })
export class ServiceinstanceResolve implements Resolve<IServiceinstance> {
  constructor(private service: ServiceinstanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IServiceinstance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((serviceinstance: HttpResponse<Serviceinstance>) => {
          if (serviceinstance.body) {
            return of(serviceinstance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Serviceinstance());
  }
}

export const serviceinstanceRoute: Routes = [
  {
    path: '',
    component: ServiceinstanceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'cloudservicebrokeruiApp.serviceinstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceinstanceDetailComponent,
    resolve: {
      serviceinstance: ServiceinstanceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cloudservicebrokeruiApp.serviceinstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceinstanceUpdateComponent,
    resolve: {
      serviceinstance: ServiceinstanceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cloudservicebrokeruiApp.serviceinstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceinstanceUpdateComponent,
    resolve: {
      serviceinstance: ServiceinstanceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cloudservicebrokeruiApp.serviceinstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
