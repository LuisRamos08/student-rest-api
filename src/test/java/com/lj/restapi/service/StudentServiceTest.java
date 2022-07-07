package com.lj.restapi.service;

import com.lj.restapi.entity.Career;
import com.lj.restapi.entity.Gender;
import com.lj.restapi.entity.Student;
import com.lj.restapi.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock private StudentRepository studentRepository;
    private StudentService studentService;
    private Student student;

    @BeforeEach void setUp(){
        student = new Student(
                1,
                "Luis",
                "Ramos",
                "lj_erm@hotmail.com",
                24,
                Gender.MALE,
                Career.Software_Developer);
        studentService = new StudentService(studentRepository);
    }

    @Test
    void canGetAllStudents() {
        //When
        studentService.getAllStudents();

        //Then
        verify(studentRepository).findAll();
    }

    @Test
    void canGetStudentById() {
        //When
        when(studentService.getStudentById(1L)).thenReturn(student);

        //Then
        Student studentFound = studentService.getStudentById(1L);

        assertEquals("Luis",studentFound.getFirstname());
        assertEquals("Ramos",studentFound.getLastname());
        assertEquals("lj_erm@hotmail.com",studentFound.getLastname());
        assertEquals(24,studentFound.getAge());
        assertEquals(Gender.MALE,studentFound.getGender());
        assertEquals(Career.Software_Developer,studentFound.getCareer());
    }

    @Test
    void canSaveStudent() {
        //Given

        //When
        studentService.saveStudent(student);

        //then
        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void shouldThrowWhenEmailIsTaken() {
        //Given
        Student student = new Student(
                1,
                "Luis",
                "Ramos",
                "lj_erm@hotmail.com",
                24,
                Gender.MALE,
                Career.Software_Developer);
        given(studentRepository.existsByEmail(student.getEmail())).
                 willReturn(true);

        //When //then
        assertThatThrownBy(() -> studentService.saveStudent(student))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email " + student.getEmail() + " taken");
    }

    @Test
    void canDelete() throws Exception{
        studentService.delete(student.getId());
        verify(studentRepository, times(1)).delete(student);
    }
}