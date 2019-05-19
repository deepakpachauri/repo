package nl.project.lms.entity;

import java.io.Serializable;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.utils.UUIDs;

@Table
public class Student implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8979377753882938593L;

	@PrimaryKey
	@CassandraType(type = DataType.Name.UUID)
	private UUIDs id;

	private String firstName;

	private String lastName;

	private String emailId;

	public Student() {
		// Default Construtor
	}

	public Student(UUIDs id, String firstName, String lastName, String emailId) {
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

	public UUIDs getId() {
		return id;
	}

	public void setId(UUIDs id) {
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
