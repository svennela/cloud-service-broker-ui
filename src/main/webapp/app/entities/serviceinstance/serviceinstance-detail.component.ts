import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceinstance } from 'app/shared/model/serviceinstance.model';

@Component({
  selector: 'jhi-serviceinstance-detail',
  templateUrl: './serviceinstance-detail.component.html'
})
export class ServiceinstanceDetailComponent implements OnInit {
  serviceinstance: IServiceinstance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceinstance }) => (this.serviceinstance = serviceinstance));
  }

  previousState(): void {
    window.history.back();
  }
}
