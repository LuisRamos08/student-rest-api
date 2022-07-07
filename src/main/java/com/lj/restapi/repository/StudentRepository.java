package com.lj.restapi.repository;

import com.lj.restapi.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student,Long> {
    @Query("select (count(s) > 0) from Student s where s.email = ?1")
    Boolean existsByEmail(String email);
}
