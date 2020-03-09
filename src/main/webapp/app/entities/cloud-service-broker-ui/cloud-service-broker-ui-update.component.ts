import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICloudServiceBrokerUI, CloudServiceBrokerUI } from 'app/shared/model/cloud-service-broker-ui.model';
import { CloudServiceBrokerUIService } from './cloud-service-broker-ui.service';

@Component({
  selector: 'jhi-cloud-service-broker-ui-update',
  templateUrl: './cloud-service-broker-ui-update.component.html'
})
export class CloudServiceBrokerUIUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    brokerName: [],
    brokerUrl: [],
    userName: [],
    password: [],
    env: []
  });

  constructor(
    protected cloudServiceBrokerUIService: CloudServiceBrokerUIService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cloudServiceBrokerUI }) => {
      this.updateForm(cloudServiceBrokerUI);
    });
  }

  updateForm(cloudServiceBrokerUI: ICloudServiceBrokerUI): void {
    this.editForm.patchValue({
      id: cloudServiceBrokerUI.id,
      brokerName: cloudServiceBrokerUI.brokerName,
      brokerUrl: cloudServiceBrokerUI.brokerUrl,
      userName: cloudServiceBrokerUI.userName,
      password: cloudServiceBrokerUI.password,
      env: cloudServiceBrokerUI.env
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cloudServiceBrokerUI = this.createFromForm();
    if (cloudServiceBrokerUI.id !== undefined) {
      this.subscribeToSaveResponse(this.cloudServiceBrokerUIService.update(cloudServiceBrokerUI));
    } else {
      this.subscribeToSaveResponse(this.cloudServiceBrokerUIService.create(cloudServiceBrokerUI));
    }
  }

  private createFromForm(): ICloudServiceBrokerUI {
    return {
      ...new CloudServiceBrokerUI(),
      id: this.editForm.get(['id'])!.value,
      brokerName: this.editForm.get(['brokerName'])!.value,
      brokerUrl: this.editForm.get(['brokerUrl'])!.value,
      userName: this.editForm.get(['userName'])!.value,
      password: this.editForm.get(['password'])!.value,
      env: this.editForm.get(['env'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICloudServiceBrokerUI>>): void {
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
