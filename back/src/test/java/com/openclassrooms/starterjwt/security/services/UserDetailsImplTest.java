package com.openclassrooms.starterjwt.security.services;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserDetailsImplTest {

    @Test
    void getAuthorities_returnsEmptyCollection() {
        UserDetailsImpl userDetails = UserDetailsImpl.builder().build();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertTrue(authorities.isEmpty());
    }

    @Test
    void isAccountNonExpired_returnsTrue() {
        UserDetailsImpl userDetails = UserDetailsImpl.builder().build();
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked_returnsTrue() {
        UserDetailsImpl userDetails = UserDetailsImpl.builder().build();
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired_returnsTrue() {
        UserDetailsImpl userDetails = UserDetailsImpl.builder().build();
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void isEnabled_returnsTrue() {
        UserDetailsImpl userDetails = UserDetailsImpl.builder().build();
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void testEquals_withSameId() {
        UserDetailsImpl userDetails1 = UserDetailsImpl.builder().id(1L).build();
        UserDetailsImpl userDetails2 = UserDetailsImpl.builder().id(1L).build();
        assertEquals(userDetails1, userDetails2);
    }

    @Test
    void testEquals_withDifferentId() {
        UserDetailsImpl userDetails1 = UserDetailsImpl.builder().id(1L).build();
        UserDetailsImpl userDetails2 = UserDetailsImpl.builder().id(2L).build();
        assertNotEquals(userDetails1, userDetails2);
    }

    @Test
    void builder_createsNonNullInstance() {
        assertNotNull(UserDetailsImpl.builder().build());
    }

    @Test
    void getters_returnCorrectValues() {
        String username = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String password = "password";
        Boolean admin = true;
        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .admin(admin)
                .build();

        assertEquals(username, userDetails.getUsername());
        assertEquals(firstName, userDetails.getFirstName());
        assertEquals(lastName, userDetails.getLastName());
        assertEquals(password, userDetails.getPassword());
        assertEquals(admin, userDetails.getAdmin());
    }
}
