import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICloudServiceBrokerUI, CloudServiceBrokerUI } from 'app/shared/model/cloud-service-broker-ui.model';
import { CloudServiceBrokerUIService } from './cloud-service-broker-ui.service';
import { CloudServiceBrokerUIComponent } from './cloud-service-broker-ui.component';
import { CloudServiceBrokerUIDetailComponent } from './cloud-service-broker-ui-detail.component';
import { CloudServiceBrokerUIUpdateComponent } from './cloud-service-broker-ui-update.component';

@Injectable({ providedIn: 'root' })
export class CloudServiceBrokerUIResolve implements Resolve<ICloudServiceBrokerUI> {
  constructor(private service: CloudServiceBrokerUIService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICloudServiceBrokerUI> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cloudServiceBrokerUI: HttpResponse<CloudServiceBrokerUI>) => {
          if (cloudServiceBrokerUI.body) {
            return of(cloudServiceBrokerUI.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CloudServiceBrokerUI());
  }
}

export const cloudServiceBrokerUIRoute: Routes = [
  {
    path: '',
    component: CloudServiceBrokerUIComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cloudservicebrokeruiApp.cloudServiceBrokerUI.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CloudServiceBrokerUIDetailComponent,
    resolve: {
      cloudServiceBrokerUI: CloudServiceBrokerUIResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cloudservicebrokeruiApp.cloudServiceBrokerUI.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CloudServiceBrokerUIUpdateComponent,
    resolve: {
      cloudServiceBrokerUI: CloudServiceBrokerUIResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cloudservicebrokeruiApp.cloudServiceBrokerUI.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CloudServiceBrokerUIUpdateComponent,
    resolve: {
      cloudServiceBrokerUI: CloudServiceBrokerUIResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cloudservicebrokeruiApp.cloudServiceBrokerUI.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
