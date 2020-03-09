import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CloudServiceBrokerUIService } from 'app/entities/cloud-service-broker-ui/cloud-service-broker-ui.service';
import { ICloudServiceBrokerUI, CloudServiceBrokerUI } from 'app/shared/model/cloud-service-broker-ui.model';

describe('Service Tests', () => {
  describe('CloudServiceBrokerUI Service', () => {
    let injector: TestBed;
    let service: CloudServiceBrokerUIService;
    let httpMock: HttpTestingController;
    let elemDefault: ICloudServiceBrokerUI;
    let expectedResult: ICloudServiceBrokerUI | ICloudServiceBrokerUI[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CloudServiceBrokerUIService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CloudServiceBrokerUI(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CloudServiceBrokerUI', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CloudServiceBrokerUI()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CloudServiceBrokerUI', () => {
        const returnedFromService = Object.assign(
          {
            brokerName: 'BBBBBB',
            brokerUrl: 'BBBBBB',
            userName: 'BBBBBB',
            password: 'BBBBBB',
            env: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CloudServiceBrokerUI', () => {
        const returnedFromService = Object.assign(
          {
            brokerName: 'BBBBBB',
            brokerUrl: 'BBBBBB',
            userName: 'BBBBBB',
            password: 'BBBBBB',
            env: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CloudServiceBrokerUI', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
