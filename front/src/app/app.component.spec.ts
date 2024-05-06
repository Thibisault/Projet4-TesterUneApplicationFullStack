import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';

import { AppComponent } from './app.component';

import { of } from 'rxjs';
import { Router } from '@angular/router';
import { AuthService } from './features/auth/services/auth.service';
import { SessionService } from './services/session.service';


describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  let sessionService: SessionService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule, MatToolbarModule],
      declarations: [AppComponent],
      providers: [AuthService, SessionService]
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    sessionService = TestBed.inject(SessionService);
    router = TestBed.inject(Router);
  });

  it('should be logged in if session is active', (done) => { // Notez l'utilisation du callback `done`
    jest.spyOn(sessionService, '$isLogged').mockReturnValue(of(true)); // Simule que l'utilisateur est connecté
  
    component.$isLogged().subscribe(value => {
      expect(value).toBe(true);
      done(); // Indique à Jest que le test est terminé après l'assertion asynchrone
    });
  });
  
  it('should logout and navigate to home', () => {
    const logOutSpy = jest.spyOn(sessionService, 'logOut').mockImplementation(() => {});
    const navigateSpy = jest.spyOn(router, 'navigate');

    component.logout();

    expect(logOutSpy).toHaveBeenCalled();
    expect(navigateSpy).toHaveBeenCalledWith(['']);
  });
});
