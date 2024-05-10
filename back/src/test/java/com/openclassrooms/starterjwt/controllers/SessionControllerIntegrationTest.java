package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private SessionMapper sessionMapper;

    @MockBean
    private SessionController sessionController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setupSecurityContext() {
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("admin", "password", authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void testFindById() throws Exception {
        String id = "1";
        Session session = new Session();
        when(sessionService.getById(Long.parseLong(id))).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(new SessionDto());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/session/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/session"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        SessionDto sessionDto = new SessionDto();
        Session session = new Session();
        when(sessionService.create(any())).thenReturn(session);
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdate() throws Exception {
        String id = "1";
        SessionDto sessionDto = new SessionDto();
        Session session = new Session();
        when(sessionService.update(eq(Long.parseLong(id)), any(Session.class))).thenReturn(session);
        when(sessionMapper.toEntity(any(SessionDto.class))).thenReturn(session);
        when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/session/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isOk());
    }


    @Test
    public void testDelete() throws Exception {
        String id = "1";
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/session/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testParticipate() throws Exception {
        String sessionId = "1";
        String userId = "100";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/session/" + sessionId + "/participate/" + userId))
                .andExpect(status().isOk());
    }

    @Test
    public void testNoLongerParticipate() throws Exception {
        String sessionId = "1";
        String userId = "100";
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/session/" + sessionId + "/participate/" + userId))
                .andExpect(status().isOk());
    }
}
