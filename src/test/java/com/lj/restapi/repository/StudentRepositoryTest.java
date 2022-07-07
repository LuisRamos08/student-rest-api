package com.lj.restapi.repository;

import com.lj.restapi.entity.Career;
import com.lj.restapi.entity.Gender;
import com.lj.restapi.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    void itShouldCheckIfStudentExistById(){
        //Given
        Student student = new Student(1,"Luis","Ramos","lj",24,Gender.MALE,Career.Software_Developer);
        studentRepository.save(student);

        //When
        Boolean actualResult = studentRepository.existsById(1L);

        //Then
        assertThat(actualResult).isTrue();
    }

    @Test
    void itShouldCheckIfStudentExistByEmail(){
        //Given
        String email = "lj_erm@hotmail.com";
        Student student = new Student(1,"Luis","Ramos", email,24,Gender.MALE,Career.Software_Developer);
        studentRepository.save(student);

        //When
        Boolean actualResult = studentRepository.existsByEmail(email);

        //Then
        assertThat(actualResult).isTrue();
    }

}