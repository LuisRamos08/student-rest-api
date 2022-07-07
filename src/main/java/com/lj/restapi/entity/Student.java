package com.lj.restapi.entity;

import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    @NonNull
    private String firstname;

    private String lastname;

    @NonNull
    private String email;

    @NonNull
    private Integer age;
    private Gender gender;
    private Career career;
}
