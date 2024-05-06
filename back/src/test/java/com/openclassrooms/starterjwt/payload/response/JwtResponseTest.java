package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JwtResponseTest {

    @Test
    void jwtResponseGettersAndSetters() {
        String token = "token";
        String type = "Bearer";
        Long id = 1L;
        String username = "username";
        String firstName = "firstName";
        String lastName = "lastName";
        Boolean admin = true;

        JwtResponse jwtResponse = new JwtResponse(token, id, username, firstName, lastName, admin);

        assertEquals(token, jwtResponse.getToken());
        assertEquals(type, jwtResponse.getType());
        assertEquals(id, jwtResponse.getId());
        assertEquals(username, jwtResponse.getUsername());
        assertEquals(firstName, jwtResponse.getFirstName());
        assertEquals(lastName, jwtResponse.getLastName());
        assertEquals(admin, jwtResponse.getAdmin());

        String newToken = "newToken";
        Long newId = 2L;
        String newUsername = "newUsername";
        String newFirstName = "newFirstName";
        String newLastName = "newLastName";
        Boolean newAdmin = false;

        jwtResponse.setToken(newToken);
        jwtResponse.setId(newId);
        jwtResponse.setUsername(newUsername);
        jwtResponse.setFirstName(newFirstName);
        jwtResponse.setLastName(newLastName);
        jwtResponse.setAdmin(newAdmin);

        assertEquals(newToken, jwtResponse.getToken());
        assertEquals(newId, jwtResponse.getId());
        assertEquals(newUsername, jwtResponse.getUsername());
        assertEquals(newFirstName, jwtResponse.getFirstName());
        assertEquals(newLastName, jwtResponse.getLastName());
        assertEquals(newAdmin, jwtResponse.getAdmin());
    }
}
