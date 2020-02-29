import { IDiscipline } from 'app/shared/model/discipline.model';

export interface IDirection {
  id?: number;
  name?: string;
  code?: string;
  appropriate?: string;
  discipline?: IDiscipline;
}

export class Direction implements IDirection {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string,
    public appropriate?: string,
    public discipline?: IDiscipline
  ) {}
}
