package at.arz.latte.rodeo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "APPLICATIONS")
@NamedQueries({ @NamedQuery(name = Application.SELECT_ALL, query = "select o from Application o ") })
public class Application {
	public static final String SELECT_ALL = "Application.selectAll";

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "APPLICATION_NAME", length = 255, unique = true)
	private String applicationName;

	public Long getId() {
		return id;
	}

	public String getApplicationName() {
		return applicationName;
	}

	@Override
	public int hashCode() {
		return applicationName == null ? 0 : applicationName.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Application other = (Application) obj;
		return applicationName.equals(other.applicationName);
	}

	@Override
	public String toString() {
		return "Application [id=" + id + ", applicationName=" + applicationName + "]";
	}

}
