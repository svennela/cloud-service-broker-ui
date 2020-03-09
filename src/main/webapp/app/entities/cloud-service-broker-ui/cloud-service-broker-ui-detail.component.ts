import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICloudServiceBrokerUI } from 'app/shared/model/cloud-service-broker-ui.model';

@Component({
  selector: 'jhi-cloud-service-broker-ui-detail',
  templateUrl: './cloud-service-broker-ui-detail.component.html'
})
export class CloudServiceBrokerUIDetailComponent implements OnInit {
  cloudServiceBrokerUI: ICloudServiceBrokerUI | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cloudServiceBrokerUI }) => (this.cloudServiceBrokerUI = cloudServiceBrokerUI));
  }

  previousState(): void {
    window.history.back();
  }
}
