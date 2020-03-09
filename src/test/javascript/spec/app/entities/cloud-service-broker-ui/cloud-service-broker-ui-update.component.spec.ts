import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CloudservicebrokeruiTestModule } from '../../../test.module';
import { CloudServiceBrokerUIUpdateComponent } from 'app/entities/cloud-service-broker-ui/cloud-service-broker-ui-update.component';
import { CloudServiceBrokerUIService } from 'app/entities/cloud-service-broker-ui/cloud-service-broker-ui.service';
import { CloudServiceBrokerUI } from 'app/shared/model/cloud-service-broker-ui.model';

describe('Component Tests', () => {
  describe('CloudServiceBrokerUI Management Update Component', () => {
    let comp: CloudServiceBrokerUIUpdateComponent;
    let fixture: ComponentFixture<CloudServiceBrokerUIUpdateComponent>;
    let service: CloudServiceBrokerUIService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CloudservicebrokeruiTestModule],
        declarations: [CloudServiceBrokerUIUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CloudServiceBrokerUIUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CloudServiceBrokerUIUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CloudServiceBrokerUIService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CloudServiceBrokerUI(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CloudServiceBrokerUI();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
