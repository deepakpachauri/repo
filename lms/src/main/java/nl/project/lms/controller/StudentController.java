package nl.project.lms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.project.lms.entity.Student;
import nl.project.lms.service.StudentService;

@RestController()
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService service;

	@RequestMapping(value = "/findAll")
	public List<Student> findAll() {

		List<Student> students = new ArrayList<>();
		service.findAll().forEach(students::add);

		return students;
	}

	@RequestMapping(value = "/findByFirstName")
	public List<Student> findByFirstName(@RequestParam(value = "firstName") String firstName) {

		List<Student> students = service.findByFirstName(firstName);
		System.out.println(students);
		return students;
	}

	@RequestMapping(value = "/findByLastName")
	public List<Student> findByLastName(@RequestParam(value = "lastName") String lastName) {
		return service.findByLastName(lastName);
	}

	@RequestMapping(value = "/findByEmail")
	public Student findByEmailId(@RequestParam(value = "email") String email) {
		return service.findByEmail(email);
	}

}
