import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CloudservicebrokeruiTestModule } from '../../../test.module';
import { CloudServiceBrokerUIDetailComponent } from 'app/entities/cloud-service-broker-ui/cloud-service-broker-ui-detail.component';
import { CloudServiceBrokerUI } from 'app/shared/model/cloud-service-broker-ui.model';

describe('Component Tests', () => {
  describe('CloudServiceBrokerUI Management Detail Component', () => {
    let comp: CloudServiceBrokerUIDetailComponent;
    let fixture: ComponentFixture<CloudServiceBrokerUIDetailComponent>;
    const route = ({ data: of({ cloudServiceBrokerUI: new CloudServiceBrokerUI(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CloudservicebrokeruiTestModule],
        declarations: [CloudServiceBrokerUIDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CloudServiceBrokerUIDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CloudServiceBrokerUIDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cloudServiceBrokerUI on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cloudServiceBrokerUI).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
