package at.arz.latte.rodeo.user;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: User
 * 
 */
@Entity
@Table(name = "USERS")
public class User
		implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "OID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQUENCES")
	private Long id;

	@Column(name = "EMAILADR", nullable = false, unique = true)
	private String email;

	private String firstName;

	private String lastName;

	private boolean disabled;

	protected User() {
		// jpa constructor
	}

	public User(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public boolean isDisabled() {
		return disabled;
	}

	public void disable() {
		disabled = true;
	}

	public void activate() {
		disabled = false;
	}

	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return email == null ? 0 : email.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email);
	}

}
