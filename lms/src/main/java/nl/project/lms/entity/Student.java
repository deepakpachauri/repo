package nl.project.lms.entity;

import java.io.Serializable;
import java.util.UUID;

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
	@CassandraType(type = DataType.Name.TIMEUUID)
	private UUID id;

	private String firstName;

	private String lastName;

	private String emailId;

	public Student() {
		// Default Construtor
	}

	public Student(UUID id, String firstName, String lastName, String emailId) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return String.format("Student[id=%s, firstName='%s', lastName='%s', emailId='%s']", this.id, this.firstName,
				this.lastName, this.emailId);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

}
