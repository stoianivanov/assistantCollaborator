import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDisciplineType } from 'app/shared/model/discipline-type.model';

type EntityResponseType = HttpResponse<IDisciplineType>;
type EntityArrayResponseType = HttpResponse<IDisciplineType[]>;

@Injectable({ providedIn: 'root' })
export class DisciplineTypeService {
  public resourceUrl = SERVER_API_URL + 'api/discipline-types';

  constructor(protected http: HttpClient) {}

  create(disciplineType: IDisciplineType): Observable<EntityResponseType> {
    return this.http.post<IDisciplineType>(this.resourceUrl, disciplineType, { observe: 'response' });
  }

  update(disciplineType: IDisciplineType): Observable<EntityResponseType> {
    return this.http.put<IDisciplineType>(this.resourceUrl, disciplineType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDisciplineType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDisciplineType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
