import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { CloudServiceBrokerUIService } from '../entities/cloud-service-broker-ui/cloud-service-broker-ui.service';
import { ICloudServiceBrokerUI, CloudServiceBrokerUI } from 'app/shared/model/cloud-service-broker-ui.model';
import { Account } from 'app/core/user/account.model';
import { HttpResponse } from '@angular/common/http';
@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  authSubscription?: Subscription;
  catalogServices = [];
  cloudServiceBrokerUIS?: ICloudServiceBrokerUI[];
  selectedcloudServiceBroker: ICloudServiceBrokerUI;

  constructor(
    private accountService: AccountService,
    protected cloudServiceBrokerUIService: CloudServiceBrokerUIService,
    private loginModalService: LoginModalService,
    private router: Router
  ) {
    this.selectedcloudServiceBroker = new CloudServiceBrokerUI();
  }

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
    this.loadAll();
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  loadAll(): void {
    this.cloudServiceBrokerUIService
      .query()
      .subscribe((res: HttpResponse<ICloudServiceBrokerUI[]>) => (this.cloudServiceBrokerUIS = res.body || []));
  }

  login(): void {
    this.loadAll(); // tmp fix to load brokers for login request..
    this.loginModalService.open();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  createinstance(serviceId: string, serviceName: string, planId: string, planName: string) {
    console.log('createinstance');
    console.log(this.selectedcloudServiceBroker.id);
    console.log(this.selectedcloudServiceBroker.brokerName);
    this.router.navigateByUrl('/serviceinstance', {
      state: {
        data: {
          id: serviceId,
          name: serviceName,
          planid: planId,
          planname: planName,
          brokername: this.selectedcloudServiceBroker.brokerName,
          brokerid: this.selectedcloudServiceBroker.id
        }
      }
    });
  }

  selectbroker(cloudServiceBrokerUI: ICloudServiceBrokerUI): void {
    if (cloudServiceBrokerUI.id !== undefined) {
      console.log(cloudServiceBrokerUI);
      this.selectedcloudServiceBroker = cloudServiceBrokerUI;
      this.cloudServiceBrokerUIService.getBrokerCatalog(cloudServiceBrokerUI.id).subscribe((data: any) => {
        console.log(data);
        this.catalogServices = data;
      });
    }
  }
}
