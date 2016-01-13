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
 * Entity implementation class for Entity: JournalEntry
 * 
 */
@Entity
@Table(name = "JOURNAL_ENTRIES")
public class JournalEntry
		extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "OID")
	private Long id;

	@Column(name = "TSTAMP", nullable = false)
	private Timestamp timestamp;
	
	@Column(name = "ACTION_NAME", nullable = false)
	private String action;

	@OneToMany(mappedBy = "entry", orphanRemoval = true, cascade = ALL)
	private List<JournalAttribute> attributes;

	protected JournalEntry() {
		// jpa entry
	}

	public JournalEntry(String action) {
		Objects.requireNonNull(action, "action required");
		this.action = action;
		this.attributes = new ArrayList<JournalAttribute>();
	}

	public void addAttribute(String name, String value) {
		attributes.add(new JournalAttribute(this, name, value));
	}

	public void addAttribute(String name, int index, String value) {
		attributes.add(new JournalAttribute(this, name, value, index));
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public String getAction() {
		return action;
	}

	public List<JournalAttribute> getAttributes() {
		return Collections.unmodifiableList(attributes);
	}

}
