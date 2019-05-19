package nl.project.lms.service;

import java.util.List;
import java.util.UUID;

import nl.project.lms.entity.Student;

public interface StudentService extends BaseService<Student, UUID> {

	List<Student> findByFirstName(String firstName);

	List<Student> findByLastName(String lastName);

	Student findByEmail(String email);

}
