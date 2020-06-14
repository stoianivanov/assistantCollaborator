import { IDisciplineRecord } from 'app/shared/model/discipline-record.model';

export interface IDiscipline {
  id?: number;
  description?: string;
  disciplineType?: string;
  disciplineRecord?: IDisciplineRecord;
}

export class Discipline implements IDiscipline {
  constructor(
    public id?: number,
    public description?: string,
    public disciplineType?: string,
    public disciplineRecord?: IDisciplineRecord
  ) {}
}
