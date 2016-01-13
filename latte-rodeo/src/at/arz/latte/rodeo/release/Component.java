package at.arz.latte.rodeo.release;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import at.arz.latte.rodeo.api.AbstractEntity;

/**
 * a component is the description of a releaseable unit.
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

	protected Component() {
		// jpa constructor
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
