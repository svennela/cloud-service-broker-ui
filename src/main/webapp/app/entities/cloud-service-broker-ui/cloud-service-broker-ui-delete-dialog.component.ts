import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICloudServiceBrokerUI } from 'app/shared/model/cloud-service-broker-ui.model';
import { CloudServiceBrokerUIService } from './cloud-service-broker-ui.service';

@Component({
  templateUrl: './cloud-service-broker-ui-delete-dialog.component.html'
})
export class CloudServiceBrokerUIDeleteDialogComponent {
  cloudServiceBrokerUI?: ICloudServiceBrokerUI;

  constructor(
    protected cloudServiceBrokerUIService: CloudServiceBrokerUIService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cloudServiceBrokerUIService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cloudServiceBrokerUIListModification');
      this.activeModal.close();
    });
  }
}
