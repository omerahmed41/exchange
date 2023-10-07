package com.exchange.domain.service;

import com.exchange.domain.entity.Student;
import com.exchange.infrastructure.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public final class StudentService {
    private final int yearOfBirth = 1992;
    private final int dateOfBirth = 3;
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    public StudentService(StudentRepository studentRepository) {

        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        logger.info("Getting all students from DB");
        return this.studentRepository.findAll();
    }

    public Student createStudent() {

        Student student = new Student(
                "omer",
                "omer@gmail.com",
                LocalDate.of(yearOfBirth, Month.OCTOBER, dateOfBirth));
        return this.studentRepository.save(student);
    }
}
