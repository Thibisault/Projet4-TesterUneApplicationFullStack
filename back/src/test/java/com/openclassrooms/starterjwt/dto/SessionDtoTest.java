package com.openclassrooms.starterjwt.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import java.util.Date;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SessionDtoTest {

    @Autowired
    private LocalValidatorFactoryBean validator;

    private SessionDto sessionDto;

    // Méthode pour répérer une string x time
    public String repeatString(String str, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    @BeforeEach
    void setUp() {
        sessionDto = new SessionDto();
        sessionDto.setName("Session Valid Name");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L);
        sessionDto.setDescription("Valid description for the session.");
    }

    @Test
    void whenDtoIsValid_thenNoViolations() {
        Set<ConstraintViolation<SessionDto>> violations = validator.validate(sessionDto);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenNameIsBlank_thenOneViolation() {
        sessionDto.setName("");
        Set<ConstraintViolation<SessionDto>> violations = validator.validate(sessionDto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void whenSerialize_thenCorrect() throws JsonProcessingException {
        SessionDto originalDto = new SessionDto(1L, "Session Name", new Date(), 1L, "Description", null, null, null);

        ObjectMapper mapper = new ObjectMapper();
        String serializedDto = mapper.writeValueAsString(originalDto);

        assertThat(serializedDto).contains("Session Name");
    }

    @Test
    void whenDeserialize_thenCorrect() throws JsonMappingException, JsonProcessingException {
        String json = "{\"id\":1,\"name\":\"Session Name\",\"description\":\"Description\",\"teacher_id\":1,\"date\":\"2023-01-01T12:00:00\",\"users\":null,\"createdAt\":null,\"updatedAt\":null}";

        ObjectMapper mapper = new ObjectMapper();
        SessionDto deserializedDto = mapper.readValue(json, SessionDto.class);

        assertThat(deserializedDto.getName()).isEqualTo("Session Name");
    }

    @Test
    void givenTwoIdenticalDtos_whenEquals_thenTrue() {
        SessionDto dto1 = new SessionDto(1L, "Session Name", new Date(), 1L, "Description", null, null, null);
        SessionDto dto2 = new SessionDto(1L, "Session Name", new Date(), 1L, "Description", null, null, null);

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }
    @Test
    void givenTwoDifferentDtos_whenEquals_thenFalse() {
        SessionDto dto1 = new SessionDto(1L, "Session Name", new Date(), 1L, "Description", null, null, null);
        SessionDto dto2 = new SessionDto(2L, "Another Session Name", new Date(), 2L, "Another Description", null, null, null);

        assertThat(dto1).isNotEqualTo(dto2);
    }

    @Test
    void whenToString_thenContainsImportantInformation() {
        SessionDto dto = new SessionDto(1L, "Session Name", new Date(), 1L, "Description", null, null, null);
        String toStringResult = dto.toString();

        assertThat(toStringResult).contains("Session Name");
        assertThat(toStringResult).contains("Description");
    }
    @Test
    void whenCanEqualWithSameType_thenTrue() {
        SessionDto dto1 = new SessionDto();
        SessionDto dto2 = new SessionDto();

        assertThat(dto1.canEqual(dto2)).isTrue();
    }
    @Test
    void whenCanEqualWithDifferentType_thenFalse() {
        SessionDto dto = new SessionDto();
        Object other = new Object();

        assertThat(dto.canEqual(other)).isFalse();
    }

    @Test
    void whenNameIsBlank_thenConstraintViolation() {
        sessionDto.setName("");

        Set<ConstraintViolation<SessionDto>> violations = validator.validate(sessionDto);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message").containsExactly("ne doit pas être vide");
    }
    @Test
    void whenNameExceedsSize_thenConstraintViolation() {
        String longName = repeatString("a", 51);
        sessionDto.setName(longName);

        Set<ConstraintViolation<SessionDto>> violations = validator.validate(sessionDto);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message").containsExactlyInAnyOrder("la taille doit être comprise entre 0 et 50");
    }

    @Test
    void whenDateIsNull_thenConstraintViolation() {
        sessionDto.setDate(null);

        Set<ConstraintViolation<SessionDto>> violations = validator.validate(sessionDto);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message").containsExactly("ne doit pas être nul");
    }
    @Test
    void whenDescriptionExceedsSize_thenConstraintViolation() {
        String longDescription = repeatString("a", 2501);
        sessionDto.setDescription(longDescription);

        Set<ConstraintViolation<SessionDto>> violations = validator.validate(sessionDto);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message").containsExactlyInAnyOrder("la taille doit être comprise entre 0 et 2500");
    }





}
