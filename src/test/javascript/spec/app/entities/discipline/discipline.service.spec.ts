import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DisciplineService } from 'app/entities/discipline/discipline.service';
import { IDiscipline, Discipline } from 'app/shared/model/discipline.model';
import { Semester } from 'app/shared/model/enumerations/semester.model';

describe('Service Tests', () => {
  describe('Discipline Service', () => {
    let injector: TestBed;
    let service: DisciplineService;
    let httpMock: HttpTestingController;
    let elemDefault: IDiscipline;
    let expectedResult: IDiscipline | IDiscipline[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DisciplineService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Discipline(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, 0, Semester.SUMMER, 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Discipline', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Discipline()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Discipline', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            department: 'BBBBBB',
            name: 'BBBBBB',
            hoursForLecture: 1,
            hoursForWorkshop: 1,
            hoursForExercise: 1,
            semester: 'BBBBBB',
            numberOfStudents: 1,
            incomingTest: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Discipline', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            department: 'BBBBBB',
            name: 'BBBBBB',
            hoursForLecture: 1,
            hoursForWorkshop: 1,
            hoursForExercise: 1,
            semester: 'BBBBBB',
            numberOfStudents: 1,
            incomingTest: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Discipline', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
