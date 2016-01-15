package at.arz.latte.rodeo.project;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import at.arz.latte.rodeo.infrastructure.AbstractEntity;

/**
 * a component groups a set of projects.
 * 
 * @author mrodler
 * 
 */
@Entity
@Table(name = "COMPONENTS")
@NamedQueries({ @NamedQuery(name = Component.SELECT_ALL, query = "select o from Component o ") })
@DiscriminatorColumn(name = "CTYPE", length = 32)
@DiscriminatorValue("COMPONENT")
public class Component
		extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final String SELECT_ALL = "Component.selectAll";

	@Id
	@Column(name = "OID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQUENCES")
	private Long id;

	@Column(name = "COMPONENT_NAME", length = 255, unique = true)
	private String name;

	@OneToMany(mappedBy = "component", orphanRemoval = false)
	private Set<Project> projects;

	protected Component() {
		// jpa constructor
	}

	public Set<Project> getProjects() {
		return Collections.unmodifiableSet(projects);
	}

	public void assignProject(Project project) {
		Component other = project.getComponent();
		if (other != null) {
			other.removeProject(project);
		}
		projects.add(project);
		project.setComponent(this);
	}

	public void removeProject(Project project) {
		projects.remove(project);
		project.setComponent(null);
	}

	public Component(String componentName) {
		Objects.requireNonNull(componentName, "componentName required");
		this.name = componentName;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return name == null ? 0 : name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Component other = (Component) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Component [id=" + getId() + ", name=" + getName() + "]";
	}

}
