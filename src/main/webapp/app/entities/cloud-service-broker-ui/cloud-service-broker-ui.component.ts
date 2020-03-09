import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICloudServiceBrokerUI } from 'app/shared/model/cloud-service-broker-ui.model';
import { CloudServiceBrokerUIService } from './cloud-service-broker-ui.service';
import { CloudServiceBrokerUIDeleteDialogComponent } from './cloud-service-broker-ui-delete-dialog.component';

@Component({
  selector: 'jhi-cloud-service-broker-ui',
  templateUrl: './cloud-service-broker-ui.component.html'
})
export class CloudServiceBrokerUIComponent implements OnInit, OnDestroy {
  cloudServiceBrokerUIS?: ICloudServiceBrokerUI[];
  eventSubscriber?: Subscription;

  constructor(
    protected cloudServiceBrokerUIService: CloudServiceBrokerUIService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.cloudServiceBrokerUIService
      .query()
      .subscribe((res: HttpResponse<ICloudServiceBrokerUI[]>) => (this.cloudServiceBrokerUIS = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCloudServiceBrokerUIS();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICloudServiceBrokerUI): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCloudServiceBrokerUIS(): void {
    this.eventSubscriber = this.eventManager.subscribe('cloudServiceBrokerUIListModification', () => this.loadAll());
  }

  delete(cloudServiceBrokerUI: ICloudServiceBrokerUI): void {
    const modalRef = this.modalService.open(CloudServiceBrokerUIDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cloudServiceBrokerUI = cloudServiceBrokerUI;
  }
}
