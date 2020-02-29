import { IDisciplineType } from 'app/shared/model/discipline-type.model';
import { IDirection } from 'app/shared/model/direction.model';
import { IIdentity } from 'app/shared/model/identity.model';
import { Semester } from 'app/shared/model/enumerations/semester.model';

export interface IDiscipline {
  id?: number;
  code?: string;
  department?: string;
  name?: string;
  hoursForLecture?: number;
  hoursForWorkshop?: number;
  hoursForExercise?: number;
  semester?: Semester;
  numberOfStudents?: number;
  incomingTest?: boolean;
  type?: IDisciplineType;
  approproateFors?: IDirection[];
  lectos?: IIdentity[];
}

export class Discipline implements IDiscipline {
  constructor(
    public id?: number,
    public code?: string,
    public department?: string,
    public name?: string,
    public hoursForLecture?: number,
    public hoursForWorkshop?: number,
    public hoursForExercise?: number,
    public semester?: Semester,
    public numberOfStudents?: number,
    public incomingTest?: boolean,
    public type?: IDisciplineType,
    public approproateFors?: IDirection[],
    public lectos?: IIdentity[]
  ) {
    this.incomingTest = this.incomingTest || false;
  }
}
