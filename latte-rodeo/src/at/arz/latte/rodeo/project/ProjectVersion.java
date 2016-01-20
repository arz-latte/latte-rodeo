package at.arz.latte.rodeo.project;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import at.arz.latte.rodeo.infrastructure.AbstractEntity;

@Entity
@Table(name = "PROJECT_VERSIONS")
@IdClass(ProjectVersionPrimaryKey.class)
public class ProjectVersion
		extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@Column(name = "PROJECT_ID")
	private Project project;

	@Id
	@Column(name = "PROJECT_VERSION_NAME")
	private String name;

	private boolean branch;
	
	protected ProjectVersion() {
		// jpa constructor
	}

	protected ProjectVersion(Project project, String name) {
		this.project = project;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	void setBranch(boolean branch) {
		this.branch = branch;
	}

	public boolean isBranch() {
		return branch;
	}

	@Override
	public int hashCode() {
		return Objects.hash(project, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectVersion other = (ProjectVersion) obj;
		return Objects.equals(project, other.project) && Objects.equals(name, other.name);
	}

}
