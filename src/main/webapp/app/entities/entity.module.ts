import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cloud-service-broker-ui',
        loadChildren: () =>
          import('./cloud-service-broker-ui/cloud-service-broker-ui.module').then(m => m.CloudservicebrokeruiCloudServiceBrokerUIModule)
      },
      {
        path: 'serviceinstance',
        loadChildren: () => import('./serviceinstance/serviceinstance.module').then(m => m.CloudservicebrokeruiServiceinstanceModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class CloudservicebrokeruiEntityModule {}
