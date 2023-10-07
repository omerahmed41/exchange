package com.exchange.application;

import com.exchange.domain.entity.Student;
import com.exchange.domain.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Secured("ROLE_ADMIN")
@RequestMapping(path = "/api/v1")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping(path = "students")
    public final List<Student> getStudents() {
        return this.studentService.getStudents();
    }

    @PostMapping(path = "student")
    public final Student createStudents() {
        return this.studentService.createStudent();
    }
}
