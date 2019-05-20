package nl.project.lms.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.project.lms.entity.Student;
import nl.project.lms.service.StudentService;

@RestController()
@RequestMapping("rest/student")
public class StudentRestController {

	@Autowired
	private StudentService service;

	@RequestMapping(value = "/findAll")
	public List<Student> findAll() {
		return service.findAll();
	}

	@RequestMapping(value = "/findByFirstName")
	public List<Student> findByFirstName(@RequestParam(value = "firstName") String firstName) {
		return service.findByFirstName(firstName);
	}

	@RequestMapping(value = "/findByLastName")
	public List<Student> findByLastName(@RequestParam(value = "lastName") String lastName) {
		return service.findByLastName(lastName);
	}

	@RequestMapping(value = "/findByEmail")
	public Student findByEmailId(@RequestParam(value = "email") String email) {
		return service.findByEmail(email);
	}

	@RequestMapping(value = "/addStudent")
	public String addStudent() {
		Student student = new Student("Vihaan", "", "vihaan.pachauri");
		service.addStudent(student);
		return "Success";
	}

	public List<Student> findByDateOfBirth(@RequestParam(value = "dateOfBirth") String dateOfBirth) {
		return service.findByFirstName(dateOfBirth);
	}

}
