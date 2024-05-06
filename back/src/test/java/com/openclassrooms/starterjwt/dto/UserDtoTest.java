package com.openclassrooms.starterjwt.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserDtoTest {

    @Autowired
    private LocalValidatorFactoryBean validator;

    private UserDto userDto;

    public String repeatString(String str, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    @BeforeEach
    void setUp() {
        userDto = new UserDto(null, "validemail@example.com", "LastName", "FirstName", false, "StrongPassword123", null, null);
    }

    @Test
    void whenDtoIsValid_thenNoViolations() {
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenEmailIsInvalid_thenConstraintViolation() {
        userDto.setEmail("invalidEmail");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void whenEmailExceedsSize_thenConstraintViolation() {
        String longEmail = "valid" + repeatString("a", 40) + "@example.com";
        userDto.setEmail(longEmail);
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void whenLastNameExceedsSize_thenConstraintViolation() {
        String longLastName = repeatString("a", 21);
        userDto.setLastName(longLastName);
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void whenFirstNameExceedsSize_thenConstraintViolation() {
        String longFirstName = repeatString("a", 21);
        userDto.setFirstName(longFirstName);
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void whenSerialize_thenCorrect() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String serializedDto = mapper.writeValueAsString(userDto);
        assertThat(serializedDto).contains("validemail@example.com");
        assertThat(serializedDto).doesNotContain("password");
    }

    @Test
    void whenDeserialize_thenCorrect() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = "{\"email\":\"validemail@example.com\",\"lastName\":\"LastName\",\"firstName\":\"FirstName\",\"admin\":false}";
        UserDto deserializedDto = mapper.readValue(json, UserDto.class);
        assertThat(deserializedDto.getEmail()).isEqualTo("validemail@example.com");
        assertThat(deserializedDto.getLastName()).isEqualTo("LastName");
        assertThat(deserializedDto.getFirstName()).isEqualTo("FirstName");
        assertThat(deserializedDto.isAdmin()).isFalse();
    }
    @Test
    public void testEquals() {
        LocalDateTime now = LocalDateTime.now();
        UserDto userDto1 = new UserDto(1L, "email@example.com", "Doe", "John", false, "password", now, now);
        UserDto userDto2 = new UserDto(1L, "email@example.com", "Doe", "John", false, "password", now, now);
        assertThat(userDto1).isEqualTo(userDto2);
    }

    @Test
    public void testHashCode() {
        LocalDateTime now = LocalDateTime.now();
        UserDto userDto1 = new UserDto(1L, "email@example.com", "Doe", "John", false, "password", now, now);
        UserDto userDto2 = new UserDto(1L, "email@example.com", "Doe", "John", false, "password", now, now);
        assertThat(userDto1.hashCode()).isEqualTo(userDto2.hashCode());
    }

    @Test
    public void testToString() {
        UserDto userDto = new UserDto(1L, "email@example.com", "Doe", "John", false, "password", null, null);
        assertThat(userDto.toString()).contains("email@example.com", "Doe", "John");
    }

    @Test
    public void testSettersAndGetters() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("email@example.com");
        userDto.setLastName("Doe");
        userDto.setFirstName("John");
        userDto.setAdmin(true);
        userDto.setPassword("password");
        LocalDateTime now = LocalDateTime.now();
        userDto.setCreatedAt(now);
        userDto.setUpdatedAt(now);

        assertThat(userDto.getId()).isEqualTo(1L);
        assertThat(userDto.getEmail()).isEqualTo("email@example.com");
        assertThat(userDto.getLastName()).isEqualTo("Doe");
        assertThat(userDto.getFirstName()).isEqualTo("John");
        assertThat(userDto.isAdmin()).isTrue();
        assertThat(userDto.getPassword()).isEqualTo("password");
        assertThat(userDto.getCreatedAt()).isEqualTo(now);
        assertThat(userDto.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    public void testCanEqual() {
        UserDto userDto = new UserDto();
        assertThat(userDto.canEqual(new UserDto())).isTrue();
        assertThat(userDto.canEqual(new Object())).isFalse();
    }
}

