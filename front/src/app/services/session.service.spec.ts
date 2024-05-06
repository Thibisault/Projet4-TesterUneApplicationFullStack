import { TestBed, fakeAsync, tick } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionService } from './session.service';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

describe('SessionService', () => {
  let service: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  it('should have correct initial values', () => {
    expect(service.isLogged).toBeFalsy();
    expect(service.sessionInformation).toBeUndefined();
  });

  it('should log in and set session information', () => {
    const sessionInfo: SessionInformation = {
      token: 'test_token',
      type: 'test_type',
      id: 1,
      username: 'testuser',
      firstName: 'Test',
      lastName: 'User',
      admin: false
    };
    service.logIn(sessionInfo);

    expect(service.isLogged).toBeTruthy();
    expect(service.sessionInformation).toEqual(sessionInfo);
  });

  it('should log out and clear session information', () => {
    const sessionInfo: SessionInformation = {
      token: 'test_token',
      type: 'test_type',
      id: 1,
      username: 'testuser',
      firstName: 'Test',
      lastName: 'User',
      admin: false
    };
    service.logIn(sessionInfo);
    service.logOut();

    expect(service.isLogged).toBeFalsy();
    expect(service.sessionInformation).toBeUndefined();
  });

  it('should emit current isLogged value on $isLogged', fakeAsync(() => {
    let value: boolean = false; 
    service.$isLogged().subscribe(v => value = v); 
    service.logIn({ 
      token: 'test_token',
      type: 'test_type',
      id: 1,
      username: 'testuser',
      firstName: 'Test',
      lastName: 'User',
      admin: false
    });
    tick();
    expect(value).toBeTruthy(); 
  }));
  
});