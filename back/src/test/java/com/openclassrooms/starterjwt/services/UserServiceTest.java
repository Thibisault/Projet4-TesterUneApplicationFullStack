package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;



    @Test
    void whenDeletingUser_thenRepositoryCalled() {
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        userService.delete(userId);

        verify(userRepository).deleteById(userId);
    }
    @Test
    void whenDeletingAllUsers_thenRepositoryCalled() {
        doNothing().when(userRepository).deleteAll();

        userService.deleteAll();

        verify(userRepository).deleteAll();
    }


    @Test
    void whenFindingByIdAndUserExists_thenCorrectUserReturned() {
        Long userId = 1L;
        User user = new User().setId(userId).setEmail("user@example.com").setFirstName("Test").setLastName("User").setAdmin(false);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User found = userService.findById(userId);

        assertNotNull(found);
        assertEquals(userId, found.getId());
        assertEquals("user@example.com", found.getEmail());
    }

    @Test
    void whenFindingByIdAndUserDoesNotExist_thenNullReturned() {
        Long userId = 2L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        User found = userService.findById(userId);

        assertNull(found);
    }

    @Test
    void whenFindingByEmailAndUserExists_thenCorrectUserReturned() {
        String userEmail = "test@example.com";
        User user = new User()
                .setEmail(userEmail)
                .setFirstName("Test")
                .setLastName("User")
                .setPassword("password")
                .setAdmin(false);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));

        User found = userService.findByEmail(userEmail);

        assertNotNull(found);
        assertEquals(userEmail, found.getEmail());
    }

    @Test
    void whenFindingByEmailAndUserDoesNotExist_thenNullReturned() {
        String userEmail = "nonexistent@example.com";
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        User found = userService.findByEmail(userEmail);

        assertNull(found);
    }

    @Test
    void whenSavingUser_thenCorrectlyPersisted() {
        User newUser = new User()
                .setEmail("newuser@example.com")
                .setFirstName("New")
                .setLastName("User")
                .setPassword("newpassword")
                .setAdmin(false);

        System.out.println(newUser);

        when(userRepository.save(any(User.class))).then(invocation -> {
            User user = invocation.getArgument(0);
            if (user.getCreatedAt() == null) {
                user.setCreatedAt(LocalDateTime.now());
            }
            user.setUpdatedAt(LocalDateTime.now());
            return user;
        });


        User savedUser = userService.save(newUser);
        System.out.println("savedUser : " +savedUser);

        assertNotNull(savedUser.getCreatedAt());
        assertNotNull(savedUser.getUpdatedAt());
        assertEquals("newuser@example.com", savedUser.getEmail());
    }

    @Test
    void whenAddingUserWithDuplicateEmail_thenExceptionThrown() {
        String userEmail = "duplicate@example.com";
        UserDto duplicateUserDto = new UserDto();
        duplicateUserDto.setEmail(userEmail);
        duplicateUserDto.setFirstName("Duplicate");
        duplicateUserDto.setLastName("User");
        duplicateUserDto.setPassword("password");
        duplicateUserDto.setAdmin(false);

        when(userRepository.existsByEmail(userEmail)).thenReturn(true);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.addUser(duplicateUserDto);
        });

        String expectedMessage = "Email already in use";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenAddingUser_thenUserSaved() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setFirstName("Test");
        userDto.setLastName("User");
        userDto.setPassword("password");
        userDto.setAdmin(false);

        User simulatedUser = new User()
                .setEmail("test@example.com")
                .setFirstName("Test")
                .setLastName("User")
                .setPassword("password")
                .setAdmin(false);

        when(userMapper.toEntity(userDto)).thenReturn(simulatedUser);

        when(userRepository.save(simulatedUser)).thenReturn(simulatedUser);

        User savedUser = userService.addUser(userDto);

        assertNotNull(savedUser);
        assertEquals("test@example.com", savedUser.getEmail());

        verify(userMapper).toEntity(userDto);

        verify(userRepository).save(simulatedUser);
    }

}
