package at.arz.latte.rodeo.journal;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.rodeo.event.Event;

/**
 * Session Bean implementation class EventJournal
 */
@Stateless
@LocalBean
public class EventJournal {

	@PersistenceContext(unitName="latte-rodeo")
	private EntityManager entityManager;
	
	public void journalizeEvent(@Observes Event event){
		EventEntry entry = new EventEntry(event.getEventName());
		entityManager.persist(entry);
	}
}
