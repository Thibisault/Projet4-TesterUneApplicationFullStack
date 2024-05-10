describe('account spec', () => {
  it('show account successfull', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
      },
    })

    cy.intercept('POST', '/api/auth/register', {
    })

    cy.intercept(
      {
        method: 'GET',
        url: '/api/user/1',
      },
      [])
      cy.intercept(
        {
          method: 'DELETE',
          url: '/api/user/1',
        },
        [])
    

    
    cy.intercept('GET', '/api/teacher', {
      statusCode: 200,
      body: [
        { id: 1, lastName: 'pr', firstName: 'yoga', createdAt: new Date(), updatedAt: new Date() }
      ]
    })

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []).as('session')

      cy.intercept(
        {
          method: 'POST',
          url: '/api/session',
        },
        [])

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
    cy.url().should('include', '/sessions'),

    cy.get('span[routerLink=me]').click()
    cy.get('span[routerLink=sessions]').click()
    cy.contains('span', 'Logout').click();

    cy.get('span[routerLink=login]').click()
    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.contains('span', 'Create').click();
    cy.url().should('include', '/sessions/create');

    cy.get('input[formControlName="name"]').type('Session de Yoga');
    cy.get('input[formControlName="date"]').type('2024-04-04');

    cy.get('mat-select[formControlName="teacher_id"]').click();
    cy.get('mat-option').contains('yoga pr').click();

    cy.get('textarea[formControlName="description"]').type('Bonjour.');
    cy.contains('button', 'Save').click();
    cy.url().should('include', '/sessions'),

    cy.contains('span', 'Close').click();



    cy.get('span[routerLink=me]').click()
    cy.contains('mat-icon', 'delete').click();
    cy.contains('span', 'Close').click();

    cy.get('span[routerlink="register"]').click()

    cy.get('input[formControlName="firstName"]').type('Monsieur A');
    cy.get('input[formControlName="lastName"]').type('Mister A');
    cy.get('input[formControlName="email"]').type('a@a');
    cy.get('input[formControlName="password"]').type('MonsieurTestA');
    cy.get('button[type="submit"]').click()

    cy.get('input[formControlName=email]').type("a@a")
    cy.get('input[formControlName=password]').type(`${"MonsieurTestA"}{enter}{enter}`)
    cy.contains('span', 'Create').click();
    cy.url().should('include', '/sessions/create');
    cy.get('span[routerLink=sessions]').click()
    cy.contains('span', 'Logout').click();

    cy.get('span[routerlink="register"]').click()
    cy.url().should('include', '/register');
    cy.get('span[routerLink=login]').click()
    cy.url().should('include', '/login');
    cy.get('span[routerlink="register"]').click()
    cy.url().should('include', '/register');
    cy.get('span[routerLink=login]').click()
    cy.url().should('include', '/login');
    cy.get('input[formControlName=email]').type("a@a")
    cy.get('input[formControlName=password]').type(`${"MonsieurTestA"}{enter}{enter}`)
    cy.url().should('include', '/sessions');
    cy.get('span[routerLink=me]').click()
    cy.url().should('include', '/me');
    cy.get('span[routerLink=sessions]').click()
    cy.url().should('include', '/sessions');
    cy.contains('span', 'Logout').click();
    cy.url().should('include', '');

    cy.get('span[routerLink=login]').click()
    cy.url().should('include', '/login');
    cy.get('input[formControlName=email]').type("a@a")
    cy.get('input[formControlName=password]').type(`${"MonsieurTestA"}{enter}{enter}`)
    cy.url().should('include', '/sessions');
    cy.get('span[routerLink=me]').click()
    cy.url().should('include', '/me');
    cy.contains('mat-icon', 'arrow_back').click();
    cy.url().should('include', '/sessions');

    cy.contains('span', 'Create').click();
    cy.url().should('include', '/sessions/create');
    cy.contains('mat-icon', 'arrow_back').click();
    cy.url().should('include', '/sessions');

  })
});