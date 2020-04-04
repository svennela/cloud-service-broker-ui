import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IServiceinstance } from 'app/shared/model/serviceinstance.model';
import { Serviceinstance } from 'app/shared/model/serviceinstance.model';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ServiceinstanceService } from './serviceinstance.service';
import { CloudServiceBrokerUIService } from '../cloud-service-broker-ui/cloud-service-broker-ui.service';
@Component({
  selector: 'jhi-serviceinstance',
  templateUrl: './serviceinstance.component.html'
})
export class ServiceinstanceComponent implements OnInit, OnDestroy {
  serviceinstances?: IServiceinstance[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  serviceInstance: IServiceinstance = new Serviceinstance();
  constructor(
    protected serviceinstanceService: ServiceinstanceService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected cloudServiceBrokerUIService: CloudServiceBrokerUIService,
    protected modalService: NgbModal
  ) {}

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

  ngOnInit(): void {
    console.log('-----------------');
    console.log(history.state);
    if (history.state.data != null) {
      this.serviceInstance.planId = history.state.data.planid;
      this.serviceInstance.serviceId = history.state.data.id;
      this.serviceInstance.planName = history.state.data.planname;
      this.serviceInstance.serviceName = history.state.data.name;
      this.serviceInstance.brokerName = history.state.data.brokername;
      this.serviceInstance.borkerId = history.state.data.brokerid;
      this.cloudServiceBrokerUIService.createServiceInstance(this.serviceInstance).subscribe((data1: any) => {
        console.log(data1);
        this.activatedRoute.data.subscribe(data => {
          this.page = data.pagingParams.page;
          this.ascending = data.pagingParams.ascending;
          this.predicate = data.pagingParams.predicate;
          this.ngbPaginationPage = data.pagingParams.page;
          this.loadPage();
        });
        this.registerChangeInServiceinstances();
      });
    } else {
      this.activatedRoute.data.subscribe(data => {
        this.page = data.pagingParams.page;
        this.ascending = data.pagingParams.ascending;
        this.predicate = data.pagingParams.predicate;
        this.ngbPaginationPage = data.pagingParams.page;
        this.loadPage();
      });
      this.registerChangeInServiceinstances();
    }
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IServiceinstance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInServiceinstances(): void {
    this.eventSubscriber = this.eventManager.subscribe('serviceinstanceListModification', () => this.loadPage());
  }

  delete(serviceinstance: IServiceinstance): void {
    console.log(serviceinstance.id);
    if (serviceinstance.id != null)
      this.cloudServiceBrokerUIService.deleteServiceInstance(serviceinstance.id).subscribe((data: any) => {
        console.log(data);
      });
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'desc' : 'asc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IServiceinstance[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/serviceinstance'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'desc' : 'asc')
      }
    });
    this.serviceinstances = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
