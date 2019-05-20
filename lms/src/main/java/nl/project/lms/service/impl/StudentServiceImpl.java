package nl.project.lms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.project.lms.entity.Student;
import nl.project.lms.repository.StudentRepository;
import nl.project.lms.service.StudentService;

@Service
public class StudentServiceImpl extends BaseServiceImpl<Student, UUID> implements StudentService {

	@Autowired
	private StudentRepository repository;

	@Override
	public List<Student> findByFirstName(String firstName) {
		List<Student> list = new ArrayList<>();
		repository.findByFirstName(firstName).forEach(list::add);
		return list;
	}

	@Override
	public List<Student> findByLastName(String lastName) {
		List<Student> list = new ArrayList<>();
		repository.findByLastName(lastName).forEach(list::add);
		return list;
	}

	@Override
	public List<Student> findByDateOfBirth(String dateOfBirth) {
		List<Student> list = new ArrayList<>();
		repository.findByDateOfBirth(dateOfBirth).forEach(list::add);
		return list;
	}

	@Override
	public Student findByEmail(String email) {
		return repository.findByEmailId(email);
	}

	@Override
	public void addStudent(Student student) {
		repository.insert(student);
	}
}
