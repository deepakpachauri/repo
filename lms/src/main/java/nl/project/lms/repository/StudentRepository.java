package nl.project.lms.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import nl.project.lms.entity.Student;

@Repository
public interface StudentRepository extends CassandraRepository<Student, UUID> {
	
	@Query("select * from Student where firstName = ?0")
	List<Student> findByFirstName(String firstName);
	
	
	@Query("select * from Student where lastName = ?0")
	List<Student> findByLastName(String lastName);
	
	
	@Query("select * from Student where dateOfBirth = ?0")
	List<Student> findByDateOfBirth(String dateOfBirth);
	
	@Query("select * from Student where emailId = ?0")
	Student findByEmailId(String emailId);

}
