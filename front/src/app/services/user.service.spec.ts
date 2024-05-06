import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { expect } from '@jest/globals';

import { UserService } from './user.service';
import { User } from '../interfaces/user.interface';

describe('UserService', () => {
  let service: UserService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService]
    });
    service = TestBed.inject(UserService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    // Assurez-vous qu'il n'y a pas de requêtes en attente à la fin
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should retrieve a user by ID', () => {
    const mockUser: User = {
      id: 1,
      email: 'test@example.com',
      lastName: 'Doe',
      firstName: 'John',
      admin: false,
      password: 'password',
      createdAt: new Date(),
      updatedAt: new Date()
    };

    service.getById('1').subscribe(user => {
      expect(user).toEqual(mockUser);
    });

    const req = httpTestingController.expectOne('api/user/1');
    expect(req.request.method).toEqual('GET');
    req.flush(mockUser); // Simuler une réponse avec mockUser
  });

  it('should delete a user by ID', () => {
    service.delete('1').subscribe(response => {
      expect(response).toEqual({}); // S'attendre à une réponse vide pour la suppression
    });

    const req = httpTestingController.expectOne('api/user/1');
    expect(req.request.method).toEqual('DELETE');
    req.flush({}); // Simuler une réponse de suppression
  });
});
