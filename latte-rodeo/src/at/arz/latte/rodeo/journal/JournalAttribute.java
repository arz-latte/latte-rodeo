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
@Table(name="JOURNAL_ATTRIBUTES")
@IdClass(JournalAttributeKey.class)
public class JournalAttribute
		extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	private JournalEntry entry;

	@Id
	@Column(name = "ATTRIBUTE_NAME")
	private String name;

	@Id
	@Column(name = "ATTRIBUTE_INDEX")
	private int index;

	@Column(name = "ATTRIBUTE_VALUE")
	private String value;

	protected JournalAttribute() {
		// jpa constructor
	}

	public JournalAttribute(JournalEntry entry, String attributeName, String attributeValue) {
		this(entry, attributeName, attributeValue, 0);
	}

	public JournalAttribute(JournalEntry entry, String attributeName, String attributeValue, int index) {
		this.entry = entry;
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
