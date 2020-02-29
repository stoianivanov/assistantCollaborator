export interface IDisciplineType {
  id?: number;
  type?: string;
}

export class DisciplineType implements IDisciplineType {
  constructor(public id?: number, public type?: string) {}
}
