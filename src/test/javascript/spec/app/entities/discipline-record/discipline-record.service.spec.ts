import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DisciplineRecordService } from 'app/entities/discipline-record/discipline-record.service';
import { IDisciplineRecord, DisciplineRecord } from 'app/shared/model/discipline-record.model';

describe('Service Tests', () => {
  describe('DisciplineRecord Service', () => {
    let injector: TestBed;
    let service: DisciplineRecordService;
    let httpMock: HttpTestingController;
    let elemDefault: IDisciplineRecord;
    let expectedResult: IDisciplineRecord | IDisciplineRecord[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DisciplineRecordService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DisciplineRecord(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DisciplineRecord', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DisciplineRecord()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DisciplineRecord', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            department: 'BBBBBB',
            name: 'BBBBBB',
            form: 'BBBBBB',
            course: 1,
            stream: 1,
            group: 1,
            hoursForLecture: 1,
            hoursForWorkshop: 1,
            hoursForExercise: 1,
            numberOfStudents: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DisciplineRecord', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            department: 'BBBBBB',
            name: 'BBBBBB',
            form: 'BBBBBB',
            course: 1,
            stream: 1,
            group: 1,
            hoursForLecture: 1,
            hoursForWorkshop: 1,
            hoursForExercise: 1,
            numberOfStudents: 1
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

      it('should delete a DisciplineRecord', () => {
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
