// Importez of depuis rxjs
import { of } from 'rxjs';
// Assurez-vous que ces importations sont correctes par rapport à votre structure de projet
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { MeComponent } from './me.component'; // Assurez-vous que le chemin est correct
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SessionService } from '../../services/session.service'; // Assurez-vous que le chemin est correct

import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let userService: UserService;
  let matSnackBar: MatSnackBar;
  let router: Router;
  let sessionService: SessionService;

  beforeEach(async () => {
    const userServiceMock = {
      getById: jest.fn().mockReturnValue(of({id: 1, firstName: 'Test', lastName: 'User', email: 'test@example.com', admin: false, password: 'password', createdAt: new Date()})),
      delete: jest.fn().mockReturnValue(of({}))
    };

    const matSnackBarMock = {
      open: jest.fn()
    };

    const routerMock = {
      navigate: jest.fn()
    };

    const mockSessionService = {
      sessionInformation: {
        admin: true,
        id: 1,
        email: "testuser@example.com",
        lastName: "User",
        firstName: "Test",
        password: "password", 
        createdAt: new Date(),
      },
      logOut: jest.fn(),
      $isLogged: jest.fn(() => of(true))
    };

    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatCardModule, // Ajoutez ceci
        MatIconModule, // Ajoutez ceci
      ],
      providers: [
        { provide: UserService, useValue: userServiceMock },
        { provide: MatSnackBar, useValue: matSnackBarMock },
        { provide: Router, useValue: routerMock },
        { provide: SessionService, useValue: mockSessionService } // Assurez-vous que mockSessionService est correctement défini
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    userService = TestBed.inject(UserService);
    matSnackBar = TestBed.inject(MatSnackBar);
    router = TestBed.inject(Router);
    sessionService = TestBed.inject(SessionService);
    fixture.detectChanges();
  });

  it('should load user information on init', () => {
    expect(userService.getById).toHaveBeenCalledWith('1');
    expect(component.user).toEqual({
      id: 1,
      firstName: 'Test',
      lastName: 'User',
      email: 'test@example.com',
      admin: false,
      password: 'password',
      createdAt: expect.any(Date),
     });
    });

  it('should navigate back on back()', () => {
    const spy = jest.spyOn(window.history, 'back');
    component.back();
    expect(spy).toHaveBeenCalled();
  });

  it('should delete account, show snackbar, logout, and navigate on delete()', () => {
    component.delete();
    expect(userService.delete).toHaveBeenCalledWith('1');
    expect(matSnackBar.open).toHaveBeenCalledWith("Your account has been deleted !", 'Close', { duration: 3000 });
    expect(sessionService.logOut).toHaveBeenCalled(); // Assurez-vous que logOut soit espionné si nécessaire
    expect(router.navigate).toHaveBeenCalledWith(['/']);
  });
});
