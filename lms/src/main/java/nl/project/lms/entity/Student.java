package nl.project.lms.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.DataType;

@Table
public class Student implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8979377753882938593L;

	@PrimaryKey
	@CassandraType(type = DataType.Name.UUID)
	private UUID studentId;

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	@NotEmpty
	@Email
	private String emailId;
	
	private Date dateOfBirth;

	public Student() {
		// Default Construtor
	}

	public Student(UUID studentId, String firstName, String lastName, String emailId) {
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
	}
	
	public Student(String firstName, String lastName, String emailId) {
		this.studentId = UUID.randomUUID();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return String.format("Student[studentId=%s, firstName='%s', lastName='%s', emailId='%s']", this.studentId, this.firstName,
				this.lastName, this.emailId);
	}

	public UUID getStudentId() {
		return studentId;
	}

	public void setStudentId(UUID studentId) {
		this.studentId = studentId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
}
