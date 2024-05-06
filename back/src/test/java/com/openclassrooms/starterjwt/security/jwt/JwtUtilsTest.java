package com.openclassrooms.starterjwt.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilsTest {

    @MockBean
    private Authentication authentication;

    @Autowired
    @InjectMocks
    private JwtUtils jwtUtils;

    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        userDetails = UserDetailsImpl.builder()
                .username("test@example.com")
                .build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
    }

    @Test
    void generateJwtToken_returnsValidToken() {
        String token = jwtUtils.generateJwtToken(authentication);
        assertNotNull(token);
        assertTrue(jwtUtils.validateJwtToken(token));
    }

    @Test
    void getUserNameFromJwtToken_returnsCorrectUsername() {
        String token = jwtUtils.generateJwtToken(authentication);
        String username = jwtUtils.getUserNameFromJwtToken(token);
        assertEquals("test@example.com", username);
    }

    @Test
    void validateJwtToken_withValidToken_returnsTrue() {
        String token = jwtUtils.generateJwtToken(authentication);
        assertTrue(jwtUtils.validateJwtToken(token));
    }

    @Test
    void validateJwtToken_withInvalidToken_returnsFalse() {
        String invalidToken = "invalid.token";
        assertFalse(jwtUtils.validateJwtToken(invalidToken));
    }
}
