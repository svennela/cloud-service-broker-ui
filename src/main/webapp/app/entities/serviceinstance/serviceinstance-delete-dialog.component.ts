import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceinstance } from 'app/shared/model/serviceinstance.model';
import { ServiceinstanceService } from './serviceinstance.service';

@Component({
  templateUrl: './serviceinstance-delete-dialog.component.html'
})
export class ServiceinstanceDeleteDialogComponent {
  serviceinstance?: IServiceinstance;

  constructor(
    protected serviceinstanceService: ServiceinstanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.serviceinstanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('serviceinstanceListModification');
      this.activeModal.close();
    });
  }
}
