import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CloudservicebrokeruiTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { CloudServiceBrokerUIDeleteDialogComponent } from 'app/entities/cloud-service-broker-ui/cloud-service-broker-ui-delete-dialog.component';
import { CloudServiceBrokerUIService } from 'app/entities/cloud-service-broker-ui/cloud-service-broker-ui.service';

describe('Component Tests', () => {
  describe('CloudServiceBrokerUI Management Delete Component', () => {
    let comp: CloudServiceBrokerUIDeleteDialogComponent;
    let fixture: ComponentFixture<CloudServiceBrokerUIDeleteDialogComponent>;
    let service: CloudServiceBrokerUIService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CloudservicebrokeruiTestModule],
        declarations: [CloudServiceBrokerUIDeleteDialogComponent]
      })
        .overrideTemplate(CloudServiceBrokerUIDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CloudServiceBrokerUIDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CloudServiceBrokerUIService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
