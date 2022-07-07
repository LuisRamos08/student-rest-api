package com.lj.restapi.controller;

import com.lj.restapi.entity.Student;
import com.lj.restapi.exception.StudentNotFoundException;
import com.lj.restapi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping(value = "/getAllStudents",produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<Student>> getAllStudents(){
        try {
            List<Student> studentList = studentService.getAllStudents();
            return new ResponseEntity<>(studentList,HttpStatus.OK);
        }catch (StudentNotFoundException studentNotFoundException) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getStudentById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity getStudentById(@PathVariable("id") Long id){
        try{
            return new ResponseEntity<>(studentService.getStudentById(id),HttpStatus.OK);
        }catch (StudentNotFoundException studentNotFoundException){
            return new ResponseEntity<>("Student with id: " + id + " not found",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/estimateAge/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Object> estimateAgeById(@PathVariable("id") Long id){
        try{
            return new ResponseEntity<>(studentService.getEstimateAgeByStudentId(id), HttpStatus.OK);
        }catch (StudentNotFoundException studentNotFoundException){
            return new ResponseEntity<>(studentNotFoundException,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/saveStudent",produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Student> saveStudent(@RequestBody Student student){
        try{
            studentService.saveStudent(student);
        }catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @PutMapping(value = "/modifyStudent/{id}")
    public ResponseEntity<Student> modifyStudent(@PathVariable Long id, @RequestBody Student student){
        try{
            Student studentModified = studentService.modifyStudent(id,student);
            return new ResponseEntity<>(studentModified, HttpStatus.OK);
        }catch (StudentNotFoundException studentNotFoundException){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/deleteStudent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Student> deleteById(@PathVariable("id") Long id) {
        Student studentToDelete;
        try {
            studentToDelete = studentService.delete(id);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(studentToDelete, HttpStatus.NO_CONTENT);
    }
}
