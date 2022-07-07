package com.lj.restapi.service;

import com.lj.restapi.entity.Student;
import com.lj.restapi.exception.StudentNotFoundException;
import com.lj.restapi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents(){
        return new ArrayList<>(studentRepository.findAll());
    }

    public Student getStudentById(Long id){
        return studentRepository.findById(id).orElseThrow(()
                -> new StudentNotFoundException("Student with id: "+ id + " not found"));
    }

    public Object getEstimateAgeByStudentId(Long id){
        Optional<Student> student = studentRepository.findById(id);
        String url = "https://api.agify.io?name="
                + student.orElseThrow(()
                    -> new StudentNotFoundException("Student with id: "+ id + " not found")).getFirstname();
        return new RestTemplate().getForObject(url,Object.class);
    }

    public Boolean getStudentByEmail(String email){
        return studentRepository.existsByEmail(email);
    }

    public void saveStudent(Student student){
        Boolean existsEmail = studentRepository.existsByEmail(student.getEmail());
        if(existsEmail) {
            throw new IllegalStateException(
                    "Email " + student.getEmail() + " taken");
        }

        studentRepository.save(student);
    }

    public Student modifyStudent(Long id, Student student){
        Student studentToEdit = this.getStudentById(id);

        setStudent(student,studentToEdit);

        studentRepository.save(studentToEdit);

        return studentToEdit;
    }

    public void setStudent(Student student, Student studentToSet){
        studentToSet.setFirstname(student.getFirstname());
        studentToSet.setLastname(student.getLastname());
        studentToSet.setEmail(student.getEmail());
        studentToSet.setGender(student.getGender());
        studentToSet.setCareer(student.getCareer());
    }

    public Student delete(Long id){
        Student studentToDelete = this.getStudentById(id);
        studentRepository.deleteById(studentToDelete.getId());
        return studentToDelete;
    }
}
