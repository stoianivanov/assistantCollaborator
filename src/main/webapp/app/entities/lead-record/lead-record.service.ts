import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILeadRecord } from 'app/shared/model/lead-record.model';

type EntityResponseType = HttpResponse<ILeadRecord>;
type EntityArrayResponseType = HttpResponse<ILeadRecord[]>;

@Injectable({ providedIn: 'root' })
export class LeadRecordService {
  public resourceUrl = SERVER_API_URL + 'api/lead-records';

  constructor(protected http: HttpClient) {}

  create(leadRecord: ILeadRecord): Observable<EntityResponseType> {
    return this.http.post<ILeadRecord>(this.resourceUrl, leadRecord, { observe: 'response' });
  }

  update(leadRecord: ILeadRecord): Observable<EntityResponseType> {
    return this.http.put<ILeadRecord>(this.resourceUrl, leadRecord, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILeadRecord>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILeadRecord[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
