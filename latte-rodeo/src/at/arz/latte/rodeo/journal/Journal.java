package at.arz.latte.rodeo.journal;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class EventJournal
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class Journal {

	@PersistenceContext(unitName = "latte-rodeo")
	private EntityManager entityManager;

	public void store(JournalEntry entry) {
		entityManager.persist(entry);
	}

	public void store(@Observes JournalEvent event) {
		JournalEntry entry = new JournalEntry(event.getClass().getSimpleName());
		entityManager.persist(entry);
	}

}
