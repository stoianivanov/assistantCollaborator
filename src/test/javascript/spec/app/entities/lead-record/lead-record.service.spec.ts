import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LeadRecordService } from 'app/entities/lead-record/lead-record.service';
import { ILeadRecord, LeadRecord } from 'app/shared/model/lead-record.model';
import { CourseType } from 'app/shared/model/enumerations/course-type.model';

describe('Service Tests', () => {
  describe('LeadRecord Service', () => {
    let injector: TestBed;
    let service: LeadRecordService;
    let httpMock: HttpTestingController;
    let elemDefault: ILeadRecord;
    let expectedResult: ILeadRecord | ILeadRecord[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LeadRecordService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new LeadRecord(0, 0, 0, 0, 0, CourseType.LECTURE);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a LeadRecord', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new LeadRecord()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a LeadRecord', () => {
        const returnedFromService = Object.assign(
          {
            maxHoursForLecture: 1,
            maxHoursForExercise: 1,
            maxHoursForWorkshop: 1,
            course: 1,
            type: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of LeadRecord', () => {
        const returnedFromService = Object.assign(
          {
            maxHoursForLecture: 1,
            maxHoursForExercise: 1,
            maxHoursForWorkshop: 1,
            course: 1,
            type: 'BBBBBB'
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

      it('should delete a LeadRecord', () => {
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
