import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CloudservicebrokeruiTestModule } from '../../../test.module';
import { CloudServiceBrokerUIComponent } from 'app/entities/cloud-service-broker-ui/cloud-service-broker-ui.component';
import { CloudServiceBrokerUIService } from 'app/entities/cloud-service-broker-ui/cloud-service-broker-ui.service';
import { CloudServiceBrokerUI } from 'app/shared/model/cloud-service-broker-ui.model';

describe('Component Tests', () => {
  describe('CloudServiceBrokerUI Management Component', () => {
    let comp: CloudServiceBrokerUIComponent;
    let fixture: ComponentFixture<CloudServiceBrokerUIComponent>;
    let service: CloudServiceBrokerUIService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CloudservicebrokeruiTestModule],
        declarations: [CloudServiceBrokerUIComponent]
      })
        .overrideTemplate(CloudServiceBrokerUIComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CloudServiceBrokerUIComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CloudServiceBrokerUIService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CloudServiceBrokerUI(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.cloudServiceBrokerUIS && comp.cloudServiceBrokerUIS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
