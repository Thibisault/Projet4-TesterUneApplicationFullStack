import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { expect } from '@jest/globals';

import { TeacherService } from './teacher.service';
import { Teacher } from '../interfaces/teacher.interface';
import { HttpErrorResponse } from '@angular/common/http';

describe('TeacherService', () => {
  let service: TeacherService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TeacherService]
    });
    service = TestBed.inject(TeacherService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify(); // Vérifie qu'aucune requête n'est en attente
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should retrieve all teachers', () => {
    const mockTeachers: Teacher[] = [
      { id: 1, lastName: 'Doe', firstName: 'John', createdAt: new Date(), updatedAt: new Date() },
      { id: 2, lastName: 'Smith', firstName: 'Anna', createdAt: new Date(), updatedAt: new Date() }
    ];

    service.all().subscribe(teachers => {
      expect(teachers.length).toBe(2);
      expect(teachers).toEqual(mockTeachers);
    });

    const req = httpTestingController.expectOne('api/teacher');
    expect(req.request.method).toEqual('GET');
    req.flush(mockTeachers); // Simule une réponse HTTP avec mockTeachers
  });

  it('should retrieve detail of a teacher', () => {
    const mockTeacher: Teacher = { id: 1, lastName: 'Doe', firstName: 'John', createdAt: new Date(), updatedAt: new Date() };

    service.detail('1').subscribe(teacher => {
      expect(teacher).toEqual(mockTeacher);
    });

    const req = httpTestingController.expectOne('api/teacher/1');
    expect(req.request.method).toEqual('GET');
    req.flush(mockTeacher); // Simule une réponse HTTP avec mockTeacher
  });

  it('should handle errors for all()', () => {
    const errorMsg = 'Failed to load teachers';
    service.all().subscribe(
      () => fail('should have failed with the 500 error'),
      (error: HttpErrorResponse) => {
        expect(error.status).toEqual(500);
        expect(error.message).toContain(errorMsg);
      }
    );
  
    const req = httpTestingController.expectOne('api/teacher');
    req.flush(errorMsg, { status: 500, statusText: 'Server Error' });
  });
  
  it('should handle errors for detail()', () => {
    const errorMsg = 'Failed to load teacher detail';
    service.detail('1').subscribe(
      () => fail('should have failed with the 404 error'),
      (error: HttpErrorResponse) => {
        expect(error.status).toEqual(404);
        expect(error.message).toContain(errorMsg);
      }
    );
  
    const req = httpTestingController.expectOne('api/teacher/1');
    req.flush(errorMsg, { status: 404, statusText: 'Not Found' });
  });
  
});
