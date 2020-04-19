import { Injectable } from '@angular/core';
import { ILectorsModel } from 'app/shared/model/lectors.model';

@Injectable({
  providedIn: 'root'
})
export class HonoredLectorService {
  constructor() {}

  readHonoredLectors(fileInput: any): ILectorsModel[] {
    const lectors: ILectorsModel[] = [];

    const reader: FileReader = new FileReader();

    reader.readAsText(fileInput.target.files[0]);

    reader.onload = () => {
      const csv: string = '' + reader.result;
      let headers: string[] = [];
      let allTextLines = csv.split(/\r|\n|\r/);
      headers = allTextLines[0].split(',');

      allTextLines = allTextLines.slice(1);
      let idx = 0;
      for (const row of allTextLines) {
        let obj = {};
        if (row.length > 0) {
          let data: string[] = row.split(',');
          obj = {
            number: ++idx,
            name: data[0],
            scienceDegree: data[1],
            education: data[2],
            job: data[3],
            phoneNumber: data[4],
            email: data[5],
            discipline: data[6],
            disciplineType: data[7],
            disciplineKind: data[8],
            spec: data[9],
            course: data[10],
            form: data[11],
            flow: data[12],
            group: data[13],
            hoursForLecture: data[14],
            hoursForWorkshop: data[15],
            hoursForExercise: data[16],
            numberOfStudents: data[17]
          };
          lectors.push(obj);
        }
      }
    };

    return lectors;
  }
}
