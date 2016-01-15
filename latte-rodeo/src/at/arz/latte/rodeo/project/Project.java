package at.arz.latte.rodeo.project;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import at.arz.latte.rodeo.infrastructure.AbstractEntity;
import at.arz.latte.rodeo.infrastructure.EventDispatcher;
import at.arz.latte.rodeo.project.admin.ProjectCreated;

@Entity
@Table(name = "PROJECTS")
@NamedQueries({ @NamedQuery(name = Project.FIND_BY_NAME, query = "select o from Project o where o.name=:name") })
public class Project
		extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_NAME = "Project.findByName";

	@Id
	@Column(name = "OID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQUENCES")
	private Long id;

	@OneToMany(mappedBy = "project", orphanRemoval = true, cascade = CascadeType.ALL)
	private Set<ProjectVersion> versions;

	@OneToOne
	@JoinColumns({	@JoinColumn(name = "OID",
								referencedColumnName = "PROJECT_ID",
								insertable = false,
								updatable = false),
					@JoinColumn(name = "MAINLINE_NAME",
								referencedColumnName = "PROJECT_VERSION_NAME",
								insertable = false,
								updatable = false) })
	private ProjectVersion mainline;

	@Column(name = "PROJECT_NAME", nullable = false, unique = true)
	private String name;

	@ManyToOne
	private Component component;

	protected Project() {
		// jpa constructor
	}

	public Project(String name) {
		this.name = name;
		this.versions = new HashSet<>();
		 EventDispatcher.notify(new ProjectCreated(name));
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void addBranch(String branchName) {
		ProjectVersion version = new ProjectVersion(this, branchName);
		version.setBranch(true);
	}

	public Set<ProjectVersion> getVersions() {
		return Collections.unmodifiableSet(versions);
	}

	public ProjectVersion getVersion(String name) {
		for (ProjectVersion projectVersion : versions) {
			if (name.equals(projectVersion.getName())) {
				return projectVersion;
			}
		}
		return null;
	}

	public void setMainline(ProjectVersion mainline) {
		if (mainline.isBranch()) {
			this.mainline = mainline;
		} else {
			throw new IllegalStateException("mainline is not a branch");
		}

	}

	public ProjectVersion getMainline() {
		return mainline;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name);
	}

	void setComponent(Component component) {
		this.component = component;
	}

	public Component getComponent() {
		return component;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		return Objects.equals(name, other.name);
	}

}
