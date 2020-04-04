import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CloudservicebrokeruiSharedModule } from 'app/shared/shared.module';
import { ServiceinstanceComponent } from './serviceinstance.component';
import { ServiceinstanceDetailComponent } from './serviceinstance-detail.component';
import { ServiceinstanceUpdateComponent } from './serviceinstance-update.component';
import { ServiceinstanceDeleteDialogComponent } from './serviceinstance-delete-dialog.component';
import { serviceinstanceRoute } from './serviceinstance.route';

@NgModule({
  imports: [CloudservicebrokeruiSharedModule, RouterModule.forChild(serviceinstanceRoute)],
  declarations: [
    ServiceinstanceComponent,
    ServiceinstanceDetailComponent,
    ServiceinstanceUpdateComponent,
    ServiceinstanceDeleteDialogComponent
  ],
  entryComponents: [ServiceinstanceDeleteDialogComponent]
})
export class CloudservicebrokeruiServiceinstanceModule {}
