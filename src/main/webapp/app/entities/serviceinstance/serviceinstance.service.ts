import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IServiceinstance } from 'app/shared/model/serviceinstance.model';

type EntityResponseType = HttpResponse<IServiceinstance>;
type EntityArrayResponseType = HttpResponse<IServiceinstance[]>;

@Injectable({ providedIn: 'root' })
export class ServiceinstanceService {
  public resourceUrl = SERVER_API_URL + 'api/serviceinstances';

  constructor(protected http: HttpClient) {}

  create(serviceinstance: IServiceinstance): Observable<EntityResponseType> {
    return this.http.post<IServiceinstance>(this.resourceUrl, serviceinstance, { observe: 'response' });
  }

  update(serviceinstance: IServiceinstance): Observable<EntityResponseType> {
    return this.http.put<IServiceinstance>(this.resourceUrl, serviceinstance, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServiceinstance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServiceinstance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
