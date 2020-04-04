import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IServiceinstance, Serviceinstance } from 'app/shared/model/serviceinstance.model';
import { ServiceinstanceService } from './serviceinstance.service';

@Component({
  selector: 'jhi-serviceinstance-update',
  templateUrl: './serviceinstance-update.component.html'
})
export class ServiceinstanceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    instanceId: [],
    name: [],
    serviceId: [],
    planId: [],
    spaceGuid: [],
    organizationGuid: [],
    operationType: [],
    operationId: []
  });

  constructor(
    protected serviceinstanceService: ServiceinstanceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceinstance }) => {
      this.updateForm(serviceinstance);
    });
  }

  updateForm(serviceinstance: IServiceinstance): void {
    this.editForm.patchValue({
      id: serviceinstance.id,
      instanceId: serviceinstance.instanceId,
      name: serviceinstance.name,
      serviceId: serviceinstance.serviceId,
      planId: serviceinstance.planId,
      spaceGuid: serviceinstance.spaceGuid,
      organizationGuid: serviceinstance.organizationGuid,
      operationType: serviceinstance.operationType,
      operationId: serviceinstance.operationId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const serviceinstance = this.createFromForm();
    if (serviceinstance.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceinstanceService.update(serviceinstance));
    } else {
      this.subscribeToSaveResponse(this.serviceinstanceService.create(serviceinstance));
    }
  }

  private createFromForm(): IServiceinstance {
    return {
      ...new Serviceinstance(),
      id: this.editForm.get(['id'])!.value,
      instanceId: this.editForm.get(['instanceId'])!.value,
      name: this.editForm.get(['name'])!.value,
      serviceId: this.editForm.get(['serviceId'])!.value,
      planId: this.editForm.get(['planId'])!.value,
      spaceGuid: this.editForm.get(['spaceGuid'])!.value,
      organizationGuid: this.editForm.get(['organizationGuid'])!.value,
      operationType: this.editForm.get(['operationType'])!.value,
      operationId: this.editForm.get(['operationId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceinstance>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
