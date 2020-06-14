import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDisciplineRecord } from 'app/shared/model/discipline-record.model';

type EntityResponseType = HttpResponse<IDisciplineRecord>;
type EntityArrayResponseType = HttpResponse<IDisciplineRecord[]>;

@Injectable({ providedIn: 'root' })
export class DisciplineRecordService {
  public resourceUrl = SERVER_API_URL + 'api/discipline-records';

  constructor(protected http: HttpClient) {}

  create(disciplineRecord: IDisciplineRecord): Observable<EntityResponseType> {
    return this.http.post<IDisciplineRecord>(this.resourceUrl, disciplineRecord, { observe: 'response' });
  }

  update(disciplineRecord: IDisciplineRecord): Observable<EntityResponseType> {
    return this.http.put<IDisciplineRecord>(this.resourceUrl, disciplineRecord, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDisciplineRecord>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDisciplineRecord[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
