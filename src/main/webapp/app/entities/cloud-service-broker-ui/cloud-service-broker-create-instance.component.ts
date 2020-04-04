import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ICloudServiceBrokerUI } from 'app/shared/model/cloud-service-broker-ui.model';
import { IServiceinstance } from 'app/shared/model/serviceinstance.model';
import { Serviceinstance } from 'app/shared/model/serviceinstance.model';
import { CloudServiceBrokerUIService } from './cloud-service-broker-ui.service';
import { ServiceinstanceService } from '../serviceinstance/serviceinstance.service';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { JhiEventManager } from 'ng-jhipster';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-cloud-service-broker-create-instance',
  templateUrl: './cloud-service-broker-create-instance.component.html'
})
export class CloudServiceBrokerCreateInstanceComponent implements OnInit, OnDestroy {
  cloudServiceBrokerUI: ICloudServiceBrokerUI | null = null;
  serviceInstance: IServiceinstance = new Serviceinstance();
  serviceinstances?: IServiceinstance[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private router: Router,
    protected modalService: NgbModal,
    protected serviceinstanceService: ServiceinstanceService,
    protected eventManager: JhiEventManager,
    protected cloudServiceBrokerUIService: CloudServiceBrokerUIService
  ) {}

  ngOnInit(): void {
    console.log(history.state);
    if (history.state.data != null) {
      this.serviceInstance.planId = history.state.data.planid;
      this.serviceInstance.serviceId = history.state.data.id;
      this.serviceInstance.planName = history.state.data.planname;
      this.serviceInstance.serviceName = history.state.data.name;
      this.serviceInstance.brokerName = history.state.data.brokername;
      this.serviceInstance.borkerId = history.state.data.brokerid;
    }
  }

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.serviceinstanceService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IServiceinstance[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  trackId(index: number, item: IServiceinstance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInServiceinstances(): void {
    this.eventSubscriber = this.eventManager.subscribe('serviceinstanceListModification', () => this.loadPage());
  }
  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  protected onSuccess(data: IServiceinstance[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/serviceinstance'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.serviceinstances = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }

  previousState(): void {
    window.history.back();
  }
}
