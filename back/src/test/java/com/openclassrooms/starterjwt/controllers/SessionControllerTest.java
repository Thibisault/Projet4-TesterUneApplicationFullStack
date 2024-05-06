package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionControllerTest {

    @Mock
    private SessionService sessionService;

    @Mock
    private SessionMapper sessionMapper;

    @InjectMocks
    private SessionController sessionController;

    private Session session;
    private SessionDto sessionDto;

    @BeforeEach
    void setUp() {
        session = new Session();
        session.setId(1L);
        session.setName("Yoga Session");

        sessionDto = new SessionDto();
        sessionDto.setId(1L);
        sessionDto.setName("Yoga Session");
    }

    @Test
    void findById_Success() {
        when(sessionService.getById(anyLong())).thenReturn(session);
        when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);

        ResponseEntity<?> response = sessionController.findById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionDto, response.getBody());

        verify(sessionService).getById(1L);
        verify(sessionMapper).toDto(session);
    }

    @Test
    void findById_NotFound() {
        when(sessionService.getById(anyLong())).thenReturn(null);

        ResponseEntity<?> response = sessionController.findById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(sessionService).getById(1L);
        verifyNoInteractions(sessionMapper);
    }

    @Test
    void findAll_Success() {
        when(sessionService.findAll()).thenReturn(Arrays.asList(session));
        when(sessionMapper.toDto(anyList())).thenReturn(Arrays.asList(sessionDto));

        ResponseEntity<?> response = sessionController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
        assertEquals(1, ((List<?>) response.getBody()).size());

        verify(sessionService).findAll();
        verify(sessionMapper).toDto(anyList());
    }

    @Test
    void create_Success() {
        when(sessionService.create(any(Session.class))).thenReturn(session);
        when(sessionMapper.toEntity(any(SessionDto.class))).thenReturn(session);
        when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);

        ResponseEntity<?> response = sessionController.create(sessionDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionDto, response.getBody());

        verify(sessionService).create(session);
        verify(sessionMapper).toEntity(sessionDto);
        verify(sessionMapper).toDto(session);
    }

    @Test
    void update_Success() {
        when(sessionService.update(anyLong(), any(Session.class))).thenReturn(session);
        when(sessionMapper.toEntity(any(SessionDto.class))).thenReturn(session);
        when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);

        ResponseEntity<?> response = sessionController.update("1", sessionDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionDto, response.getBody());

        verify(sessionService).update(eq(1L), any(Session.class));
        verify(sessionMapper).toEntity(sessionDto);
        verify(sessionMapper).toDto(session);
    }

    @Test
    void update_BadRequest() {
        ResponseEntity<?> response = sessionController.update("notANumber", sessionDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verifyNoInteractions(sessionService);
        verifyNoInteractions(sessionMapper);
    }
    @Test
    void save_Success() {
        when(sessionService.getById(anyLong())).thenReturn(session);
        doNothing().when(sessionService).delete(anyLong());

        ResponseEntity<?> response = sessionController.save("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sessionService).delete(1L);
    }

    @Test
    void save_NotFound() {
        when(sessionService.getById(anyLong())).thenReturn(null);

        ResponseEntity<?> response = sessionController.save("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(sessionService, never()).delete(1L);
    }

    @Test
    void save_BadRequest() {
        ResponseEntity<?> response = sessionController.save("notANumber");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verifyNoInteractions(sessionService);
    }

    @Test
    void participate_Success() {
        doNothing().when(sessionService).participate(anyLong(), anyLong());

        ResponseEntity<?> response = sessionController.participate("1", "2");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sessionService).participate(1L, 2L);
    }

    @Test
    void participate_BadRequest_Id() {
        ResponseEntity<?> response = sessionController.participate("notANumber", "2");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(sessionService, never()).participate(anyLong(), anyLong());
    }

    @Test
    void participate_BadRequest_UserId() {
        ResponseEntity<?> response = sessionController.participate("1", "notANumber");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(sessionService, never()).participate(anyLong(), anyLong());
    }
    @Test
    void noLongerParticipate_Success() {
        doNothing().when(sessionService).noLongerParticipate(anyLong(), anyLong());

        ResponseEntity<?> response = sessionController.noLongerParticipate("1", "2");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sessionService).noLongerParticipate(1L, 2L);
    }

    @Test
    void noLongerParticipate_BadRequest_Id() {
        ResponseEntity<?> response = sessionController.noLongerParticipate("notANumber", "2");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(sessionService, never()).noLongerParticipate(anyLong(), anyLong());
    }

    @Test
    void noLongerParticipate_BadRequest_UserId() {
        ResponseEntity<?> response = sessionController.noLongerParticipate("1", "notANumber");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(sessionService, never()).noLongerParticipate(anyLong(), anyLong());
    }

}
