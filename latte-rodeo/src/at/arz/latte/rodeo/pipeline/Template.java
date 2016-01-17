package at.arz.latte.rodeo.pipeline;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.openjpa.persistence.Externalizer;
import org.apache.openjpa.persistence.Persistent;

import at.arz.latte.rodeo.infrastructure.AbstractEntity;

/**
 * Entity implementation class for Entity: Template
 *
 */
@Entity
@Table(name = "TEMPLATES")
public class Template extends AbstractEntity {

	public static final int MAX_LENGTH = 2000;

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "OID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQUENCES")
	private Long id;

	@Persistent
	@Externalizer("toString")
	@Column(name = "TEMPLATE_NAME", nullable = false, unique = true)
	private TemplateName name;

	@Column(name = "TEMPLATE_CONTENT", length = MAX_LENGTH)
	private String content;

	protected Template() {
		// jpa constructor
	}

	public Template(TemplateName name, String content) {
		Objects.requireNonNull(name, "name required");
		this.name = name;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public Long getId() {
		return id;
	}

	public TemplateName getName() {
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
		Template other = (Template) obj;
		return Objects.equals(name, other.name);
	}

}
