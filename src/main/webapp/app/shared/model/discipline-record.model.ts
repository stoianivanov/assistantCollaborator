import { IClassType } from 'app/shared/model/class-type.model';
import { IDiscipline } from 'app/shared/model/discipline.model';
import { IIdentity } from 'app/shared/model/identity.model';
import { IDirection } from 'app/shared/model/direction.model';

export interface IDisciplineRecord {
  id?: number;
  code?: string;
  department?: string;
  name?: string;
  form?: string;
  course?: number;
  stream?: number;
  group?: number;
  hoursForLecture?: number;
  hoursForWorkshop?: number;
  hoursForExercise?: number;
  numberOfStudents?: number;
  classType?: IClassType;
  disciplines?: IDiscipline[];
  lectos?: IIdentity[];
  directions?: IDirection[];
}

export class DisciplineRecord implements IDisciplineRecord {
  constructor(
    public id?: number,
    public code?: string,
    public department?: string,
    public name?: string,
    public form?: string,
    public course?: number,
    public stream?: number,
    public group?: number,
    public hoursForLecture?: number,
    public hoursForWorkshop?: number,
    public hoursForExercise?: number,
    public numberOfStudents?: number,
    public classType?: IClassType,
    public disciplines?: IDiscipline[],
    public lectos?: IIdentity[],
    public directions?: IDirection[]
  ) {}
}
