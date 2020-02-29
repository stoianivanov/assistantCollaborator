import { IDiscipline } from 'app/shared/model/discipline.model';
import { CourseType } from 'app/shared/model/enumerations/course-type.model';

export interface ILeadRecord {
  id?: number;
  maxHoursForLecture?: number;
  maxHoursForExercise?: number;
  maxHoursForWorkshop?: number;
  course?: number;
  type?: CourseType;
  assitent?: IDiscipline;
}

export class LeadRecord implements ILeadRecord {
  constructor(
    public id?: number,
    public maxHoursForLecture?: number,
    public maxHoursForExercise?: number,
    public maxHoursForWorkshop?: number,
    public course?: number,
    public type?: CourseType,
    public assitent?: IDiscipline
  ) {}
}
