package at.arz.latte.rodeo.user;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * To make shure that user entities can stay forever, we need an account entity to represent the system login
 * information.
 * 
 * Accounts can be deleted or recycled, without removing or modifying the user information.
 */
@Entity
@Table(name = "ACCOUNTS")
public class SystemAccount
		implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * the userId is unique and mapped to the users system account.
	 */
	@Id
	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "USER_PASSWORD", nullable = true)
	private String password;

	@OneToOne(orphanRemoval = false)
	private User user;

	protected SystemAccount() {
		// jpa constructor
	}

	public SystemAccount(String userId) {
		this.userId = userId;
	}

	void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return userId == null ? 0 : userId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemAccount other = (SystemAccount) obj;
		return Objects.equals(userId, other.userId);
	}

}
