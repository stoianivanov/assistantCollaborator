import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IClassType } from 'app/shared/model/class-type.model';

type EntityResponseType = HttpResponse<IClassType>;
type EntityArrayResponseType = HttpResponse<IClassType[]>;

@Injectable({ providedIn: 'root' })
export class ClassTypeService {
  public resourceUrl = SERVER_API_URL + 'api/class-types';

  constructor(protected http: HttpClient) {}

  create(classType: IClassType): Observable<EntityResponseType> {
    return this.http.post<IClassType>(this.resourceUrl, classType, { observe: 'response' });
  }

  update(classType: IClassType): Observable<EntityResponseType> {
    return this.http.put<IClassType>(this.resourceUrl, classType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IClassType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClassType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
