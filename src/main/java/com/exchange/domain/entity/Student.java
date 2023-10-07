package com.exchange.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;


/**
 * The type Student.
 */
@Entity
@Table
@AllArgsConstructor @NoArgsConstructor @Builder
@Data
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    @Transient
    private  Integer age;
    private LocalDate dob;

    /**
     * Instantiates a new Student.
     *
     * @param name  the name
     * @param email the email
     * @param dob   the dob
     */
    public Student(String name, String email, LocalDate dob) {
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    /**
     * Gets age.
     *
     * @return the age
     */
    public final Integer getAge() {
        return Period.between(this.dob, (LocalDate.now())).getYears();
    }
}
