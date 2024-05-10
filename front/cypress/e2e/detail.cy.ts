describe('account spec', () => {
    it('show account successful', () => {
      cy.intercept('POST', '/api/auth/login', {
        statusCode: 200,
        body: {
          id: 1,
          username: 'userName',
          firstName: 'firstName',
          lastName: 'lastName',
          admin: true
        },
      });
  
      cy.intercept('POST', '/api/auth/register', {
        statusCode: 201
      });
  
      cy.intercept('GET', '/api/user/1', {
        statusCode: 200,
        body: {}
      });
  
      cy.intercept('DELETE', '/api/user/1', {
        statusCode: 204
      });

      cy.intercept('GET', '/api/teacher', {
        statusCode: 200,
        body: [
          { id: 1, lastName: 'pr', firstName: 'yoga', createdAt: new Date(), updatedAt: new Date() }
        ]
      })
  
      const fakeTeacher = {
        id: 10,
        lastName: 'pr',
        firstName: 'yoga',
        createdAt: new Date(),
        updatedAt: new Date()
      };
      cy.intercept('GET', `/api/teacher/${fakeTeacher.id}`, {
        statusCode: 200,
        body: fakeTeacher
      });
  
      const fakeSession = {
        id: 99,
        name: "Test Session",
        description: "This is a test session.",
        date: new Date('2024-05-15T14:00:00Z'),
        teacher_id: fakeTeacher.id,
        users: [1, 2, 3],
        createdAt: new Date(),
        updatedAt: new Date()
      };
      cy.intercept('GET', `/api/session/${fakeSession.id}`, {
        statusCode: 200,
        body: fakeSession
      });
      cy.intercept('GET', '/api/session', {
        statusCode: 200,
        body: [fakeSession]
      }).as('getSessionList');
  
      cy.visit('/login');
      cy.get('input[formControlName=email]').type("user@example.com");
      cy.get('input[formControlName=password]').type("password{enter}{enter}");
  
      cy.url().should('include', '/sessions');

      cy.contains('button', 'Detail').click();
      cy.contains('arrow_back').click();
      
      cy.contains('button', 'Edit').click();
      cy.get('input[formControlName="name"]').type('Sample Session Name');
      cy.get('input[formControlName="date"]').type('2024-05-20');
      cy.get('mat-select[formControlName="teacher_id"]').click();
      cy.get('mat-option').contains('yoga pr').click();

      cy.get('textarea[formControlName="description"]').type('Bonjour.');
      cy.contains('button', 'Save').click();
      cy.contains('arrow_back').click();
      cy.contains('button', 'Detail').click();
      cy.contains('button', 'Delete').click();

    });
  });
  