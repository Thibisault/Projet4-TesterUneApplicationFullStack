package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SignupRequestTest {

    private LocalValidatorFactoryBean validator;

    private LocalValidatorFactoryBean createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }

    @BeforeEach
    void setUp() {
        validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
    }

    @Test
    void validSignupRequest() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("password123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidEmailSignupRequest() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("notanemail");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("password123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        assertEquals(1, violations.size());
        assertEquals("doit être une adresse électronique syntaxiquement correcte", violations.iterator().next().getMessage());
    }

    @Test
    void emptyFieldsSignupRequest() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("");
        signupRequest.setFirstName("");
        signupRequest.setLastName("");
        signupRequest.setPassword("");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        assertEquals(7, violations.size());
    }

    @Test
    void firstNameViolations() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("user@example.com");
        signupRequest.setFirstName("J");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("password123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validateProperty(signupRequest, "firstName");
        assertFalse(violations.isEmpty());
        assertEquals("la taille doit être comprise entre 3 et 20", violations.iterator().next().getMessage());
    }

    @Test
    void lastNameViolations() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("user@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("D");
        signupRequest.setPassword("password123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validateProperty(signupRequest, "lastName");
        assertFalse(violations.isEmpty());
        assertEquals("la taille doit être comprise entre 3 et 20", violations.iterator().next().getMessage());
    }

    @Test
    void passwordViolations() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("user@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("pass");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validateProperty(signupRequest, "password");
        assertFalse(violations.isEmpty());
        assertEquals("la taille doit être comprise entre 6 et 40", violations.iterator().next().getMessage());
    }

    @Test
    void settersAndGetters() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("newuser@example.com");
        signupRequest.setFirstName("Jane");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("newpassword123");

        assertEquals("newuser@example.com", signupRequest.getEmail());
        assertEquals("Jane", signupRequest.getFirstName());
        assertEquals("Doe", signupRequest.getLastName());
        assertEquals("newpassword123", signupRequest.getPassword());
    }

    @Test
    void testEqualsAndHashCode() {
        SignupRequest signupRequest1 = new SignupRequest();
        signupRequest1.setEmail("user@example.com");
        signupRequest1.setFirstName("John");
        signupRequest1.setLastName("Doe");
        signupRequest1.setPassword("password123");

        SignupRequest signupRequest2 = new SignupRequest();
        signupRequest2.setEmail("user@example.com");
        signupRequest2.setFirstName("John");
        signupRequest2.setLastName("Doe");
        signupRequest2.setPassword("password123");

        assertEquals(signupRequest1, signupRequest2);
        assertEquals(signupRequest1.hashCode(), signupRequest2.hashCode());
    }

    @Test
    void testToString() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("user@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("password123");

        String expected = "SignupRequest(email=user@example.com, firstName=John, lastName=Doe, password=password123)";
        assertTrue(signupRequest.toString().contains(expected));
    }
}
