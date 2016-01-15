package at.arz.latte.rodeo.journal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import at.arz.latte.rodeo.api.AbstractEntity;

/**
 * Entity implementation class for Entity: JournalAttribute
 *
 */
@Entity
@Table(name="EVENT_ATTRIBUTES")
@IdClass(AttributePrimaryKey.class)
public class Attribute
		extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	private JournalEntry event;

	@Id
	@Column(name = "ATTRIBUTE_NAME")
	private String name;

	@Id
	@Column(name = "ATTRIBUTE_INDEX")
	private int index;

	@Column(name = "ATTRIBUTE_VALUE")
	private String value;

	protected Attribute() {
		// jpa constructor
	}

	public Attribute(JournalEntry entry, String attributeName, String attributeValue) {
		this(entry, attributeName, attributeValue, 0);
	}

	public Attribute(JournalEntry entry, String attributeName, String attributeValue, int index) {
		this.event = entry;
		this.name = attributeName;
		this.value = attributeValue;
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
}
