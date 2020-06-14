import { IDisciplineRecord } from 'app/shared/model/discipline-record.model';

export interface IDirection {
  id?: number;
  name?: string;
  description?: string;
  disciplineRecords?: IDisciplineRecord[];
}

export class Direction implements IDirection {
  constructor(public id?: number, public name?: string, public description?: string, public disciplineRecords?: IDisciplineRecord[]) {}
}
