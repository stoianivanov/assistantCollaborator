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
            name: data[1],
            scienceDegree: data[2],
            education: data[3],
            job: data[4],
            phoneNumber: data[5],
            email: data[6],
            discipline: data[7],
            disciplineType: data[8],
            disciplineKind: data[9],
            spec: data[10],
            course: data[11],
            form: data[12],
            flow: data[13],
            group: data[14],
            hoursForLecture: data[15],
            hoursForWorkshop: data[16],
            hoursForExercise: data[17],
            numberOfStudents: data[18]
          };
          lectors.push(obj);
        }
      }
    };

    return lectors;
  }
}
