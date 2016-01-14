package at.arz.latte.rodeo.journal;

import static javax.persistence.CascadeType.ALL;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import at.arz.latte.rodeo.api.AbstractEntity;

/**
 * Entity implementation class for Entity: Event
 * 
 */
@Entity
@Table(name = "EVENTS")
public class Event
		extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "OID")
	private Long id;

	@Column(name = "TSTAMP", nullable = false)
	private Timestamp timestamp;
	
	@Column(name = "EVENT_NAME", nullable = false)
	private String name;

	@OneToMany(mappedBy = "event", orphanRemoval = true, cascade = ALL)
	private List<EventAttribute> attributes;

	protected Event() {
		// jpa entry
	}

	public Event(String name) {
		Objects.requireNonNull(name, "name required");
		this.name = name;
		this.attributes = new ArrayList<EventAttribute>();
	}

	public void addAttribute(String name, String value) {
		attributes.add(new EventAttribute(this, name, value));
	}

	public void addAttribute(String name, int index, String value) {
		attributes.add(new EventAttribute(this, name, value, index));
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public String getAction() {
		return name;
	}

	public List<EventAttribute> getAttributes() {
		return Collections.unmodifiableList(attributes);
	}

}
