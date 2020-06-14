export interface IClassType {
  id?: number;
  type?: string;
}

export class ClassType implements IClassType {
  constructor(public id?: number, public type?: string) {}
}
