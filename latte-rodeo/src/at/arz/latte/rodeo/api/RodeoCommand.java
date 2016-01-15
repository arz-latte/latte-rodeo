package at.arz.latte.rodeo.api;

import javax.persistence.EntityManager;

public interface RodeoCommand {
	void execute(EntityManager entityManager);
}
