import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CloudservicebrokeruiSharedModule } from 'app/shared/shared.module';
import { CloudServiceBrokerUIComponent } from './cloud-service-broker-ui.component';
import { CloudServiceBrokerUIDetailComponent } from './cloud-service-broker-ui-detail.component';
import { CloudServiceBrokerUIUpdateComponent } from './cloud-service-broker-ui-update.component';
import { CloudServiceBrokerUIDeleteDialogComponent } from './cloud-service-broker-ui-delete-dialog.component';
import { cloudServiceBrokerUIRoute } from './cloud-service-broker-ui.route';

@NgModule({
  imports: [CloudservicebrokeruiSharedModule, RouterModule.forChild(cloudServiceBrokerUIRoute)],
  declarations: [
    CloudServiceBrokerUIComponent,
    CloudServiceBrokerUIDetailComponent,
    CloudServiceBrokerUIUpdateComponent,
    CloudServiceBrokerUIDeleteDialogComponent
  ],
  entryComponents: [CloudServiceBrokerUIDeleteDialogComponent]
})
export class CloudservicebrokeruiCloudServiceBrokerUIModule {}
