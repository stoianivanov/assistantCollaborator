import { IDisciplineRecord } from 'app/shared/model/discipline-record.model';

export interface IIdentity {
  id?: number;
  fullName?: string;
  scienceDegree?: string;
  education?: string;
  job?: string;
  phoneNumber?: string;
  eMail?: string;
  disciplines?: IDisciplineRecord[];
}

export class Identity implements IIdentity {
  constructor(
    public id?: number,
    public fullName?: string,
    public scienceDegree?: string,
    public education?: string,
    public job?: string,
    public phoneNumber?: string,
    public eMail?: string,
    public disciplines?: IDisciplineRecord[]
  ) {}
}
