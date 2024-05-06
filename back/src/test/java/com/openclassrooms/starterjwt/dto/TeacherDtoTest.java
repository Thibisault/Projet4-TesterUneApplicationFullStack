package com.openclassrooms.starterjwt.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

public class TeacherDtoTest {

    private TeacherDto teacherDto;

    @BeforeEach
    void setUp() {
        teacherDto = new TeacherDto(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void testEquals() {
        TeacherDto anotherTeacherDto = new TeacherDto(1L, "Doe", "John", teacherDto.getCreatedAt(), teacherDto.getUpdatedAt());
        assertThat(teacherDto).isEqualTo(anotherTeacherDto);
    }

    @Test
    void testHashCode() {
        TeacherDto anotherTeacherDto = new TeacherDto(1L, "Doe", "John", teacherDto.getCreatedAt(), teacherDto.getUpdatedAt());
        assertThat(teacherDto.hashCode()).isEqualTo(anotherTeacherDto.hashCode());
    }

    @Test
    void testToString() {
        assertThat(teacherDto.toString()).contains("Doe", "John");
    }

    @Test
    void testSettersAndGetters() {
        TeacherDto anotherTeacherDto = new TeacherDto();
        anotherTeacherDto.setId(teacherDto.getId());
        anotherTeacherDto.setLastName("Doe");
        anotherTeacherDto.setFirstName("John");
        anotherTeacherDto.setCreatedAt(teacherDto.getCreatedAt());
        anotherTeacherDto.setUpdatedAt(teacherDto.getUpdatedAt());

        assertThat(anotherTeacherDto.getId()).isEqualTo(teacherDto.getId());
        assertThat(anotherTeacherDto.getLastName()).isEqualTo("Doe");
        assertThat(anotherTeacherDto.getFirstName()).isEqualTo("John");
        assertThat(anotherTeacherDto.getCreatedAt()).isEqualTo(teacherDto.getCreatedAt());
        assertThat(anotherTeacherDto.getUpdatedAt()).isEqualTo(teacherDto.getUpdatedAt());
    }

    @Test
    void testCanEqual() {
        TeacherDto anotherTeacherDto = new TeacherDto();
        assertThat(teacherDto.canEqual(anotherTeacherDto)).isTrue();
        assertThat(teacherDto.canEqual(new Object())).isFalse();
    }
}
