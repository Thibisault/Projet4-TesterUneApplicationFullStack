package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SessionService sessionService;

    @Test
    void whenCreatingSession_thenSessionSaved() {
        Session session = new Session();

        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        Session savedSession = sessionService.create(session);

        assertNotNull(savedSession);
        assertEquals(session, savedSession);
    }

    @Test
    void whenDeletingSession_thenRepositoryCalled() {
        Long sessionId = 1L;

        sessionService.delete(sessionId);

        verify(sessionRepository).deleteById(sessionId);
    }

    @Test
    void whenFindingAllSessions_thenAllSessionsReturned() {
        List<Session> sessions = new ArrayList<>();
        sessions.add(new Session());
        sessions.add(new Session());

        when(sessionRepository.findAll()).thenReturn(sessions);

        List<Session> foundSessions = sessionService.findAll();

        assertNotNull(foundSessions);
        assertEquals(sessions.size(), foundSessions.size());
    }

    @Test
    void whenUpdatingSession_thenSessionUpdated() {
        Long sessionId = 1L;
        Session session = new Session().setId(sessionId).setName("Session").setDescription("Description").setDate(new Date()).setUsers(new ArrayList<>());
        Session updatedSession = new Session().setId(sessionId).setName("Updated Session").setDescription("Updated Description").setDate(new Date()).setUsers(new ArrayList<>());

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(sessionRepository.save(any(Session.class))).thenReturn(updatedSession);

        Session result = sessionService.update(sessionId, updatedSession);

        assertNotNull(result);
        assertEquals(updatedSession.getName(), result.getName());
        assertEquals(updatedSession.getDescription(), result.getDescription());
    }

    @Test
    void whenParticipatingInSession_thenUserAdded() {
        Long sessionId = 1L;
        Long userId = 1L;
        Session session = new Session().setId(sessionId).setName("Session").setDescription("Description").setDate(new Date()).setUsers(new ArrayList<>());
        User user = new User().setId(userId).setEmail("user@example.com").setFirstName("Test").setLastName("User");

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        sessionService.participate(sessionId, userId);

        assertTrue(session.getUsers().contains(user));
    }

    @Test
    void whenNoLongerParticipatingInSession_thenUserRemoved() {
        Long sessionId = 1L;
        Long userId = 1L;
        User user = new User().setId(userId).setEmail("user@example.com").setFirstName("Test").setLastName("User");
        List<User> users = new ArrayList<>();
        users.add(user);
        Session session = new Session().setId(sessionId).setName("Session").setDescription("Description").setDate(new Date()).setUsers(users);

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        sessionService.noLongerParticipate(sessionId, userId);

        assertFalse(session.getUsers().contains(user));
    }


    @Test
    void whenParticipatingInNonExistingSession_thenNotFoundExceptionThrown() {
        Long sessionId = 1L;
        Long userId = 1L;

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sessionService.participate(sessionId, userId));
    }


    @Test
    void whenNoLongerParticipatingInNonExistingSession_thenNotFoundExceptionThrown() {
        Long sessionId = 1L;
        Long userId = 1L;

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sessionService.noLongerParticipate(sessionId, userId));
    }
}