import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IServiceinstance } from 'app/shared/model/serviceinstance.model';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICloudServiceBrokerUI } from 'app/shared/model/cloud-service-broker-ui.model';

type EntityResponseType = HttpResponse<ICloudServiceBrokerUI>;
type EntityArrayResponseType = HttpResponse<ICloudServiceBrokerUI[]>;

@Injectable({ providedIn: 'root' })
export class CloudServiceBrokerUIService {
  public resourceUrl = SERVER_API_URL + 'api/cloud-service-broker-uis';

  constructor(protected http: HttpClient) {}

  create(cloudServiceBrokerUI: ICloudServiceBrokerUI): Observable<EntityResponseType> {
    return this.http.post<ICloudServiceBrokerUI>(this.resourceUrl, cloudServiceBrokerUI, { observe: 'response' });
  }

  update(cloudServiceBrokerUI: ICloudServiceBrokerUI): Observable<EntityResponseType> {
    return this.http.put<ICloudServiceBrokerUI>(this.resourceUrl, cloudServiceBrokerUI, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICloudServiceBrokerUI>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBrokerCatalog(id: number) {
    return this.http.get(`${this.resourceUrl}/catalog/${id}`);
  }

  deleteServiceInstance(id: number) {
    return this.http.get(`${this.resourceUrl}/deleteservice/${id}`);
  }
  createServiceInstance(serviceinstance: IServiceinstance) {
    console.log(serviceinstance);
    return this.http.post<IServiceinstance>(`${this.resourceUrl}/${serviceinstance.borkerId}/serviceinstance/`, serviceinstance, {
      observe: 'response'
    });
  }
  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICloudServiceBrokerUI[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
