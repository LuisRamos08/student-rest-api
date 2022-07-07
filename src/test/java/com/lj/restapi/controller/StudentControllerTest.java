package com.lj.restapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.lj.restapi.entity.Career;
import com.lj.restapi.entity.Gender;
import com.lj.restapi.entity.Student;
import com.lj.restapi.repository.StudentRepository;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class StudentControllerTest {
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentController studentController;

    Student student1 = new Student(1, "Luis", "Ramos", "lj_erm@hotmail.com", 24, Gender.MALE, Career.Software_Developer);
    Student student2 = new Student(2, "Jose", "Mercedes", "jm@hotmail.com", 23, Gender.MALE, Career.Data_Analyst);
    Student student3 = new Student(3, "Kath", "Smith", "ks@hotmail.com", 22, Gender.FEMALE, Career.Architecture);

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc = standaloneSetup(studentController).build();
    }

    @Test
    public void canGetAllStudents() throws Exception{
        List<Student> students = new ArrayList<>(Arrays.asList(student1,student2,student3));

        when(studentRepository.findAll()).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/students")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

    }
}